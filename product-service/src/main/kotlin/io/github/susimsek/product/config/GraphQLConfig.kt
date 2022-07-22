package io.github.susimsek.product.config

import com.apollographql.federation.graphqljava.Federation
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration(proxyBeanMethods = false)
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