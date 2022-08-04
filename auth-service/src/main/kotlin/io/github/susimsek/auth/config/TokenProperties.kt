package io.github.susimsek.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConfigurationProperties("token")
@ConstructorBinding
data class TokenProperties(
    var accessTokenTimeToLive: Duration = Duration.ofMinutes(5),
    var refreshTokenTimeToLive: Duration = Duration.ofMinutes(5)
)
