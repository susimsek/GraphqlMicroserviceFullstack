package io.github.susimsek.mscommonweb.graphql

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import graphql.schema.GraphQLScalarType
import io.github.susimsek.mscommonweb.graphql.scalar.GraphQlDateTimeProperties
import io.github.susimsek.mscommonweb.graphql.scalar.ScalarUtil
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.graphql.execution.RuntimeWiringConfigurer
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(GraphQlDateTimeProperties::class)
class GraphqlDateTimeAutoConfiguration {

    @Bean
    fun jsonCustomizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder: Jackson2ObjectMapperBuilder ->
            builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            builder.featuresToDisable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
        }
    }

    @Bean
    fun graphQlOffsetDateTimeScalar(configurationProperties: GraphQlDateTimeProperties): GraphQLScalarType {
        return ScalarUtil.offsetDateTimeScalar(
            configurationProperties.offsetDateTime.scalarName,
            configurationProperties.offsetDateTime.format
        )
    }

    @Bean
    fun graphQlLocalDateScalar(configurationProperties: GraphQlDateTimeProperties): GraphQLScalarType {
        return ScalarUtil.localDateScalar(
            configurationProperties.localDate.scalarName,
            configurationProperties.localDate.format
        )
    }

    @Bean
    fun graphQlLocalDateTimeScalar(configurationProperties: GraphQlDateTimeProperties): GraphQLScalarType {
        return ScalarUtil.localDateTimeScalar(
            configurationProperties.localDateTime.scalarName,
            configurationProperties.localDateTime.format
        )
    }

    @Bean
    fun graphqlDateTimeConfigurer(
        graphQlOffsetDateTimeScalar: GraphQLScalarType,
        graphQlLocalDateTimeScalar: GraphQLScalarType,
        graphQlLocalDateScalar: GraphQLScalarType
    ): RuntimeWiringConfigurer {
        return RuntimeWiringConfigurer { builder ->
            builder.scalar(graphQlOffsetDateTimeScalar)
            builder.scalar(graphQlLocalDateTimeScalar)
            builder.scalar(graphQlLocalDateScalar)
        }
    }
}
