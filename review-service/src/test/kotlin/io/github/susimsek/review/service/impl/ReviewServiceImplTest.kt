package io.github.susimsek.review.service.impl

import io.github.susimsek.review.model.Review
import io.github.susimsek.review.repository.ReviewRepository
import io.github.susimsek.review.util.ReviewCreator.DEFAULT_ID
import io.github.susimsek.review.util.ReviewCreator.DEFAULT_PRODUCT_ID
import io.github.susimsek.review.util.ReviewCreator.DEFAULT_STAR_RATING
import io.github.susimsek.review.util.ReviewCreator.DEFAULT_TEXT
import io.github.susimsek.review.util.ReviewCreator.PRODUCT_ID2
import io.github.susimsek.review.util.ReviewCreator.createEntity
import io.github.susimsek.review.util.ReviewCreator.createMultiEntityList
import io.github.susimsek.review.util.ReviewCreator.createSingleEntityList
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import kotlin.test.assertEquals

@Extensions(
    ExtendWith(MockitoExtension::class)
)
class ReviewServiceImplTest {

    @Mock
    private lateinit var reviewRepositoryMock: ReviewRepository

    @InjectMocks
    private lateinit var reviewService: ReviewServiceImpl

    private lateinit var review: Review

    @BeforeEach
    fun initTest() {
        review = createEntity()
    }

    @Test
    fun saveReview_shouldSaveAndReturnReview() {
        Mockito.`when`(reviewRepositoryMock.save(Mockito.any(Review::class.java))).thenReturn(Mono.just(review))

        val result = reviewService.saveReview(review)

        StepVerifier
            .create(result)
            .consumeNextWith{
                    review ->
                assertEquals(review.id, DEFAULT_ID)
                assertEquals(review.productId, DEFAULT_PRODUCT_ID)
                assertEquals(review.text, DEFAULT_TEXT)
                assertEquals(review.starRating, DEFAULT_STAR_RATING)
            }
            .verifyComplete()

        Mockito.verify(reviewRepositoryMock, Mockito.times(1)).save(Mockito.any(Review::class.java))
    }

    @Test
    fun saveAllReviews_shouldSaveAllAndReturnReviews() {
        val entities = createMultiEntityList()
        Mockito.`when`(reviewRepositoryMock.saveAll(anyList())).thenReturn(Flux.fromIterable(entities))

        val result = reviewService.saveAllReviews(entities)

        StepVerifier
            .create(result)
            .expectNextCount(2)
            .verifyComplete()

        Mockito.verify(reviewRepositoryMock, Mockito.times(1)).saveAll(anyList())
    }

    @Test
    fun getReviewsByProductIdsIn__shouldReturnReviewsForSingleProduct() {
        val entities = createSingleEntityList()
        Mockito.`when`(reviewRepositoryMock.findByProductId(anyString())).thenReturn(Flux.fromIterable(entities))

        val result = reviewService.getReviewsByProductIdsIn(mutableListOf(DEFAULT_PRODUCT_ID))

        StepVerifier
            .create(result)
            .expectNextCount(1)
            .verifyComplete()

        Mockito.verify(reviewRepositoryMock, Mockito.times(1)).findByProductId(anyString())
    }

    @Test
    fun getReviewsByProductIdsIn__shouldReturnEmptyReviewsForEmptyReviewsListAndSingleProduct() {
        Mockito.`when`(reviewRepositoryMock.findByProductId(anyString())).thenReturn(Flux.empty())

        val result = reviewService.getReviewsByProductIdsIn(mutableListOf(DEFAULT_PRODUCT_ID))

        StepVerifier
            .create(result)
            .expectNextCount(0)
            .verifyComplete()

        Mockito.verify(reviewRepositoryMock, Mockito.times(1)).findByProductId(anyString())
    }

    @Test
    fun getReviewsByProductIdsIn__shouldReturnReviewsForMultiProducts() {
        val entities = createMultiEntityList()
        Mockito.`when`(reviewRepositoryMock.findByProductIdIn(anyList())).thenReturn(Flux.fromIterable(entities))

        val result = reviewService.getReviewsByProductIdsIn(mutableListOf(DEFAULT_PRODUCT_ID, PRODUCT_ID2))

        StepVerifier
            .create(result)
            .expectNextCount(2)
            .verifyComplete()

        Mockito.verify(reviewRepositoryMock, Mockito.times(1)).findByProductIdIn(anyList())
    }

    @Test
    fun getReviewsByProductIdsIn__shouldReturnEmptyReviewsForEmptyReviewsListAndMultiProducts() {
        Mockito.`when`(reviewRepositoryMock.findByProductIdIn(anyList())).thenReturn(Flux.empty())

        val result = reviewService.getReviewsByProductIdsIn(mutableListOf(DEFAULT_PRODUCT_ID, PRODUCT_ID2))

        StepVerifier
            .create(result)
            .expectNextCount(0)
            .verifyComplete()

        Mockito.verify(reviewRepositoryMock, Mockito.times(1)).findByProductIdIn(anyList())
    }

    @Test
    fun deleteAllReviews_shouldDeleteAllReviews() {

        Mockito.`when`(reviewRepositoryMock.deleteAll()).thenReturn(Mono.empty())

        val result = reviewService.deleteAllReviews()

        StepVerifier.create(result).expectNextCount(0).verifyComplete()

        Mockito.verify(reviewRepositoryMock, Mockito.times(1)).deleteAll()
    }
}