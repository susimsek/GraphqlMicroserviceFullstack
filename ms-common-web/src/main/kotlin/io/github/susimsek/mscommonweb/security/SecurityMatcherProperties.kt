package io.github.susimsek.mscommonweb.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("security-matcher")
@ConstructorBinding
data class SecurityMatcherProperties(
    var ignorePatterns: MutableList<String> = mutableListOf("/graphiql"),
    var permitAllPatterns: MutableList<String> = mutableListOf("/actuator/health/**", "/graphql")
)
