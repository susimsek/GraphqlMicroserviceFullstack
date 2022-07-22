package io.github.susimsek.auth.repository

import io.github.susimsek.auth.domain.Authorization
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.security.oauth2.core.OAuth2TokenType
import java.util.Optional


class OAuth2AuthorizationRepositoryOverrideImpl(
    private val mongoTemplate: MongoTemplate
) : OAuth2AuthorizationRepositoryOverride {

    override fun findByToken(token: String, tokenType: OAuth2TokenType?): Optional<Authorization> {
        val query = Query()

        if (tokenType == null) {
            query.addCriteria(
                Criteria().orOperator(
                    Criteria.where("state").`is`(token),
                    Criteria.where("authorizationCodeValue").`is`(token),
                    Criteria.where("accessTokenValue").`is`(token),
                    Criteria.where("refreshTokenValue").`is`(token)
                )
            )
            return Optional.ofNullable(mongoTemplate.findOne(query, Authorization::class.java))
        } else if ("state" == tokenType.value) {
            query.addCriteria(Criteria.where("state").`is`(token))
            return Optional.ofNullable(mongoTemplate.findOne(query, Authorization::class.java))
        } else if ("code" == tokenType.value) {
            query.addCriteria(Criteria.where("authorizationCodeValue").`is`(token))
            return Optional.ofNullable(mongoTemplate.findOne(query, Authorization::class.java))
        } else if (OAuth2TokenType.ACCESS_TOKEN == tokenType) {
            query.addCriteria(Criteria.where("accessTokenValue").`is`(token))
            return Optional.ofNullable(mongoTemplate.findOne(query, Authorization::class.java))
        } else if (OAuth2TokenType.REFRESH_TOKEN == tokenType) {
            query.addCriteria(Criteria.where("refreshTokenValue").`is`(token))
            return Optional.ofNullable(mongoTemplate.findOne(query, Authorization::class.java))
        } else {
            return Optional.empty()
        }
    }
}
