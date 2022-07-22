package io.github.susimsek.product.service

import io.github.susimsek.product.model.Product
import io.github.susimsek.product.repository.ProductRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono


@Service
class ProductService(
    private val productRepository: ProductRepository
    ) {

    fun getProduct(id: String): Mono<Product> {
        return productRepository.findById(id)
    }

    fun getAllProducts(): Flux<Product> {
        return productRepository.findAll()
    }
}