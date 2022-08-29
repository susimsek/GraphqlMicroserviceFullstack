package io.github.susimsek.product.repository.redis

import io.github.susimsek.mscommonweb.cache.AbstractReactiveRedisRepository
import io.github.susimsek.product.model.Product
import org.springframework.stereotype.Repository

@Repository
class ProductRedisRepository : AbstractReactiveRedisRepository<Product>() {
    override fun getKey(): String {
        return "product"
    }
}
