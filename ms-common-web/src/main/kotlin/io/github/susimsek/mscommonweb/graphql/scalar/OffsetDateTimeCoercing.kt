package io.github.susimsek.mscommonweb.graphql.scalar

import graphql.Internal
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException




@Internal
class OffsetDateTimeCoercing(
    private val formatter: DateTimeFormatter
) : Coercing<OffsetDateTime, String?> {

    private fun convertImpl(input: Any): OffsetDateTime? {
        if (input is String) {
            try {
                return OffsetDateTime.parse(input, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
            } catch (ignored: DateTimeParseException) {
                // nothing to-do
            }
        } else if (input is OffsetDateTime) {
            return input
        }
        return null
    }

    @Throws(CoercingSerializeException::class)
    override fun serialize(input: Any): String? {
        return if (input is OffsetDateTime) {
            input.format(formatter)
        } else {
            val result = convertImpl(input) ?:  throw CoercingParseValueException("Invalid value '$input' for OffsetDateTime")
            result.format(formatter)
        }
    }

    @Throws(CoercingParseValueException::class)
    override fun parseValue(input: Any): OffsetDateTime {
        return convertImpl(input) ?: throw CoercingParseValueException("Invalid value '$input' for OffsetDateTime")
    }

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(input: Any): OffsetDateTime {
        val value = (input as StringValue).value
        return convertImpl(value)
            ?: throw CoercingParseLiteralException("Invalid value '$input' for OffsetDateTime")
    }
}
