package io.github.susimsek.review.model

import java.io.Serializable

data class Product(
    var id: String? = null
) : Serializable {
    companion object {
        const val PRODUCT_TYPE = "Product"
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