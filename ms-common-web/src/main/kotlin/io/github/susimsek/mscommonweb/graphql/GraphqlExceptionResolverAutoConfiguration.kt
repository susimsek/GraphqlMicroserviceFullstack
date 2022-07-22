package io.github.susimsek.mscommonweb.graphql

import io.github.susimsek.mscommonweb.graphql.exception.resolver.ReactiveGraphqlExceptionResolver
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
class GraphqlExceptionResolverAutoConfiguration {

    @Bean
    fun reactiveGraphqlExceptionResolver(): ReactiveGraphqlExceptionResolver {
        return ReactiveGraphqlExceptionResolver()
    }
}
