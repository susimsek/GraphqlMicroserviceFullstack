package io.github.susimsek.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Configuration

@ConfigurationProperties("auth-server.provider")
@Configuration
@ConstructorBinding
class ProviderProperties {
    var issuer: String = "http://localhost:9000"
}