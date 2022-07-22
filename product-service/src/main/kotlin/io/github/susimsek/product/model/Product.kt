package io.github.susimsek.product.model

import io.github.susimsek.mscommonweb.mongo.model.audit.AbstractAuditingEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "product")
data class Product(
    @Id
    var id: String = UUID.randomUUID().toString(),
    var name: String? = null,
    var description: String? = null
): AbstractAuditingEntity()