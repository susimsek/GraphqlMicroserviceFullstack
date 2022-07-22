package io.github.susimsek.mscommonweb.graphql.exception

data class FieldError(
    var property: String? = null,
    var message: String? = null
)
