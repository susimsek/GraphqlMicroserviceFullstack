package io.github.susimsek.product.model

import com.querydsl.core.annotations.QueryEntity
import io.github.susimsek.mscommonweb.mongo.model.audit.AbstractAuditingEntity
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable
import java.util.UUID

@QueryEntity
@Document(collection = "product")
data class Product(
    @Id
    var id: String = UUID.randomUUID().toString(),
    var name: String? = null,
    var description: String? = null,
    var description2: String? = description
): AbstractAuditingEntity(), Serializable {
    companion object {
        private const val serialVersionUID = 1L
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Product) return false
        return id == other.id
    }
}