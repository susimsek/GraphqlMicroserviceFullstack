package io.github.susimsek.auth.repository

import io.github.susimsek.auth.domain.Authority
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface OAuth2AuthorityRepository : MongoRepository<Authority, String>, QuerydslPredicateExecutor<Authority> {
    fun findByName(name: String): Optional<Authority>
}
