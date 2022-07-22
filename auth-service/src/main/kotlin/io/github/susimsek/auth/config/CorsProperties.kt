package io.github.susimsek.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("cors")
@ConstructorBinding
data class CorsProperties(
    var allowedOrigins: MutableList<String> = mutableListOf(),
    var allowedMethods: MutableList<String> = mutableListOf(),
    var allowedHeaders: MutableList<String> = mutableListOf(),
    var exposedHeaders: MutableList<String> = mutableListOf(),
    var allowCredentials: Boolean = false,
    var maxAge: Long = 3600
)
