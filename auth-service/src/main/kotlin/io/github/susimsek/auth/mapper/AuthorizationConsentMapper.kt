package io.github.susimsek.auth.mapper

import io.github.susimsek.auth.domain.AuthorizationConsent
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.stereotype.Component

@Component
class AuthorizationConsentMapper {

    fun toAuthorizationConsent(authorizationConsent: OAuth2AuthorizationConsent): AuthorizationConsent {
        val authorities = mutableSetOf<String>()

        authorizationConsent.authorities.forEach {authority ->  authorities.add(authority.authority) }
        return AuthorizationConsent(
            registeredClientId = authorizationConsent.registeredClientId,
            principalName = authorizationConsent.principalName,
            authorities = authorities
        )
    }

    fun toOAuth2AuthorizationConsent(client: RegisteredClient, authorizationConsent: AuthorizationConsent): OAuth2AuthorizationConsent {
        val principalName: String = authorizationConsent.principalName
        val builder = OAuth2AuthorizationConsent.withId(authorizationConsent.registeredClientId, principalName)
        val authorizationConsentAuthorities = authorizationConsent.authorities
        authorizationConsentAuthorities.forEach {  builder.authority(SimpleGrantedAuthority(it))}
        return builder.build()
    }
}