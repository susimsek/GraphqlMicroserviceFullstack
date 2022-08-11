package io.github.susimsek.product.util

import io.github.susimsek.product.model.Product

object ProductCreator {

    const val DEFAULT_ID = "ID"
    const val DEFAULT_NAME = "AAAAAAAAAA"
    const val DEFAULT_DESCRIPTION = "AAAAAAAAAA"

    fun createEntity(): Product {
        return Product(id = DEFAULT_ID, name = DEFAULT_NAME, description = DEFAULT_DESCRIPTION)
    }
}