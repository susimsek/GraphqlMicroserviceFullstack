package io.github.susimsek.product.config

import org.mockito.Mockito.mock
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder


@TestConfiguration
class TestSecurityConfig {

    @Bean
    fun jwtDecoder(): ReactiveJwtDecoder {
        return mock(ReactiveJwtDecoder::class.java)
    }
}