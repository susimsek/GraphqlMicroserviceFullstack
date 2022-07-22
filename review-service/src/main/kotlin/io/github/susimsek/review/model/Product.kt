package io.github.susimsek.review.model

data class Product(
    var id: String? = null
) {
    companion object {
        const val PRODUCT_TYPE = "Product"
    }
}