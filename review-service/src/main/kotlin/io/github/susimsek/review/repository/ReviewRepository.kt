package io.github.susimsek.review.repository

import io.github.susimsek.review.model.Review
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface ReviewRepository : ReactiveMongoRepository<Review, String> {

    fun findByProductIdIn(productIds: MutableList<String?>): Flux<Review>
}
