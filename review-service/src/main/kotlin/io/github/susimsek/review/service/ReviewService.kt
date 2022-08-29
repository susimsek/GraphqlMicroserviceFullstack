package io.github.susimsek.review.service

import io.github.susimsek.review.model.Review
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ReviewService {
    fun getReviewsByProductIdsIn(productIds: MutableList<String?>): Flux<Review>
    fun saveReview(review: Review): Mono<Review>
    fun saveAllReviews(reviews: MutableList<Review>): Flux<Review>
    fun deleteAllReviews(): Mono<Boolean>
}
