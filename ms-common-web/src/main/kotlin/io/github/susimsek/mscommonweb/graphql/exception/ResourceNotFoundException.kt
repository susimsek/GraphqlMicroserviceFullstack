package io.github.susimsek.mscommonweb.graphql.exception

class ResourceNotFoundException(resourceName: String, fieldName: String, fieldValue: Any)
    : RuntimeException("$resourceName with $fieldName: $fieldValue was not found.")
