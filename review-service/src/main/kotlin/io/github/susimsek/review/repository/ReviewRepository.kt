package io.github.susimsek.review.repository

import io.github.susimsek.review.model.Review
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface ReviewRepository : ReactiveMongoRepository<Review, String>, ReactiveQuerydslPredicateExecutor<Review> {

    fun findByProductIdIn(productIds: MutableList<String?>): Flux<Review>
    fun findByProductId(productId: String): Flux<Review>
}
