package io.github.susimsek.auth.service

import io.github.susimsek.auth.domain.Authorization
import io.github.susimsek.auth.mapper.AuthorizationMapper
import io.github.susimsek.auth.repository.OAuth2AuthorizationRepository
import org.springframework.dao.DataRetrievalFailureException
import org.springframework.security.oauth2.core.OAuth2TokenType
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.util.Assert

class MongodbOAuth2AuthorizationService(
    private val registeredClientRepository: RegisteredClientRepository,
    private val oauth2AuthorizationRepository: OAuth2AuthorizationRepository,
    private val authorizationMapper: AuthorizationMapper
) : OAuth2AuthorizationService {

    override fun save(authorization: OAuth2Authorization) {
        Assert.notNull(authorization, "authorization cannot be null")
        val existingAuthorization = oauth2AuthorizationRepository.findById(authorization.id).orElse(null)
        if (existingAuthorization == null) {
            this.insertAuthorization(authorization)
        } else {
            this.updateAuthorization(existingAuthorization, authorization)
        }
    }

    override fun remove(authorization: OAuth2Authorization) {
        Assert.notNull(authorization, "authorization cannot be null")
        oauth2AuthorizationRepository.deleteById(authorization.id)
    }

    override fun findById(id: String): OAuth2Authorization? {
        Assert.hasText(id, "id cannot be empty")
        val entity = oauth2AuthorizationRepository.findById(id).orElse(null) ?: return null
        val registeredClient = registeredClientRepository.findById(entity.registeredClientId)
            ?: throw DataRetrievalFailureException("The RegisteredClient with id '${entity.registeredClientId}' was not found in the RegisteredClientRepository.")
        return authorizationMapper.toOAuth2Authorization(registeredClient, entity)
    }

    override fun findByToken(token: String, tokenType: OAuth2TokenType?): OAuth2Authorization? {
        Assert.hasText(token, "token cannot be empty")
        val entity = oauth2AuthorizationRepository.findByToken(token, tokenType).orElse(null) ?: return null
        val registeredClient = registeredClientRepository.findById(entity.registeredClientId)
            ?: throw DataRetrievalFailureException("The RegisteredClient with id '${entity.registeredClientId}' was not found in the RegisteredClientRepository.")
        return authorizationMapper.toOAuth2Authorization(registeredClient, entity)
    }


    private fun insertAuthorization(oAuth2Authorization: OAuth2Authorization) {
        val authorization = authorizationMapper.toAuthorization(oAuth2Authorization)
        oauth2AuthorizationRepository.save(authorization)
    }

    private fun updateAuthorization(existingAuthorization: Authorization, oAuth2Authorization: OAuth2Authorization) {
        val authorization = authorizationMapper.toAuthorization(oAuth2Authorization)
        existingAuthorization.registeredClientId = authorization.registeredClientId
        existingAuthorization.principalName = authorization.principalName
        existingAuthorization.authorizationGrantType = authorization.authorizationGrantType
        existingAuthorization.attributes = authorization.attributes
        existingAuthorization.state = authorization.state
        existingAuthorization.authorizationCodeValue = authorization.authorizationCodeValue
        existingAuthorization.authorizationCodeIssuedAt = authorization.authorizationCodeIssuedAt
        existingAuthorization.authorizationCodeExpiresAt = authorization.authorizationCodeExpiresAt
        existingAuthorization.authorizationCodeMetadata = authorization.authorizationCodeMetadata
        existingAuthorization.accessTokenValue = authorization.accessTokenValue
        existingAuthorization.accessTokenIssuedAt = authorization.accessTokenIssuedAt
        existingAuthorization.accessTokenExpiresAt = authorization.accessTokenExpiresAt
        existingAuthorization.accessTokenMetadata = authorization.accessTokenMetadata
        existingAuthorization.accessTokenType = authorization.accessTokenType
        existingAuthorization.accessTokenScopes = authorization.accessTokenScopes
        existingAuthorization.oidcIdTokenValue = authorization.oidcIdTokenValue
        existingAuthorization.oidcIdTokenIssuedAt = authorization.oidcIdTokenIssuedAt
        existingAuthorization.oidcIdTokenExpiresAt = authorization.oidcIdTokenExpiresAt
        existingAuthorization.oidcIdTokenMetadata = authorization.oidcIdTokenMetadata
        existingAuthorization.refreshTokenValue = authorization.refreshTokenValue
        existingAuthorization.refreshTokenIssuedAt = authorization.refreshTokenIssuedAt
        existingAuthorization.refreshTokenExpiresAt = authorization.refreshTokenExpiresAt
        existingAuthorization.refreshTokenMetadata = authorization.refreshTokenMetadata
        oauth2AuthorizationRepository.save(existingAuthorization)

    }
}