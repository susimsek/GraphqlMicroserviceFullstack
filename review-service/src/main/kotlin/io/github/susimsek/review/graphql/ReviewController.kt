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
        return reviewService.getProductAllReviewsIn(products)
    }
}