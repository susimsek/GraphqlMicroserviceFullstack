package io.github.susimsek.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Configuration

@ConfigurationProperties("security-matcher")
@Configuration
@ConstructorBinding
data class SecurityMatcherProperties(
    var ignorePatterns: MutableList<String> = mutableListOf("/favicon.ico", "/assets/**", "/webjars/**"),
    var permitAllPatterns: MutableList<String> = mutableListOf("/actuator/health/**")
)
