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

    @Throws(CoercingSerializeException::class)
    override fun serialize(dataFetcherResult: Any): String? {
        if (dataFetcherResult is LocalDateTime) {
            return dataFetcherResult.format(formatter)
        } else {
            throw CoercingSerializeException("$dataFetcherResult, Not a valid LocalDateTime")
        }
    }

    @Throws(CoercingParseValueException::class)
    override fun parseValue(input: Any): LocalDateTime {
        return LocalDateTime.parse(input.toString(), formatter)
    }

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(input: Any): LocalDateTime {
        if (input is StringValue) {
            return LocalDateTime.parse(input.value, formatter)
        }
        throw CoercingParseLiteralException("$input, Value is not a valid ISO LocalDateTime")
    }
}
