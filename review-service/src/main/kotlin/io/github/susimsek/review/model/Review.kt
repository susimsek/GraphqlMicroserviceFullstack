package io.github.susimsek.review.model

import io.github.susimsek.mscommonweb.mongo.model.audit.AbstractAuditingEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "review")
data class Review(
    @Id
    var id: String = UUID.randomUUID().toString(),
    var productId: String? = null,
    var text: String? = null,
    var starRating: Int? = null,
): AbstractAuditingEntity()