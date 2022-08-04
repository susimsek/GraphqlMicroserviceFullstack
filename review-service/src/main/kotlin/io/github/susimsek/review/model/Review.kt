package io.github.susimsek.review.model

import io.github.susimsek.mscommonweb.mongo.model.audit.AbstractAuditingEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.util.UUID

@Document(collection = "review")
data class Review(
    @Id
    var id: String = UUID.randomUUID().toString(),
    var productId: String,
    var text: String? = null,
    var starRating: Int? = null,
): AbstractAuditingEntity(), Serializable {

    companion object {
        private const val serialVersionUID = 1L
    }
    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Review) return false
        return id == other.id
    }
}