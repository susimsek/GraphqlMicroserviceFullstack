package io.github.susimsek.auth.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.CollectionUtils
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CorsProperties::class)
class CorsConfig {
    @Bean
    fun corsFilter(corsProperties: CorsProperties): CorsFilter {
        val source = UrlBasedCorsConfigurationSource()
        if (!CollectionUtils.isEmpty(corsProperties.allowedOrigins)) {
            val config = CorsConfiguration()
            config.allowedOrigins = corsProperties.allowedOrigins
            config.allowedMethods = corsProperties.allowedMethods
            config.allowedHeaders = corsProperties.allowedHeaders
            config.exposedHeaders = corsProperties.exposedHeaders
            config.allowCredentials = corsProperties.allowCredentials
            config.maxAge = corsProperties.maxAge

            source.apply {
                registerCorsConfiguration("/**", config)
            }
        }
        return CorsFilter(source)
    }
}
