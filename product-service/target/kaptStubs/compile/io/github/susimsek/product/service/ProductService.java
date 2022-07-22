package io.github.susimsek.product.service;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016J\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lio/github/susimsek/product/service/ProductService;", "", "productRepository", "Lio/github/susimsek/product/repository/ProductRepository;", "(Lio/github/susimsek/product/repository/ProductRepository;)V", "getAllProducts", "Lreactor/core/publisher/Flux;", "Lio/github/susimsek/product/model/Product;", "getProduct", "Lreactor/core/publisher/Mono;", "id", "", "product-service"})
@org.springframework.stereotype.Service
public class ProductService {
    private final io.github.susimsek.product.repository.ProductRepository productRepository = null;
    
    public ProductService(@org.jetbrains.annotations.NotNull
    io.github.susimsek.product.repository.ProductRepository productRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public reactor.core.publisher.Mono<io.github.susimsek.product.model.Product> getProduct(@org.jetbrains.annotations.NotNull
    java.lang.String id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public reactor.core.publisher.Flux<io.github.susimsek.product.model.Product> getAllProducts() {
        return null;
    }
}