package io.github.susimsek.auth.service

import io.github.susimsek.auth.domain.Client
import io.github.susimsek.auth.mapper.ClientMapper
import io.github.susimsek.auth.repository.OAuth2RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.util.Assert

class MongodbRegisteredClientService(
    private val oauth2RegisteredClientRepository: OAuth2RegisteredClientRepository,
    private val registeredClientMapper: ClientMapper
) : RegisteredClientRepository {


    override fun save(registeredClient: RegisteredClient) {
        Assert.notNull(registeredClient, "registeredClient cannot be null")
        val existingRegisteredClient = oauth2RegisteredClientRepository.findById(registeredClient.id).orElse(null)
        if (existingRegisteredClient != null) {
            this.updateRegisteredClient(existingRegisteredClient, registeredClient)
        } else {
            this.insertRegisteredClient(registeredClient)
        }
    }

    override fun findById(id: String): RegisteredClient? {
        Assert.hasText(id, "id cannot be empty")
        return oauth2RegisteredClientRepository.findById(id)
            .map(registeredClientMapper::toRegisteredClient)
            .orElse(null)
    }

    override fun findByClientId(clientId: String): RegisteredClient? {
        Assert.hasText(clientId, "clientId cannot be empty")
        return oauth2RegisteredClientRepository.findByClientId(clientId)
            .map(registeredClientMapper::toRegisteredClient)
            .orElse(null)
    }

    private fun insertRegisteredClient(registeredClient: RegisteredClient) {
        val client = registeredClientMapper.toClient(registeredClient)
        oauth2RegisteredClientRepository.save(client)
    }

    private fun updateRegisteredClient(existingRegisteredClient: Client, registeredClient: RegisteredClient) {
        val oauth2RegisteredClient = registeredClientMapper.toClient(registeredClient)
        existingRegisteredClient.clientName = oauth2RegisteredClient.clientName
        existingRegisteredClient.clientAuthenticationMethods = oauth2RegisteredClient.clientAuthenticationMethods
        existingRegisteredClient.authorizationGrantTypes = oauth2RegisteredClient.authorizationGrantTypes
        existingRegisteredClient.redirectUris = oauth2RegisteredClient.redirectUris
        existingRegisteredClient.scopes = oauth2RegisteredClient.scopes
        existingRegisteredClient.clientSettings = oauth2RegisteredClient.clientSettings
        existingRegisteredClient.tokenSettings = oauth2RegisteredClient.tokenSettings
        oauth2RegisteredClientRepository.save(existingRegisteredClient)
    }
}