package io.github.susimsek.review.service

import io.github.susimsek.review.model.Product
import io.github.susimsek.review.model.Review
import io.github.susimsek.review.repository.ReviewRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class ReviewService(
    private val reviewRepository: ReviewRepository
    ) {

    fun getProductAllReviewsIn(products: MutableList<Product>): Mono<Map<Product, List<Review>>> {
        val productIds = products.map {it.id}.toMutableList()

        return reviewRepository.findByProductIdIn(productIds)
            .collectMultimap { it.productId!! }
            .map { m ->
                productIds.associate { id ->
                    val key = products.find { id.equals(it.id) }!!
                    val value = m[id]?.toMutableList() ?: emptyList()
                    key to value
                }
            }
    }
}