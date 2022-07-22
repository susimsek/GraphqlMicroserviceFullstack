package io.github.susimsek.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.core.io.Resource

@ConfigurationProperties("jwk-set")
@ConstructorBinding
data class JWKSetProperties(
    var keyStore: Resource,
    var keyStorePassword: String
)
