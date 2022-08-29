package io.github.susimsek.product.service.impl

import io.github.susimsek.mscommonweb.graphql.exception.ResourceNotFoundException
import io.github.susimsek.product.model.Product
import io.github.susimsek.product.repository.ProductRepository
import io.github.susimsek.product.service.ProductService
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ProductServiceImpl(
    private val productRepository: ProductRepository
    ) : ProductService {

    override fun getProduct(id: String): Mono<Product> {
        return productRepository.findById(id)
            .switchIfEmpty(Mono.error((ResourceNotFoundException("Product", "id", id))))
    }

    override fun saveProduct(product: Product): Mono<Product> {
        return productRepository.save(product)
    }

    override fun deleteAllProducts(): Mono<Boolean> {
        return productRepository.deleteAll().map { true }
    }

    override fun getAllProducts(): Flux<Product> {
        return productRepository.findAll()
    }
}
