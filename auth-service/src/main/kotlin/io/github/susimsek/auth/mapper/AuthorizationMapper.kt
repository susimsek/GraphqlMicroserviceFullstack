package io.github.susimsek.auth.mapper

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.susimsek.auth.domain.Authorization
import org.springframework.security.jackson2.SecurityJackson2Modules
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.OAuth2AccessToken
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType
import org.springframework.security.oauth2.core.OAuth2AuthorizationCode
import org.springframework.security.oauth2.core.OAuth2RefreshToken
import org.springframework.security.oauth2.core.oidc.OidcIdToken
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module
import org.springframework.stereotype.Component
import org.springframework.util.CollectionUtils
import org.springframework.util.StringUtils
import java.time.Instant
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
        val authorizationCode = oAuth2Authorization.getToken(OAuth2AuthorizationCode::class.java)

        var authorizationCodeValue: String? = null
        var authorizationCodeIssuedAt: OffsetDateTime? = null
        var authorizationCodeExpiresAt: OffsetDateTime? = null
        var authorizationCodeMetadata: String? = null
        if (authorizationCode != null) {
            authorizationCodeValue = authorizationCode.token.tokenValue
            if (authorizationCode.token.issuedAt != null) {
                authorizationCodeIssuedAt =  OffsetDateTime.ofInstant(authorizationCode.token.issuedAt, ZoneId.systemDefault())
            }
            if (authorizationCode.token.expiresAt != null) {
                authorizationCodeExpiresAt = OffsetDateTime.ofInstant(authorizationCode.token.expiresAt, ZoneId.systemDefault())
            }
            authorizationCodeMetadata = writeMap(authorizationCode.metadata)
        }

        val accessToken = oAuth2Authorization.getToken(OAuth2AccessToken::class.java)

        var accessTokenValue: String? = null
        var accessTokenIssuedAt: OffsetDateTime? = null
        var accessTokenExpiresAt: OffsetDateTime? = null
        var accessTokenMetadata: String? = null
        if (accessToken != null) {
            accessTokenValue = accessToken.token.tokenValue
            if (accessToken.token.issuedAt != null) {
                accessTokenIssuedAt =  OffsetDateTime.ofInstant(accessToken.token.issuedAt, ZoneId.systemDefault())
            }
            if (accessToken.token.expiresAt != null) {
                accessTokenExpiresAt = OffsetDateTime.ofInstant(accessToken.token.expiresAt, ZoneId.systemDefault())
            }
            accessTokenMetadata = writeMap(accessToken.metadata)
        }

        var accessTokenType: String? = null
        var accessTokenScopes: MutableSet<String>? = null
        if (accessToken != null) {
            accessTokenType = (accessToken.token as OAuth2AccessToken).tokenType.value
            if (!CollectionUtils.isEmpty((accessToken.token as OAuth2AccessToken).scopes)) {
                accessTokenScopes = (accessToken.token as OAuth2AccessToken).scopes
            }
        }

        val oidcIdToken = oAuth2Authorization.getToken(OidcIdToken::class.java)

        var oidcIdTokenValue: String? = null
        var oidcIdTokenIssuedAt: OffsetDateTime? = null
        var oidcIdTokenExpiresAt: OffsetDateTime? = null
        var oidcIdTokenMetadata: String? = null
        if (oidcIdToken != null) {
            oidcIdTokenValue = oidcIdToken.token.tokenValue
            if (oidcIdToken.token.issuedAt != null) {
                oidcIdTokenIssuedAt =  OffsetDateTime.ofInstant(oidcIdToken.token.issuedAt, ZoneId.systemDefault())
            }
            if (oidcIdToken.token.expiresAt != null) {
                oidcIdTokenExpiresAt = OffsetDateTime.ofInstant(oidcIdToken.token.expiresAt, ZoneId.systemDefault())
            }
            oidcIdTokenMetadata = writeMap(oidcIdToken.metadata)
        }

        val refreshToken = oAuth2Authorization.refreshToken

        var refreshTokenValue: String? = null
        var refreshTokenIssuedAt: OffsetDateTime? = null
        var refreshTokenExpiresAt: OffsetDateTime? = null
        var refreshTokenMetadata: String? = null
        if (refreshToken != null) {
            refreshTokenValue = refreshToken.token.tokenValue
            if (refreshToken.token.issuedAt != null) {
                refreshTokenIssuedAt =  OffsetDateTime.ofInstant(refreshToken.token.issuedAt, ZoneId.systemDefault())
            }
            if (refreshToken.token.expiresAt != null) {
                refreshTokenExpiresAt = OffsetDateTime.ofInstant(refreshToken.token.expiresAt, ZoneId.systemDefault())
            }
            refreshTokenMetadata = writeMap(refreshToken.metadata)
        }


        return Authorization(
            id = oAuth2Authorization.id,
            registeredClientId = oAuth2Authorization.registeredClientId,
            principalName = oAuth2Authorization.principalName,
            authorizationGrantType = oAuth2Authorization.authorizationGrantType.value,
            attributes = attributes,
            state = state,
            authorizationCodeValue = authorizationCodeValue,
            authorizationCodeIssuedAt = authorizationCodeIssuedAt,
            authorizationCodeExpiresAt = authorizationCodeExpiresAt,
            authorizationCodeMetadata = authorizationCodeMetadata,
            accessTokenValue = accessTokenValue,
            accessTokenIssuedAt = accessTokenIssuedAt,
            accessTokenExpiresAt = accessTokenExpiresAt,
            accessTokenMetadata = accessTokenMetadata,
            accessTokenType = accessTokenType,
            accessTokenScopes = accessTokenScopes,
            oidcIdTokenValue = oidcIdTokenValue,
            oidcIdTokenIssuedAt = oidcIdTokenIssuedAt,
            oidcIdTokenExpiresAt = oidcIdTokenExpiresAt,
            oidcIdTokenMetadata = oidcIdTokenMetadata,
            refreshTokenValue = refreshTokenValue,
            refreshTokenIssuedAt = refreshTokenIssuedAt,
            refreshTokenExpiresAt = refreshTokenExpiresAt,
            refreshTokenMetadata = refreshTokenMetadata
        )
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

        val authorizationCodeValue: String? = authorization.authorizationCodeValue
        var tokenIssuedAt: Instant?
        var tokenExpiresAt: Instant?
        if (StringUtils.hasText(authorizationCodeValue)) {
            tokenIssuedAt = authorization.authorizationCodeIssuedAt?.toInstant()
            tokenExpiresAt = authorization.authorizationCodeExpiresAt?.toInstant()
            var authorizationCodeMetadata: Map<String, Any> = emptyMap()
            val authCodeMetadata = authorization.authorizationCodeMetadata
            if (authCodeMetadata != null) {
                authorizationCodeMetadata = parseMap(authCodeMetadata)
            }

            val authorizationCode = OAuth2AuthorizationCode(authorizationCodeValue, tokenIssuedAt, tokenExpiresAt)
            builder.token(
                authorizationCode
            ) { metadata ->
                metadata.putAll(
                    authorizationCodeMetadata
                )
            }
        }

        val accessTokenValue: String? = authorization.accessTokenValue
        if (StringUtils.hasText(accessTokenValue)) {
            tokenIssuedAt = authorization.accessTokenIssuedAt?.toInstant()
            tokenExpiresAt = authorization.accessTokenExpiresAt?.toInstant()
            var accessTokenMetadata: Map<String, Any> = emptyMap()
            val authAccessTokenMetadata = authorization.accessTokenMetadata
            if (authAccessTokenMetadata != null) {
                accessTokenMetadata = parseMap(authAccessTokenMetadata)
            }

            var tokenType: TokenType? = null
            if (TokenType.BEARER.value.equals(authorization.accessTokenType, ignoreCase = true)) {
                tokenType = TokenType.BEARER
            }
            var scopes: Set<String?> = emptySet<String>()
            val authAccessTokenScopes = authorization.accessTokenScopes
            if (authAccessTokenScopes != null) {
                scopes = authAccessTokenScopes
            }
            val accessToken = OAuth2AccessToken(tokenType, accessTokenValue, tokenIssuedAt, tokenExpiresAt, scopes)
            builder.token(
                accessToken
            ) { metadata ->
                metadata.putAll(
                    accessTokenMetadata
                )
            }
        }

        val oidcIdTokenValue: String? = authorization.oidcIdTokenValue
        if (StringUtils.hasText(oidcIdTokenValue)) {
            tokenIssuedAt = authorization.oidcIdTokenIssuedAt?.toInstant()
            tokenExpiresAt = authorization.oidcIdTokenExpiresAt?.toInstant()
            var oidcTokenMetadata: Map<String, Any> = emptyMap()
            val authOidcIdTokenMetadata = authorization.oidcIdTokenMetadata
            if (authOidcIdTokenMetadata!= null) {
                oidcTokenMetadata = parseMap(authOidcIdTokenMetadata)
            }

            val claims = oidcTokenMetadata[OAuth2Authorization.Token.CLAIMS_METADATA_NAME] as MutableMap<String, Any>

            val oidcToken = OidcIdToken(
                oidcIdTokenValue,
                tokenIssuedAt,
                tokenExpiresAt,
                claims
            )
            builder.token(
                oidcToken
            ) { metadata ->
                metadata.putAll(
                    oidcTokenMetadata
                )
            }
        }

        val refreshTokenValue: String? = authorization.refreshTokenValue
        if (StringUtils.hasText(refreshTokenValue)) {
            tokenIssuedAt = authorization.refreshTokenIssuedAt?.toInstant()
            tokenExpiresAt = authorization.refreshTokenExpiresAt?.toInstant()
            var refreshTokenMetadata: Map<String, Any> = emptyMap()
            val authRefreshTokenMetadata = authorization.refreshTokenMetadata
            if (authRefreshTokenMetadata != null) {
                refreshTokenMetadata = parseMap(authRefreshTokenMetadata)
            }

            val refreshToken = OAuth2RefreshToken(refreshTokenValue, tokenIssuedAt, tokenExpiresAt)
            builder.token(
                refreshToken
            ) { metadata ->
                metadata.putAll(
                    refreshTokenMetadata
                )
            }
        }

        return builder.build()
    }

    private fun resolveClientAuthenticationMethod(clientAuthenticationMethod: String): ClientAuthenticationMethod {
        return if (ClientAuthenticationMethod.CLIENT_SECRET_BASIC.value == clientAuthenticationMethod) {
            ClientAuthenticationMethod.CLIENT_SECRET_BASIC
        } else if (ClientAuthenticationMethod.CLIENT_SECRET_POST.value == clientAuthenticationMethod) {
            ClientAuthenticationMethod.CLIENT_SECRET_POST
        } else {
            if (ClientAuthenticationMethod.NONE.value == clientAuthenticationMethod) ClientAuthenticationMethod.NONE else ClientAuthenticationMethod(
                clientAuthenticationMethod
            )
        }
    }

    private fun resolveAuthorizationGrantType(authorizationGrantType: String): AuthorizationGrantType? {
        return if (AuthorizationGrantType.AUTHORIZATION_CODE.value == authorizationGrantType) {
            AuthorizationGrantType.AUTHORIZATION_CODE
        } else if (AuthorizationGrantType.CLIENT_CREDENTIALS.value == authorizationGrantType) {
            AuthorizationGrantType.CLIENT_CREDENTIALS
        } else {
            if (AuthorizationGrantType.REFRESH_TOKEN.value == authorizationGrantType) AuthorizationGrantType.REFRESH_TOKEN else AuthorizationGrantType(
                authorizationGrantType
            )
        }
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
}