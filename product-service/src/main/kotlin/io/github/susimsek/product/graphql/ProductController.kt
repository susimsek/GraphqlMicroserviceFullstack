package io.github.susimsek.product.graphql

import io.github.susimsek.product.model.Product
import io.github.susimsek.product.service.ProductService
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Controller
@PreAuthorize("isAuthenticated()")
class ProductController(
    private val productService: ProductService
) {

    @QueryMapping
    fun product(@Argument id: String): Mono<Product> {
        return productService.getProduct(id)
    }

    @QueryMapping
    fun products(): Flux<Product> {
        return productService.getAllProducts()
    }
}