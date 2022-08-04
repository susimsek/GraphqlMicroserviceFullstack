package io.github.susimsek.mscommonweb.graphql.scalar

import graphql.Internal
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Internal
class LocalDateCoercing(
    private val formatter: DateTimeFormatter
) : Coercing<LocalDate, String?> {

    private fun convertImpl(input: Any): LocalDate? {
        if (input is String) {
            return LocalDate.parse(input, DateTimeFormatter.ISO_LOCAL_DATE)
        } else if (input is LocalDate) {
            return input
        }
        return null
    }

    @Throws(CoercingSerializeException::class)
    override fun serialize(input: Any): String? {
        return if (input is LocalDate) {
            return input.format(formatter)
        } else {
            val result = convertImpl(input) ?:  throw CoercingParseValueException("Invalid value '$input' for LocalDate")
            result.format(formatter)
        }
    }

    @Throws(CoercingParseValueException::class)
    override fun parseValue(input: Any): LocalDate {
        return convertImpl(input) ?: throw CoercingParseValueException("Invalid value '$input' for LocalDate")
    }

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(input: Any): LocalDate {
        val value = (input as StringValue).value
        return convertImpl(value)
            ?: throw CoercingParseLiteralException("Invalid value '$input' for LocalDate")
    }
}
