package io.github.susimsek.mscommonweb.security

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.OrServerWebExchangeMatcher
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers

@Configuration(proxyBeanMethods = false)
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@EnableConfigurationProperties(SecurityMatcherProperties::class)
class WebSecurityAutoConfiguration(
    private val securityMatcherProperties: SecurityMatcherProperties
) {

    @Bean
    fun springWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
            .securityMatcher(
                NegatedServerWebExchangeMatcher(
                    OrServerWebExchangeMatcher(
                        ServerWebExchangeMatchers.pathMatchers(*securityMatcherProperties.ignorePatterns.toTypedArray()),
                        ServerWebExchangeMatchers.pathMatchers(HttpMethod.OPTIONS, "/**")
                    )
                )
            )
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .csrf().disable()
            .authorizeExchange()
            .pathMatchers(*securityMatcherProperties.permitAllPatterns.toTypedArray()).permitAll()
            .anyExchange().authenticated()
            .and()
            .oauth2ResourceServer()
            .jwt()
        return http.build()
    }
}
