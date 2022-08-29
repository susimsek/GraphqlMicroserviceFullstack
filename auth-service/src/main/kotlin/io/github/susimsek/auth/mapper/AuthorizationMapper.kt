package io.github.susimsek.auth.mapper

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.susimsek.auth.domain.Authorization
import org.springframework.security.jackson2.SecurityJackson2Modules
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType
import org.springframework.security.oauth2.core.OAuth2AuthorizationCode
import org.springframework.security.oauth2.core.OAuth2RefreshToken
import org.springframework.security.oauth2.core.OAuth2Token
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module
import org.springframework.stereotype.Component
import org.springframework.util.CollectionUtils
import org.springframework.util.StringUtils
import java.time.OffsetDateTime
import java.time.ZoneId

@Component
class AuthorizationMapper {

    private var objectMapper = jacksonObjectMapper()

    init {
        val classLoader = AuthorizationMapper::class.java.classLoader
        val securityModules = SecurityJackson2Modules.getModules(classLoader)
        objectMapper.registerModules(securityModules)
        objectMapper.registerModules(OAuth2AuthorizationServerJackson2Module())
    }

    fun toAuthorization(oAuth2Authorization: OAuth2Authorization): Authorization {

        val attributes = writeMap(oAuth2Authorization.attributes)
        var state: String? = null
        val authorizationState = oAuth2Authorization.getAttribute<String>("state")
        if (StringUtils.hasText(authorizationState)) {
            state = authorizationState
        }

        val authorization = Authorization(
            id = oAuth2Authorization.id,
            registeredClientId = oAuth2Authorization.registeredClientId,
            principalName = oAuth2Authorization.principalName,
            authorizationGrantType = oAuth2Authorization.authorizationGrantType.value,
            attributes = attributes,
            state = state
        )

        val authorizationCode = oAuth2Authorization.getToken(OAuth2AuthorizationCode::class.java)
        setTokensToAuthorization(authorization, "authorization_code", authorizationCode)

        val accessToken = oAuth2Authorization.getToken(OAuth2AccessToken::class.java)
        setTokensToAuthorization(authorization, "access_token", accessToken)

        var accessTokenType: String? = null
        var accessTokenScopes: MutableSet<String>? = null
        if (accessToken != null) {
            accessTokenType = (accessToken.token as OAuth2AccessToken).tokenType.value
            if (!CollectionUtils.isEmpty((accessToken.token as OAuth2AccessToken).scopes)) {
                accessTokenScopes = (accessToken.token as OAuth2AccessToken).scopes
            }
        }

        authorization.accessTokenType = accessTokenType
        authorization.accessTokenScopes = accessTokenScopes

        val oidcIdToken = oAuth2Authorization.getToken(OidcIdToken::class.java)
        setTokensToAuthorization(authorization, "oidc_token", oidcIdToken)

        val refreshToken = oAuth2Authorization.refreshToken
        setTokensToAuthorization(authorization, "refresh_token", refreshToken)

        return authorization
    }

    fun toOAuth2Authorization(client: RegisteredClient, authorization: Authorization): OAuth2Authorization {

        val builder = OAuth2Authorization.withRegisteredClient(client)
        val id: String = authorization.id
        val principalName: String = authorization.principalName
        val authorizationGrantType: String = authorization.authorizationGrantType
        var attributes: Map<String, Any> = emptyMap()
        val authAttributes = authorization.attributes
        if (authAttributes != null) {
            attributes = parseMap(authAttributes)
        }

        builder.id(id).principalName(principalName)
            .authorizationGrantType(AuthorizationGrantType(authorizationGrantType))
            .attributes { attrs: MutableMap<String, Any> ->
                attrs.putAll(
                    attributes
                )
            }

        val state: String? = authorization.state
        if (StringUtils.hasText(state)) {
            builder.attribute("state", state)
        }

        setTokenToOAuth2AuthorizationBuilder(
            builder,
                Token(
                tokenName = "authorization_code",
                tokenValue = authorization.authorizationCodeValue,
                issuedAt = authorization.authorizationCodeIssuedAt,
                expiresAt = authorization.authorizationCodeExpiresAt,
                metadata = authorization.authorizationCodeMetadata
            )
        )

        setTokenToOAuth2AuthorizationBuilder(
            builder,
                Token(
                tokenName = "access_token",
                tokenValue = authorization.accessTokenValue,
                issuedAt = authorization.accessTokenIssuedAt,
                expiresAt = authorization.accessTokenExpiresAt,
                metadata = authorization.accessTokenMetadata,
                tokenType = authorization.accessTokenType,
                tokenScopes = authorization.accessTokenScopes
            )
        )

        setTokenToOAuth2AuthorizationBuilder(
            builder,
                Token(
                tokenName = "oidc_token",
                tokenValue = authorization.oidcIdTokenValue,
                issuedAt = authorization.oidcIdTokenIssuedAt,
                expiresAt = authorization.oidcIdTokenExpiresAt,
                metadata = authorization.oidcIdTokenMetadata
            )
        )

        setTokenToOAuth2AuthorizationBuilder(
            builder,
                Token(
                tokenName = "refresh_token",
                tokenValue = authorization.refreshTokenValue,
                issuedAt = authorization.refreshTokenIssuedAt,
                expiresAt = authorization.refreshTokenExpiresAt,
                metadata = authorization.refreshTokenMetadata
            )
        )

        return builder.build()
    }

