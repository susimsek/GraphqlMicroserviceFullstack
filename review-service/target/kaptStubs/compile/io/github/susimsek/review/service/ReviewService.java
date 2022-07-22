package io.github.susimsek.review.service;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J.\u0010\u0005\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u00070\u00062\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lio/github/susimsek/review/service/ReviewService;", "", "reviewRepository", "Lio/github/susimsek/review/repository/ReviewRepository;", "(Lio/github/susimsek/review/repository/ReviewRepository;)V", "getProductAllReviewsIn", "Lreactor/core/publisher/Mono;", "", "Lio/github/susimsek/review/model/Product;", "", "Lio/github/susimsek/review/model/Review;", "products", "", "review-service"})
@org.springframework.stereotype.Service
public class ReviewService {
    private final io.github.susimsek.review.repository.ReviewRepository reviewRepository = null;
    
    public ReviewService(@org.jetbrains.annotations.NotNull
    io.github.susimsek.review.repository.ReviewRepository reviewRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public reactor.core.publisher.Mono<java.util.Map<io.github.susimsek.review.model.Product, java.util.List<io.github.susimsek.review.model.Review>>> getProductAllReviewsIn(@org.jetbrains.annotations.NotNull
    java.util.List<io.github.susimsek.review.model.Product> products) {
        return null;
    }
}