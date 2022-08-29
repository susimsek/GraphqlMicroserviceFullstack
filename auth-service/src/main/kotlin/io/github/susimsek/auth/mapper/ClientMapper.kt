package io.github.susimsek.auth.mapper

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.susimsek.auth.domain.Client
import org.springframework.security.jackson2.SecurityJackson2Modules
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.OAuth2TokenFormat
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.config.ClientSettings
import org.springframework.security.oauth2.server.authorization.config.ConfigurationSettingNames
import org.springframework.security.oauth2.server.authorization.config.TokenSettings
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module
import org.springframework.stereotype.Component
import java.time.Clock
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.function.Consumer

@Component
class ClientMapper {

    private var objectMapper = jacksonObjectMapper()

    init {
        val classLoader = ClientMapper::class.java.classLoader
        val securityModules = SecurityJackson2Modules.getModules(classLoader)
        objectMapper.registerModules(securityModules)
        objectMapper.registerModule(OAuth2AuthorizationServerJackson2Module())
    }

    fun toClient(registeredClient: RegisteredClient): Client {

        val clientIdIssuedAt: OffsetDateTime = if (registeredClient.clientIdIssuedAt != null) {
            OffsetDateTime.ofInstant(registeredClient.clientIdIssuedAt, ZoneId.systemDefault())
        } else {
            OffsetDateTime.now(Clock.systemDefaultZone())
        }

        val clientSecretExpiresAt: OffsetDateTime? = if (registeredClient.clientSecretExpiresAt != null) {
            OffsetDateTime.ofInstant(registeredClient.clientSecretExpiresAt, ZoneId.systemDefault())
        } else {
            null
        }

        val clientAuthenticationMethods: MutableSet<String> = mutableSetOf()
        registeredClient.clientAuthenticationMethods.forEach(
            Consumer { clientAuthenticationMethod: ClientAuthenticationMethod ->
            clientAuthenticationMethods.add(
                clientAuthenticationMethod.value
            )
        }
        )
        val authorizationGrantTypes: MutableSet<String> = mutableSetOf()
        registeredClient.authorizationGrantTypes.forEach(
            Consumer { authorizationGrantType: AuthorizationGrantType ->
            authorizationGrantTypes.add(
                authorizationGrantType.value
            )
        }
        )

        return Client(
            id = registeredClient.id,
            clientId = registeredClient.clientId,
            clientIdIssuedAt = clientIdIssuedAt,
            clientSecret = registeredClient.clientSecret,
            clientSecretExpiresAt = clientSecretExpiresAt,
            clientName = registeredClient.clientName,
            clientAuthenticationMethods = clientAuthenticationMethods,
            authorizationGrantTypes = authorizationGrantTypes,
            redirectUris = registeredClient.redirectUris,
            scopes = registeredClient.scopes,
            clientSettings = writeMap(registeredClient.clientSettings.settings),
            tokenSettings = writeMap(registeredClient.tokenSettings.settings)
        )
    }

    fun toRegisteredClient(client: Client): RegisteredClient {

        val clientIdIssuedAt = client.clientIdIssuedAt
        val clientSecretExpiresAt = client.clientSecretExpiresAt
        val clientAuthenticationMethods = client.clientAuthenticationMethods
        val authorizationGrantTypes = client.authorizationGrantTypes
        val redirectUris = client.redirectUris
        val clientScopes = client.scopes
        val builder = RegisteredClient.withId(client.id).clientId(client.clientId)
            .clientIdIssuedAt(clientIdIssuedAt.toInstant())
            .clientSecret(client.clientSecret)
            .clientSecretExpiresAt(clientSecretExpiresAt?.toInstant())
            .clientName(client.clientName)
            .clientAuthenticationMethods { authenticationMethods: MutableSet<ClientAuthenticationMethod> ->
                clientAuthenticationMethods.forEach(
                    Consumer { authenticationMethod: String ->
                        authenticationMethods.add(
                            resolveClientAuthenticationMethod(authenticationMethod)
                        )
                    }
                )
            }.authorizationGrantTypes { grantTypes: MutableSet<AuthorizationGrantType?> ->
                authorizationGrantTypes.forEach(
                    Consumer { grantType: String ->
                        grantTypes.add(
                            resolveAuthorizationGrantType(grantType)
                        )
                    }
                )
            }.redirectUris { uris: MutableSet<String> ->
                uris.addAll(
                    redirectUris
                )
            }.scopes { scopes: MutableSet<String> ->
                scopes.addAll(
                    clientScopes
                )
            }
        val clientSettingsMap: Map<String, Any> = parseMap(client.clientSettings)
        builder.clientSettings(ClientSettings.withSettings(clientSettingsMap).build())
        val tokenSettingsMap: Map<String, Any> = parseMap(client.tokenSettings)
        val tokenSettingsBuilder = TokenSettings.withSettings(tokenSettingsMap)
        if (!tokenSettingsMap.containsKey(ConfigurationSettingNames.Token.ACCESS_TOKEN_FORMAT)) {
            tokenSettingsBuilder.accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
        }

        builder.tokenSettings(tokenSettingsBuilder.build())
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
