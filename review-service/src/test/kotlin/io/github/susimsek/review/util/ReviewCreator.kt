package io.github.susimsek.review.util

import io.github.susimsek.review.model.Review

object ReviewCreator {

    const val DEFAULT_ID = "ID"
    const val DEFAULT_PRODUCT_ID = "PRODUCT_ID"
    const val PRODUCT_ID2 = "PRODUCT_ID2"
    const val DEFAULT_TEXT = "AAAAAAAAAA"
    const val DEFAULT_STAR_RATING = 1

    fun createEntity(): Review {
        return Review(id = DEFAULT_ID, productId = DEFAULT_PRODUCT_ID, text = DEFAULT_TEXT,
            starRating = DEFAULT_STAR_RATING)
    }

    fun createMultiEntityList(): MutableList<Review> {
        val review2 = Review(id = "ID2", productId = "PRODUCT_ID2", text = DEFAULT_TEXT,
            starRating = DEFAULT_STAR_RATING)
        return mutableListOf(createEntity(), review2)
    }

    fun createSingleEntityList(): MutableList<Review> {
        return mutableListOf(createEntity())
    }
}