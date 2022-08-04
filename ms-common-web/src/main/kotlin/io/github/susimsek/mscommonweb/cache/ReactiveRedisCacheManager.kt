package io.github.susimsek.mscommonweb.cache

import org.springframework.data.redis.core.ReactiveHashOperations
import org.springframework.data.redis.core.ReactiveRedisOperations
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration
import javax.annotation.PostConstruct

class ReactiveRedisCacheManager<T>(
    private val redisOperations: ReactiveRedisOperations<String, Any>,
    private val cacheProperties: CacheProperties
) : ReactiveCacheManager<T> {
    private lateinit var reactiveHashOperations: ReactiveHashOperations<String, String, T>

    @PostConstruct
    private fun init() {
        reactiveHashOperations = redisOperations.opsForHash()
    }

    override fun put(key: String, hashKey: String, entity: T): Mono<T> {
        return put(key, hashKey, entity, cacheProperties.expiration).map { entity }
    }

    override fun put(key: String, hashKey: String, entity: T, expiration: Duration): Mono<T> {
        val update = reactiveHashOperations.put(key, hashKey, entity)
        val setTtl = redisOperations.expire(key, expiration)
        return update.then(setTtl).map { entity }
    }

    override fun getAll(key: String): Flux<T> {
        return reactiveHashOperations.values(key)
    }

    override fun putAll(key: String, map: Map<String, T>): Flux<T> {
        return putAll(key, map, cacheProperties.expiration)
    }

    override fun putAll(key: String, map: Map<String, T>, expiration: Duration): Flux<T> {
        val update = reactiveHashOperations.putAll(key, map)
            .flatMapIterable { map.values }
        val setTtl = redisOperations.expire(key, expiration)
        return update.then(setTtl).flatMapMany{update}
    }

    override fun get(key: String, hashKey: String): Mono<T> {
        return reactiveHashOperations.get(key, hashKey)
    }

    override fun delete(key: String, hashKey: String): Mono<Long> {
        return reactiveHashOperations.remove(key, hashKey)
    }

    override fun deleteAll(key: String): Mono<Boolean> {
        return reactiveHashOperations.delete(key)
    }
}