package io.github.susimsek.mscommonweb.graphql.scalar

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties(prefix = "graphql.datetime.scalars")
@ConstructorBinding
data class GraphQlDateTimeProperties(
    var offsetDateTime: ScalarDefinition = ScalarDefinition("OffsetDateTime", "yyyy-MM-dd'T'HH:mm:ssXXX"),
    var localDateTime: ScalarDefinition = ScalarDefinition("LocalDateTime", "yyyy-MM-dd'T'HH:MM:ss"),
    var localDate: ScalarDefinition = ScalarDefinition("LocalDate", "yyyy-MM-dd")
)

data class ScalarDefinition(var scalarName: String?, var format: String?)
