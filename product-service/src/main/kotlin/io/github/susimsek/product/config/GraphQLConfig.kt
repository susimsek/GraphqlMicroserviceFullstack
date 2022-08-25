package io.github.susimsek.product.config

import com.apollographql.federation.graphqljava.Federation
import io.github.susimsek.mscommonweb.graphql.GraphqlDateTimeAutoConfiguration
import io.github.susimsek.mscommonweb.graphql.GraphqlExceptionResolverAutoConfiguration
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration(proxyBeanMethods = false)
@Import(value = [GraphqlDateTimeAutoConfiguration::class, GraphqlExceptionResolverAutoConfiguration::class])
class GraphQLConfig {

    @Bean
    fun federationTransform(): GraphQlSourceBuilderCustomizer {
        return GraphQlSourceBuilderCustomizer { builder ->
            builder.schemaFactory { registry, wiring ->
                Federation.transform(registry, wiring)
                    .fetchEntities { null }
                    .resolveEntityType { null }
                    .build()
            }
        }
    }
}