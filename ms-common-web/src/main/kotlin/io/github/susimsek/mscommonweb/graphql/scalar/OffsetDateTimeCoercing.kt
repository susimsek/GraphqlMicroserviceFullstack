package io.github.susimsek.mscommonweb.graphql.scalar

import graphql.Internal
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException
import graphql.schema.CoercingParseValueException
import graphql.schema.CoercingSerializeException
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@Internal
class OffsetDateTimeCoercing(
    private val formatter: DateTimeFormatter
) : Coercing<OffsetDateTime, String?> {

    @Throws(CoercingSerializeException::class)
    override fun serialize(dataFetcherResult: Any): String? {
        if (dataFetcherResult is OffsetDateTime) {
            return dataFetcherResult.format(formatter)
        } else {
            throw CoercingSerializeException("$dataFetcherResult, Not a valid OffsetDateTime")
        }
    }

    @Throws(CoercingParseValueException::class)
    override fun parseValue(input: Any): OffsetDateTime {
        return OffsetDateTime.parse(input.toString(), formatter)
    }

    @Throws(CoercingParseLiteralException::class)
    override fun parseLiteral(input: Any): OffsetDateTime {
        if (input is StringValue) {
            return OffsetDateTime.parse(input.value, formatter)
        }
        throw CoercingParseLiteralException("$input, Value is not a valid ISO OffsetDateTime")
    }
}
