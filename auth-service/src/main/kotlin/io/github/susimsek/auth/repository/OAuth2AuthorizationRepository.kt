package io.github.susimsek.auth.repository

import io.github.susimsek.auth.domain.Authorization
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.stereotype.Repository

@Repository
interface OAuth2AuthorizationRepository :
    MongoRepository<Authorization, String>,
    OAuth2AuthorizationRepositoryOverride,
    QuerydslPredicateExecutor<Authorization>
