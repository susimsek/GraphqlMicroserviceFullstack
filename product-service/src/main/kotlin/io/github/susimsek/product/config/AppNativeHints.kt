package io.github.susimsek.product.config

import io.github.susimsek.product.config.AppNativeHints.Companion.GRAPHQL_FEDERATION_CONFIG_PROPERTIES
import io.github.susimsek.product.config.AppNativeHints.Companion.GRAPHQL_SCHEMA_CONFIG_PROPERTIES
import io.github.susimsek.product.model.Product
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.graphql.GraphQlSourceBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.core.NativeDetector
import org.springframework.core.io.ClassPathResource
import org.springframework.nativex.hint.ResourceHint
import org.springframework.nativex.hint.TypeAccess
import org.springframework.nativex.hint.TypeHint

@TypeHint(
    types = [Product::class], access = [
        TypeAccess.RESOURCE, TypeAccess.PUBLIC_CLASSES, TypeAccess.DECLARED_CLASSES,
        TypeAccess.DECLARED_CONSTRUCTORS, TypeAccess.PUBLIC_CONSTRUCTORS, TypeAccess.DECLARED_METHODS,
        TypeAccess.PUBLIC_METHODS, TypeAccess.PUBLIC_FIELDS, TypeAccess.DECLARED_FIELDS]
)
@ResourceHint(patterns = [GRAPHQL_SCHEMA_CONFIG_PROPERTIES, GRAPHQL_FEDERATION_CONFIG_PROPERTIES])
@Lazy(false)
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(name = ["org.springframework.nativex.NativeListener"])
class AppNativeHints {
    @Bean
    fun graphQlSourceBuilderCustomizer(): GraphQlSourceBuilderCustomizer {
        return GraphQlSourceBuilderCustomizer { builder ->
            if (NativeDetector.inNativeImage()) {
                builder.schemaResources(ClassPathResource(GRAPHQL_SCHEMA_CONFIG_PROPERTIES))
                builder.schemaResources(ClassPathResource(GRAPHQL_FEDERATION_CONFIG_PROPERTIES))
            }
        }
    }

    companion object {
        const val GRAPHQL_SCHEMA_CONFIG_PROPERTIES = "graphql/schema.graphqls"
        const val GRAPHQL_FEDERATION_CONFIG_PROPERTIES = "fed2directives.graphqls"
    }
}
