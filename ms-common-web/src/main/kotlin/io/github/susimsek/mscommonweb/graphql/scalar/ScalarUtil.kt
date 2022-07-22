package io.github.susimsek.mscommonweb.graphql.scalar

import graphql.schema.GraphQLScalarType
import java.time.format.DateTimeFormatter

object ScalarUtil {

    fun offsetDateTimeScalar(name: String?, format: String?): GraphQLScalarType {
        val dateTimeFormatter = format?.let { DateTimeFormatter.ofPattern(format) }
            ?: DateTimeFormatter.ISO_OFFSET_DATE_TIME
        return GraphQLScalarType.newScalar()
            .name(name ?: "OffsetDateTime")
            .description("OffsetDateTime type")
            .coercing(OffsetDateTimeCoercing(dateTimeFormatter))
            .build()
    }

    fun localDateTimeScalar(name: String?, format: String?): GraphQLScalarType {
        val dateTimeFormatter = format?.let { DateTimeFormatter.ofPattern(format) }
            ?: DateTimeFormatter.ISO_LOCAL_DATE_TIME
        return GraphQLScalarType.newScalar()
            .name(name ?: "LocalDateTime")
            .description("LocalDateTime type")
            .coercing(LocalDateTimeCoercing(dateTimeFormatter))
            .build()
    }

    fun localDateScalar(name: String?, format: String?): GraphQLScalarType {
        val dateTimeFormatter = format?.let { DateTimeFormatter.ofPattern(format) }
            ?: DateTimeFormatter.ISO_LOCAL_DATE
        return GraphQLScalarType.newScalar()
            .name(name ?: "LocalDate")
            .description("LocalDate type")
            .coercing(LocalDateCoercing(dateTimeFormatter))
            .build()
    }
}
