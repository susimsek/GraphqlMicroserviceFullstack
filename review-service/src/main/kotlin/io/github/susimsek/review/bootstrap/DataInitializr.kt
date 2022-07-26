package io.github.susimsek.review.bootstrap

import io.github.susimsek.review.model.Review
import io.github.susimsek.review.service.ReviewService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(
    value = ["command.line.runner.enabled"],
    havingValue = "true",
    matchIfMissing = true
)
internal class DataInitializr(private val reviewService: ReviewService) : CommandLineRunner {

    private val reviews = mutableListOf(
        Review(id = "1020", productId = "1", text = "Very cramped :( Do not recommend.", starRating = 2),
        Review(id = "1030", productId = "2", starRating = 3),
        Review(id = "1040", productId = "3", starRating = 5),
        Review(id = "1041", productId = "3", text = "Reusable!", starRating = 5),
        Review(id = "1042", productId = "3", starRating = 5),
        Review(id = "1050", productId = "4", text = "Amazing! Would Fly Again!", starRating = 5),
        Review(id = "1051", productId = "4", starRating = 5)
    )

    override fun run(args: Array<String>) {
        reviewService
            .deleteAllReviews()
            .thenMany(
                    reviewService.saveAllReviews(reviews)
            )
            .subscribe()
    }
}
