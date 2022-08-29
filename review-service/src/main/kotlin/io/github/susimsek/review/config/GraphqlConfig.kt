package io.github.susimsek.review.config

import com.apollographql.federation.graphqljava.Federation
import com.apollographql.federation.graphqljava._Entity
import graphql.TypeResolutionEnvironment
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import graphql.schema.TypeResolver
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.TypeDefinitionRegistry
import io.github.susimsek.mscommonweb.graphql.GraphqlDateTimeAutoConfiguration
import io.github.susimsek.mscommonweb.graphql.GraphqlExceptionResolverAutoConfiguration
import io.github.susimsek.review.model.Product
import io.github.susimsek.review.model.Product.Companion.PRODUCT_TYPE
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.graphql.execution.GraphQlSource.SchemaResourceBuilder
import java.util.stream.Collectors

@Configuration(proxyBeanMethods = false)
@Import(value = [GraphqlDateTimeAutoConfiguration::class, GraphqlExceptionResolverAutoConfiguration::class])
class GraphqlConfig {

    @Bean
    fun federationTransform(): GraphQlSourceBuilderCustomizer {
        val entityDataFetcher: DataFetcher<*> = DataFetcher { env: DataFetchingEnvironment ->
            val representations =
                env.getArgument<List<Map<String, Any>>>(_Entity.argumentName)
            representations.stream()
                .map { representation: Map<String, Any> ->
                    if (PRODUCT_TYPE == representation["__typename"]) {
                        return@map Product(representation["id"] as String?)
                    }
                    null
                }
                .collect(Collectors.toList())
        }
        val entityTypeResolver = TypeResolver { env: TypeResolutionEnvironment ->
            val src = env.getObject<Any>()
            if (src is Product) {
                return@TypeResolver env.schema
                    .getObjectType(PRODUCT_TYPE)
            }
            null
        }
        return GraphQlSourceBuilderCustomizer { builder: SchemaResourceBuilder ->
            builder.schemaFactory { registry: TypeDefinitionRegistry, wiring: RuntimeWiring ->
                Federation.transform(registry, wiring)
                    .fetchEntities(entityDataFetcher)
                    .resolveEntityType(entityTypeResolver)
                    .build()
            }
        }
    }
}
