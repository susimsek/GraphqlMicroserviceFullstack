package io.github.susimsek.review.service.cache


import io.github.susimsek.review.model.Review
import io.github.susimsek.review.repository.redis.ReviewRedisRepository
import io.github.susimsek.review.service.ReviewService
import io.github.susimsek.review.service.impl.ReviewServiceImpl
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
@Primary
class CachedReviewService(
    private val reviewService: ReviewServiceImpl,
    private val reviewRedisRepository: ReviewRedisRepository
) : ReviewService {

    override fun getReviewsByProductIdsIn(productIds: MutableList<String?>): Flux<Review> {
        val hashKey = when (productIds.size) {
            1 -> productIds.first().hashCode().toString()
            else -> productIds.hashCode().toString()
        }
        val cacheResult = reviewRedisRepository.findById(hashKey)
        return cacheResult.hasElement()
            .flatMapMany {
                when (it) {
                    true -> cacheResult.flatMapIterable {map -> map.values }
                    else -> getAllFromDatabase(productIds)
                }
            }
    }

    override fun saveReview(review: Review): Mono<Review> {
        return reviewService.saveReview(review)
            .flatMap(reviewRedisRepository::saveReview)
    }

    override fun saveAllReviews(reviews: MutableList<Review>): Flux<Review> {
        return reviewService.saveAllReviews(reviews)
            .collectMultimap { it.productId.hashCode().toString() }
            .map {it.mapValues { reviewMap -> reviewMap.value.associateBy { review -> review.id }.toMutableMap() } }
            .flatMap(reviewRedisRepository::saveAll)
            .flatMapIterable { reviews }
    }

    override fun deleteAllReviews(): Mono<Boolean> {
        return reviewService.deleteAllReviews()
            .then(reviewRedisRepository.deleteAll())
            .then(reviewRedisRepository.deleteAll())
    }

    private fun getAllFromDatabase(productIds: MutableList<String?>): Flux<Review> {
        val hashKey = when (productIds.size) {
            1 -> productIds.first().hashCode().toString()
            else -> productIds.hashCode().toString()
        }
        return reviewService.getReviewsByProductIdsIn(productIds)
            .collectMap(Review::productId)
            .flatMapMany{ map -> reviewRedisRepository.save(hashKey, map)
                .flatMapIterable { it.values }
            }
    }
}