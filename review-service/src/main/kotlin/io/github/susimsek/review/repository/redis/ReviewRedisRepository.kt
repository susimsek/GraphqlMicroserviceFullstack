package io.github.susimsek.review.repository.redis

import io.github.susimsek.mscommonweb.cache.AbstractReactiveRedisRepository
import io.github.susimsek.review.model.Review
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class ReviewRedisRepository: AbstractReactiveRedisRepository<MutableMap<String, Review>>() {
    override fun getKey(): String {
        return "productReview"
    }

    fun saveReview(review: Review): Mono<Review> {
        val cacheResult = findById(review.productId.hashCode().toString())
        return cacheResult.hasElement()
            .flatMap {
                when (it) {
                    true -> cacheResult.flatMap { reviews -> updateReview(review, reviews) }
                    else -> addReview(review)
                }
            }.map { review }
    }

    private fun addReview(review: Review): Mono<MutableMap<String, Review>> {
        return save(review.productId.hashCode().toString(), hashMapOf(review.id to review))
    }

    private fun updateReview(review: Review, map: MutableMap<String, Review>): Mono<MutableMap<String, Review>> {
        when (val stored = map[review.id]) {
            null -> map[review.id] = review
            else -> {
                stored.text = review.text
                stored.starRating = review.starRating
                map[review.id] = stored
            }
        }
        return save(review.productId.hashCode().toString(), map)
    }
}