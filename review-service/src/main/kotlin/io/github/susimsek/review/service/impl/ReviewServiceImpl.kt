package io.github.susimsek.review.service.impl

import io.github.susimsek.review.model.Review
import io.github.susimsek.review.repository.ReviewRepository
import io.github.susimsek.review.service.ReviewService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository
    ): ReviewService {


    override fun getReviewsByProductIdsIn(productIds: MutableList<String?>): Flux<Review> {
        return when (productIds.size) {
            1 -> reviewRepository.findByProductId(productIds.first()!!)
            else -> reviewRepository.findByProductIdIn(productIds)
        }
    }

    override fun saveReview(review: Review): Mono<Review> {
        return reviewRepository.save(review)
    }

    override fun saveAllReviews(reviews: MutableList<Review>): Flux<Review> {
        return reviewRepository.saveAll(reviews)
    }

    override fun deleteAllReviews(): Mono<Boolean> {
        return reviewRepository.deleteAll().map { true }
    }
}