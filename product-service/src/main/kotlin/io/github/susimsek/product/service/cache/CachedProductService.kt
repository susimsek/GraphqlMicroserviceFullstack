package io.github.susimsek.product.service.cache

import io.github.susimsek.product.model.Product
import io.github.susimsek.product.repository.redis.ProductRedisRepository
import io.github.susimsek.product.service.ProductService
import io.github.susimsek.product.service.impl.ProductServiceImpl
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
@Primary
class CachedProductService(
    private val productService: ProductServiceImpl,
    private val productRedisRepository: ProductRedisRepository
) : ProductService {

    override fun getProduct(id: String): Mono<Product> {
        val cacheResult = productRedisRepository.findById(id)
        return cacheResult.hasElement()
            .flatMap {
                when (it) {
                true -> cacheResult
                else -> getFromDatabase(id)
            }
            }
    }

    override fun saveProduct(product: Product): Mono<Product> {
        return productService.saveProduct(product)
            .flatMap { productRedisRepository.save(it.id, it) }
    }

    override fun deleteAllProducts(): Mono<Boolean> {
        return productService.deleteAllProducts()
            .then(productRedisRepository.deleteAll())
    }

    override fun getAllProducts(): Flux<Product> {
        val cacheResult = productRedisRepository.findAll()
        return cacheResult.hasElements()
            .flatMapMany {
                when (it) {
                true -> cacheResult
                else -> getAllFromDatabase()
            }
            }
    }

    private fun getFromDatabase(id: String): Mono<Product> {
        return productService.getProduct(id)
            .flatMap { productRedisRepository.save(it.id, it) }
    }

    private fun getAllFromDatabase(): Flux<Product> {
        return productService.getAllProducts()
            .collectMap(Product::id)
            .flatMap(productRedisRepository::saveAll)
            .flatMapIterable { it.values }
    }
}
