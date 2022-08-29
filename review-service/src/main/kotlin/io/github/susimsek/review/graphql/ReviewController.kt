package io.github.susimsek.review.graphql

import io.github.susimsek.review.model.Product
import io.github.susimsek.review.model.Review
import io.github.susimsek.review.service.ReviewService
import org.springframework.graphql.data.method.annotation.BatchMapping
import org.springframework.stereotype.Controller
import reactor.core.publisher.Mono

@Controller
class ReviewController(
    private val reviewService: ReviewService
) {

    @BatchMapping
    fun reviews(products: MutableList<Product>): Mono<Map<Product, List<Review>>> {
        val productIds = products.map { it.id }.toMutableList()
        val result = reviewService.getReviewsByProductIdsIn(productIds)
        return result
            .collectMultimap { it.productId }
            .map { m ->
                productIds.associate { id ->
                    val key = products.find { id.equals(it.id) }!!
                    val value = m[id]?.toMutableList() ?: emptyList()
                    key to value
                }
            }
    }
}
