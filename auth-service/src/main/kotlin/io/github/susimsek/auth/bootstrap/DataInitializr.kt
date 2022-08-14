package io.github.susimsek.auth.bootstrap

import io.github.susimsek.auth.domain.Authority
import io.github.susimsek.auth.domain.User
import io.github.susimsek.auth.repository.OAuth2AuthorityRepository
import io.github.susimsek.auth.repository.OAuth2UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.ClientSettings
import org.springframework.security.oauth2.server.authorization.config.TokenSettings
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(
    value = ["command.line.runner.enabled"],
    havingValue = "true",
    matchIfMissing = true)
internal class DataInitializr(private val oAuth2AuthorityRepository: OAuth2AuthorityRepository,
                              private val oAuth2UserRepository: OAuth2UserRepository,
                              private val passwordEncoder: PasswordEncoder,
                              private val tokenSettings: TokenSettings,
                              private val registeredClientRepository: RegisteredClientRepository) : CommandLineRunner {

    override fun run(args: Array<String>) {
        oAuth2AuthorityRepository.deleteAll()
        oAuth2UserRepository.deleteAll()
        var authority = Authority("ROLE_ADMIN")
        authority = oAuth2AuthorityRepository.save(authority)

        val userAuthority = Authority("ROLE_USER")
        oAuth2AuthorityRepository.save(userAuthority)

        val newUser = User(
            username = "admin",
            password = passwordEncoder.encode("password"),
            email = "admin58@gmail.com",
            authorities = mutableSetOf(authority)
        )
        oAuth2UserRepository.save(newUser)

        val webClient = RegisteredClient.withId("e4a295f7-0a5f-4cbc-bcd3-d870243d1b04")
            .clientId("web-client")
            .clientSecret( passwordEncoder.encode("123456"))
            .redirectUri("https://oidcdebugger.com/debug")
            .redirectUri("http://gqlmsweb.susimsek.github.io/callback")
            .redirectUri("https://gqlmsweb.susimsek.github.io/callback")
            .redirectUri("http://127.0.0.1:3000/callback")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .scope(OidcScopes.OPENID)
            .scope(OidcScopes.PROFILE)
            .tokenSettings(tokenSettings)
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
            .build()

        registeredClientRepository.save(webClient)
    }
}