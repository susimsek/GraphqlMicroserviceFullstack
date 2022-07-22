package io.github.susimsek.product.bootstrap;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0011\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001b\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0016\u00a2\u0006\u0002\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lio/github/susimsek/product/bootstrap/DataInitializr;", "Lorg/springframework/boot/CommandLineRunner;", "productRepository", "Lio/github/susimsek/product/repository/ProductRepository;", "(Lio/github/susimsek/product/repository/ProductRepository;)V", "run", "", "args", "", "", "([Ljava/lang/String;)V", "product-service"})
@org.springframework.stereotype.Component
public class DataInitializr implements org.springframework.boot.CommandLineRunner {
    private final io.github.susimsek.product.repository.ProductRepository productRepository = null;
    
    public DataInitializr(@org.jetbrains.annotations.NotNull
    io.github.susimsek.product.repository.ProductRepository productRepository) {
        super();
    }
    
    @java.lang.Override
    public void run(@org.jetbrains.annotations.NotNull
    java.lang.String[] args) {
    }
}