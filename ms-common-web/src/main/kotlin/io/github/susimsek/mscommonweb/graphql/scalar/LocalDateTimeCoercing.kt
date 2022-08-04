package io.github.susimsek.mscommonweb.graphql.scalar

import graphql.Internal
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Internal
class LocalDateTimeCoercing(
    private val formatter: DateTimeFormatter
) : Coercing<LocalDateTime, String?> {

    private fun convertImpl(input: Any): LocalDateTime? {
        if (input is String) {
            return LocalDateTime.parse(input, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        } else if (input is LocalDateTime) {
            return input
        }
        return null
    }


    @Throws(CoercingSerializeException::class)
    override fun serialize(input: Any): String? {
        return if (input is LocalDateTime) {
            input.format(formatter)
        } else {
            val result = convertImpl(input) ?:  throw CoercingParseValueException("Invalid value '$input' for LocalDateTime")
            result.format(formatter)
        }
    }

    @Throws(CoercingParseValueException::class)
    override fun parseValue(input: Any): LocalDateTime {
        return convertImpl(input) ?: throw CoercingParseValueException("Invalid value '$input' for LocalDateTime")
    }

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(input: Any): LocalDateTime {
        val value = (input as StringValue).value
        return convertImpl(value)
            ?: throw CoercingParseLiteralException("Invalid value '$input' for LocalDateTime")
    }
}
