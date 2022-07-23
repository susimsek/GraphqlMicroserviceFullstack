package io.github.susimsek.auth.config

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import io.github.susimsek.auth.mapper.AuthorizationConsentMapper
import io.github.susimsek.auth.mapper.AuthorizationMapper
import io.github.susimsek.auth.mapper.ClientMapper
import io.github.susimsek.auth.repository.OAuth2AuthorizationConsentRepository
import io.github.susimsek.auth.repository.OAuth2AuthorizationRepository
import io.github.susimsek.auth.repository.OAuth2RegisteredClientRepository
import io.github.susimsek.auth.repository.OAuth2UserRepository
import io.github.susimsek.auth.security.FederatedIdentityConfigurer
import io.github.susimsek.auth.security.FederatedIdentityIdTokenCustomizer
import io.github.susimsek.auth.service.MongodbOAuth2AuthorizationConsentService
import io.github.susimsek.auth.service.MongodbOAuth2AuthorizationService
import io.github.susimsek.auth.service.MongodbRegisteredClientService
import io.github.susimsek.auth.service.MongodbUserDetailsService
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationEndpointConfigurer
import org.springframework.security.config.annotation.web.configurers.oauth2.server.authorization.OAuth2AuthorizationServerConfigurer
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings
import org.springframework.security.oauth2.server.authorization.config.TokenSettings
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.util.matcher.RequestMatcher
import java.security.KeyStore
import java.time.Duration


@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(JWKSetProperties::class)
class AuthorizationServerConfig(
    private val providerProperties: ProviderProperties
) {

    private val CUSTOM_CONSENT_PAGE_URI = "/oauth2/consent"

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Throws(Exception::class)
    fun authorizationServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {

        val authorizationServerConfigurer = OAuth2AuthorizationServerConfigurer<HttpSecurity>()
        authorizationServerConfigurer
            .authorizationEndpoint { authorizationEndpoint: OAuth2AuthorizationEndpointConfigurer ->
                authorizationEndpoint.consentPage(
                    CUSTOM_CONSENT_PAGE_URI
                )
            }

        val endpointsMatcher: RequestMatcher = authorizationServerConfigurer
            .endpointsMatcher
        http
            .cors().and()
            .requestMatcher(endpointsMatcher)
            .authorizeRequests { authorizeRequests ->
                authorizeRequests.anyRequest().authenticated()
            }
            .csrf{csrf -> csrf.ignoringRequestMatchers(endpointsMatcher)}
            .exceptionHandling { exceptions ->
                exceptions.authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login")) }
            .oauth2ResourceServer{it.jwt()}
            .apply(authorizationServerConfigurer)

        http.apply(FederatedIdentityConfigurer())

        return http.build()
    }


    @Bean
    fun idTokenCustomizer(): OAuth2TokenCustomizer<JwtEncodingContext> {
        return FederatedIdentityIdTokenCustomizer()
    }

    @Bean
    fun registeredClientRepository(oauth2RegisteredClientRepository: OAuth2RegisteredClientRepository,
                                   registeredClientMapper: ClientMapper): RegisteredClientRepository {
       return MongodbRegisteredClientService(oauth2RegisteredClientRepository, registeredClientMapper)
    }

    @Bean
    fun oAuth2AuthorizationService(registeredClientRepository: RegisteredClientRepository,
                                   oAuth2AuthorizationRepository: OAuth2AuthorizationRepository,
                                   authorizationMapper: AuthorizationMapper
    ): OAuth2AuthorizationService {
        return MongodbOAuth2AuthorizationService(registeredClientRepository, oAuth2AuthorizationRepository, authorizationMapper)
    }

    @Bean
    fun oAuth2AuthorizationConsentService(registeredClientRepository: RegisteredClientRepository,
                                          oAuth2AuthorizationConsentRepository: OAuth2AuthorizationConsentRepository,
                                          authorizationConsentMapper: AuthorizationConsentMapper
    ): OAuth2AuthorizationConsentService {
        return MongodbOAuth2AuthorizationConsentService(registeredClientRepository, oAuth2AuthorizationConsentRepository, authorizationConsentMapper)
    }

    @Bean
    fun userDetailsService(oAuth2UserRepository: OAuth2UserRepository
    ): UserDetailsService {
       return MongodbUserDetailsService(oAuth2UserRepository)
    }

    @Bean
    fun jwtDecoder(jwkSource: JWKSource<SecurityContext>): JwtDecoder {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource)
    }

    @Bean
    fun jwkSource(jwkSetProperties: JWKSetProperties): JWKSource<SecurityContext> {
        val jwkSet = buildJWKSet(jwkSetProperties)
        return ImmutableJWKSet(jwkSet)
    }

    @Bean
    fun providerSettings(): ProviderSettings {
        return ProviderSettings.builder()
            .issuer(providerProperties.issuer)
            .build()
    }

    @Bean
    fun tokenSettings(): TokenSettings {
        return TokenSettings.builder()
            .accessTokenTimeToLive(Duration.ofMinutes(5))
            .refreshTokenTimeToLive(Duration.ofMinutes(10))
            .build()
    }


    private fun buildJWKSet(jwkSetProperties: JWKSetProperties): JWKSet {
        return try {
            val ksFile = jwkSetProperties.keyStore
            val keyStorePassword = jwkSetProperties.keyStorePassword
            val keyStore = KeyStore.getInstance("pkcs12")
            keyStore.load(ksFile.inputStream, keyStorePassword.toCharArray())
            JWKSet.load(keyStore) { keyStorePassword.toCharArray() }
        } catch (ex: Exception) {
            throw IllegalStateException(ex)
        }
    }
}