package io.github.susimsek.auth.repository

import io.github.susimsek.auth.domain.AuthorizationConsent
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface OAuth2AuthorizationConsentRepository : MongoRepository<AuthorizationConsent, String>,
    QuerydslPredicateExecutor<AuthorizationConsent> {
    fun findByRegisteredClientId(clientId: String): Optional<AuthorizationConsent>
    fun findByRegisteredClientIdAndPrincipalName(registeredClientId: String, principalName: String): Optional<AuthorizationConsent>
    fun deleteByRegisteredClientIdAndPrincipalName(registeredClientId: String, principalName: String)
}