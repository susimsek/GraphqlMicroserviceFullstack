package io.github.susimsek.mscommonweb.graphql.exception

class ResourceAlreadyExistsException(resourceName: String, fieldName: String, fieldValue: Any) :
RuntimeException("$resourceName with $fieldName: $fieldValue is already exists.")
