package io.github.susimsek.mscommonweb.cache

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

interface ReactiveCacheManager<T> {
    fun put(key: String, hashKey:String, entity: T): Mono<T>
    fun put(key: String, hashKey: String, entity: T, expiration: Duration): Mono<T>
    fun getAll(key: String): Flux<T>
    fun putAll(key: String, map: Map<String, T>): Mono<Map<String, T>>
    fun putAll(key: String, map: Map<String, T>, expiration: Duration): Mono<Map<String, T>>
    fun get(key: String, hashKey:String): Mono<T>
    fun delete(key: String, hashKey:String): Mono<Long>
    fun deleteAll(key: String): Mono<Boolean>
}