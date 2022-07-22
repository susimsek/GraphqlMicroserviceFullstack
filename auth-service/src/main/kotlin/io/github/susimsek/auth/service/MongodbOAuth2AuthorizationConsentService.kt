package io.github.susimsek.auth.service

import io.github.susimsek.auth.domain.AuthorizationConsent
import io.github.susimsek.auth.mapper.AuthorizationConsentMapper
import io.github.susimsek.auth.repository.OAuth2AuthorizationConsentRepository
import org.springframework.dao.DataRetrievalFailureException
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.util.Assert

class MongodbOAuth2AuthorizationConsentService(
    private val registeredClientRepository: RegisteredClientRepository,
    private val oAuth2AuthorizationConsentRepository: OAuth2AuthorizationConsentRepository,
    private val authorizationConsentMapper: AuthorizationConsentMapper
) : OAuth2AuthorizationConsentService {
    override fun save(authorizationConsent: OAuth2AuthorizationConsent) {
        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null")
        val existingAuthorizationConsent = oAuth2AuthorizationConsentRepository.findById(authorizationConsent.registeredClientId).orElse(null)
        if (existingAuthorizationConsent != null) {
            this.updateAuthorizationConsent(existingAuthorizationConsent, authorizationConsent)
        } else {
            this.insertAuthorizationConsent(authorizationConsent)
        }
    }

    override fun remove(authorizationConsent: OAuth2AuthorizationConsent) {
        Assert.notNull(authorizationConsent, "authorizationConsent cannot be null")
        oAuth2AuthorizationConsentRepository.deleteByRegisteredClientIdAndPrincipalName(
            authorizationConsent.registeredClientId, authorizationConsent.principalName)
    }

    override fun findById(registeredClientId: String, principalName: String): OAuth2AuthorizationConsent? {
        Assert.hasText(registeredClientId, "registeredClientId cannot be empty")
        Assert.hasText(principalName, "principalName cannot be empty")
        val entity = oAuth2AuthorizationConsentRepository.findByRegisteredClientIdAndPrincipalName(
            registeredClientId, principalName).orElse(null) ?: return null
        val registeredClient = registeredClientRepository.findById(entity.registeredClientId)
            ?: throw DataRetrievalFailureException("The RegisteredClient with id '${entity.registeredClientId}' was not found in the RegisteredClientRepository.")
        return authorizationConsentMapper.toOAuth2AuthorizationConsent(registeredClient, entity)
    }

    private fun insertAuthorizationConsent(oAuth2AuthorizationConsent: OAuth2AuthorizationConsent) {
        val authorizationConsent = authorizationConsentMapper.toAuthorizationConsent(oAuth2AuthorizationConsent)
        oAuth2AuthorizationConsentRepository.save(authorizationConsent)
    }

    private fun updateAuthorizationConsent(existingAuthorizationConsent: AuthorizationConsent, oAuth2AuthorizationConsent: OAuth2AuthorizationConsent) {
        val authorizationConsent = authorizationConsentMapper.toAuthorizationConsent(oAuth2AuthorizationConsent)
        existingAuthorizationConsent.authorities = authorizationConsent.authorities
        oAuth2AuthorizationConsentRepository.save(existingAuthorizationConsent)
    }
}