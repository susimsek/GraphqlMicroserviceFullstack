package io.github.susimsek.review.repository;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0000\bg\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u001e\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00020\u00052\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0007H&\u00a8\u0006\b"}, d2 = {"Lio/github/susimsek/review/repository/ReviewRepository;", "Lorg/springframework/data/mongodb/repository/ReactiveMongoRepository;", "Lio/github/susimsek/review/model/Review;", "", "findByProductIdIn", "Lreactor/core/publisher/Flux;", "productIds", "", "review-service"})
@org.springframework.stereotype.Repository
public abstract interface ReviewRepository extends org.springframework.data.mongodb.repository.ReactiveMongoRepository<io.github.susimsek.review.model.Review, java.lang.String> {
    
    @org.jetbrains.annotations.NotNull
    public abstract reactor.core.publisher.Flux<io.github.susimsek.review.model.Review> findByProductIdIn(@org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> productIds);
}