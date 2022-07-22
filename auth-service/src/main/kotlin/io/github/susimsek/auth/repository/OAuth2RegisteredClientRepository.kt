package io.github.susimsek.auth.repository

import io.github.susimsek.auth.domain.Client
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface OAuth2RegisteredClientRepository : MongoRepository<Client, String>{
    fun findByClientId(clientId: String): Optional<Client>
}