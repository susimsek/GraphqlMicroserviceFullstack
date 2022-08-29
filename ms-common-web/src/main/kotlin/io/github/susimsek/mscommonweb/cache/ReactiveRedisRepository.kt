package io.github.susimsek.mscommonweb.cache

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ReactiveRedisRepository<T> {
    fun save(id: String, entity: T): Mono<T>
    fun findAll(): Flux<T>
    fun saveAll(map: Map<String, T>): Mono<Map<String, T>>
    fun findById(id: String): Mono<T>
    fun delete(id: String): Mono<Long>
    fun deleteAll(): Mono<Boolean>
    fun cacheManager(): ReactiveCacheManager<T>
}
