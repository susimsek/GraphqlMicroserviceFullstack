package io.github.susimsek.product.service

import io.github.susimsek.product.model.Product
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ProductService {
    fun getProduct(id: String): Mono<Product>
    fun saveProduct(product: Product): Mono<Product>
    fun deleteAllProducts(): Mono<Boolean>
    fun getAllProducts(): Flux<Product>
}