    private fun parseMap(data: String): Map<String, Any> {
        return try {
            objectMapper.readValue(data)
        } catch (var3: Exception) {
            throw IllegalArgumentException(var3.message, var3)
        }
    }

    private fun writeMap(data: Map<String, Any>): String {
        return try {
            objectMapper.writeValueAsString(data)
        } catch (var3: Exception) {
            throw IllegalArgumentException(var3.message, var3)
        }
    }

    private fun setTokensToAuthorization(
        authorization: Authorization,
        tokenName: String,
        token: OAuth2Authorization.Token<out OAuth2Token>?
    ) {
        var tokenValue: String? = null
        var issuedAt: OffsetDateTime? = null
        var expiresAt: OffsetDateTime? = null
        var metadata: String? = null
        if (token != null) {
            tokenValue = token.token.tokenValue
            if (token.token.issuedAt != null) {
                issuedAt =
                    OffsetDateTime.ofInstant(token.token.issuedAt, ZoneId.systemDefault())
            }
            if (token.token.expiresAt != null) {
                expiresAt =
                    OffsetDateTime.ofInstant(token.token.expiresAt, ZoneId.systemDefault())
            }
            metadata = writeMap(token.metadata)
        }

        when (tokenName) {
            "authorization_code" -> {
                authorization.authorizationCodeValue = tokenValue
                authorization.authorizationCodeIssuedAt = issuedAt
                authorization.authorizationCodeExpiresAt = expiresAt
                authorization.authorizationCodeMetadata = metadata
            }
            "access_token" -> {
                authorization.accessTokenValue = tokenValue
                authorization.accessTokenIssuedAt = issuedAt
                authorization.accessTokenExpiresAt = expiresAt
                authorization.accessTokenMetadata = metadata
            }
            "oidc_token" -> {
                authorization.oidcIdTokenValue = tokenValue
                authorization.oidcIdTokenIssuedAt = issuedAt
                authorization.oidcIdTokenExpiresAt = expiresAt
                authorization.oidcIdTokenMetadata = metadata
            }
            "refresh_token" -> {
                authorization.refreshTokenValue = tokenValue
                authorization.refreshTokenIssuedAt = issuedAt
                authorization.refreshTokenExpiresAt = expiresAt
                authorization.refreshTokenMetadata = metadata
            }
        }
    }

    private fun setTokenToOAuth2AuthorizationBuilder(
        builder: OAuth2Authorization.Builder,
                                                     token: Token
                        ) {
        if (StringUtils.hasText(token.tokenValue)) {
            val tokenIssuedAt = token.issuedAt?.toInstant()
            val tokenExpiresAt = token.expiresAt?.toInstant()
            var tokenMetadata: Map<String, Any> = emptyMap()
            if (token.metadata != null) {
                tokenMetadata = parseMap(token.metadata!!)
            }

            val oAuth2Token = when (token.tokenName) {
                "authorization_code" -> OAuth2AuthorizationCode(token.tokenValue, tokenIssuedAt, tokenExpiresAt)
                "access_token" -> {
                    var type: TokenType? = null
                    if (TokenType.BEARER.value.equals(token.tokenType, ignoreCase = true)) {
                        type = TokenType.BEARER
                    }
                    var scopes: Set<String?> = emptySet<String>()
                    if (token.tokenScopes != null) {
                        scopes = token.tokenScopes!!
                    }
                    OAuth2AccessToken(type, token.tokenValue, tokenIssuedAt, tokenExpiresAt, scopes)
                }
                "oidc_token" -> {
                    val claims = tokenMetadata[OAuth2Authorization.Token.CLAIMS_METADATA_NAME] as MutableMap<String, Any>
                    OidcIdToken(
                        token.tokenValue,
                        tokenIssuedAt,
                        tokenExpiresAt,
                        claims
                    )
                }
                "refresh_token" -> OAuth2RefreshToken(token.tokenValue, tokenIssuedAt, tokenExpiresAt)
                else -> OAuth2AuthorizationCode(token.tokenValue, tokenIssuedAt, tokenExpiresAt)
            }
            builder.token(
                oAuth2Token
            ) { m ->
                m.putAll(
                    tokenMetadata
                )
            }
        }
    }

    data class Token(
        var tokenName: String,
        var tokenValue: String?,
        var issuedAt: OffsetDateTime?,
        var expiresAt: OffsetDateTime?,
        var metadata: String?,
        var tokenType: String? = null,
        var tokenScopes: MutableSet<String>? = null
    )
}
