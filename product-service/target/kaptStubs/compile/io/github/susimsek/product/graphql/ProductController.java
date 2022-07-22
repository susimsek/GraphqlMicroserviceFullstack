package io.github.susimsek.product.graphql;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0001\u0010\b\u001a\u00020\tH\u0017J\u000e\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u000bH\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lio/github/susimsek/product/graphql/ProductController;", "", "productService", "Lio/github/susimsek/product/service/ProductService;", "(Lio/github/susimsek/product/service/ProductService;)V", "product", "Lreactor/core/publisher/Mono;", "Lio/github/susimsek/product/model/Product;", "id", "", "products", "Lreactor/core/publisher/Flux;", "product-service"})
@org.springframework.security.access.prepost.PreAuthorize(value = "isAuthenticated()")
@org.springframework.stereotype.Controller
public class ProductController {
    private final io.github.susimsek.product.service.ProductService productService = null;
    
    public ProductController(@org.jetbrains.annotations.NotNull
    io.github.susimsek.product.service.ProductService productService) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    @org.springframework.graphql.data.method.annotation.QueryMapping
    public reactor.core.publisher.Mono<io.github.susimsek.product.model.Product> product(@org.jetbrains.annotations.NotNull
    @org.springframework.graphql.data.method.annotation.Argument
    java.lang.String id) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    @org.springframework.graphql.data.method.annotation.QueryMapping
    public reactor.core.publisher.Flux<io.github.susimsek.product.model.Product> products() {
        return null;
    }
}