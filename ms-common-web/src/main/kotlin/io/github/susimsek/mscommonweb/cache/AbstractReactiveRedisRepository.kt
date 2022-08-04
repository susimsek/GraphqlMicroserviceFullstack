package io.github.susimsek.mscommonweb.cache

import org.springframework.beans.factory.annotation.Autowired
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

abstract class AbstractReactiveRedisRepository<T> : ReactiveRedisRepository<T> {

    @Autowired
    private lateinit var reactiveCacheManager: ReactiveCacheManager<T>

    abstract fun getKey(): String

    override fun save(id: String, entity: T): Mono<T> {
        return reactiveCacheManager.put(getKey(), id, entity)
    }

    override fun findAll(): Flux<T> {
        return reactiveCacheManager.getAll(getKey())
    }

    override fun findById(id: String): Mono<T> {
        return reactiveCacheManager.get(getKey(), id)
    }

    override fun delete(id: String): Mono<Long> {
        return reactiveCacheManager.delete(getKey(), id)
    }

    override fun deleteAll(): Mono<Boolean> {
        return reactiveCacheManager.deleteAll(getKey())
    }

    override fun saveAll(map: Map<String, T>): Flux<T> {
        return reactiveCacheManager.putAll(getKey(), map)
    }

    override fun cacheManager(): ReactiveCacheManager<T> {
       return reactiveCacheManager
    }
}