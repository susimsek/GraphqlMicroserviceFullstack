package io.github.susimsek.auth.security

import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer
import java.util.function.Consumer

class FederatedIdentityIdTokenCustomizer : OAuth2TokenCustomizer<JwtEncodingContext> {
    override fun customize(context: JwtEncodingContext) {
        if (OidcParameterNames.ID_TOKEN == context.tokenType.value) {
            val thirdPartyClaims = extractClaims(context.getPrincipal())
            context.claims.claims { existingClaims: MutableMap<String, Any> ->
                // Remove conflicting claims set by this authorization server
                existingClaims.keys.forEach(Consumer { key: String -> thirdPartyClaims.remove(key) })

                // Remove standard id_token claims that could cause problems with clients
                ID_TOKEN_CLAIMS.forEach(Consumer { key: String -> thirdPartyClaims.remove(key) })

                // Add all other claims directly to id_token
                existingClaims.putAll(thirdPartyClaims)
            }
        }
    }

    private fun extractClaims(principal: Authentication): MutableMap<String, Any> {
        val claims: Map<String, Any> = when (principal.principal) {
            is OidcUser -> {
                val oidcUser = principal.principal as OidcUser
                val idToken = oidcUser.idToken
                idToken.claims
            }
            is OAuth2User -> {
                val oauth2User = principal.principal as OAuth2User
                oauth2User.attributes
            }
            else -> {
                emptyMap()
            }
        }
        return HashMap(claims)
    }

    companion object {
        private val ID_TOKEN_CLAIMS = mutableSetOf(
            IdTokenClaimNames.ISS,
            IdTokenClaimNames.SUB,
            IdTokenClaimNames.AUD,
            IdTokenClaimNames.EXP,
            IdTokenClaimNames.IAT,
            IdTokenClaimNames.AUTH_TIME,
            IdTokenClaimNames.NONCE,
            IdTokenClaimNames.ACR,
            IdTokenClaimNames.AMR,
            IdTokenClaimNames.AZP,
            IdTokenClaimNames.AT_HASH,
            IdTokenClaimNames.C_HASH
        )
    }
}
