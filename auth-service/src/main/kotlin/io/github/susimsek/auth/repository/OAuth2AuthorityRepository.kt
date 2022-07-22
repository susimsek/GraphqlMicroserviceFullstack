package io.github.susimsek.auth.repository

import io.github.susimsek.auth.domain.Authority
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface OAuth2AuthorityRepository : MongoRepository<Authority, String>{
    fun findByName(name: String): Optional<Authority>
}