package io.github.susimsek.auth.config

import io.github.susimsek.auth.security.FederatedIdentityConfigurer
import io.github.susimsek.auth.security.UserRepositoryOAuth2UserHandler
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@EnableConfigurationProperties(CorsProperties::class)
class DefaultSecurityConfig(
    private val securityMatcherProperties: SecurityMatcherProperties,
    private val userRepositoryOAuth2UserHandler: UserRepositoryOAuth2UserHandler
) {

    @Bean
    @Throws(Exception::class)
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        val federatedIdentityConfigurer = FederatedIdentityConfigurer()
            .oauth2UserHandler(userRepositoryOAuth2UserHandler)
        http
            .authorizeHttpRequests { authorize ->
                authorize
                    .antMatchers(*securityMatcherProperties.permitAllPatterns.toTypedArray()).permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin {
                formLogin ->
                    formLogin.loginPage("/login").permitAll()
            }
            .apply(federatedIdentityConfigurer)
        return http.build()
    }

    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers(*securityMatcherProperties.ignorePatterns.toTypedArray())
        }
    }
}
