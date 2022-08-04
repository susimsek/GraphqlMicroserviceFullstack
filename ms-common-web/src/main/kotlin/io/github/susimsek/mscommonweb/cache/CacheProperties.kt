package io.github.susimsek.mscommonweb.cache

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration


@ConfigurationProperties(prefix = "cache")
@ConstructorBinding
data class CacheProperties(
    var expiration: Duration = Duration.ofHours(1)
)

