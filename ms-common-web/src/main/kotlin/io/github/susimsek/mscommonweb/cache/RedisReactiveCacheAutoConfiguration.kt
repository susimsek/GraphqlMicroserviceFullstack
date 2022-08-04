package io.github.susimsek.mscommonweb.cache

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(CacheProperties::class)
class RedisReactiveCacheAutoConfiguration {

    @Bean
    fun ObjectMapper.jackson2JsonRedisSerializer(): RedisSerializer<Any> {
        val om = jacksonObjectMapper()
            .registerModule(JavaTimeModule())
            om.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            om.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, false)
            .activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(Any::class.java)
                .build(), ObjectMapper.DefaultTyping.EVERYTHING)
        return GenericJackson2JsonRedisSerializer(om)
    }

    @Bean
    fun redisOperations(factory: ReactiveRedisConnectionFactory, jackson2JsonRedisSerializer: RedisSerializer<Any>): ReactiveRedisOperations<String, Any> {
        val stringRedisSerializer = StringRedisSerializer()
        val context: RedisSerializationContext<String, Any> = RedisSerializationContext
            .newSerializationContext<String, Any>(StringRedisSerializer())
            .key(stringRedisSerializer)
            .value(jackson2JsonRedisSerializer)
            .hashKey(stringRedisSerializer)
            .hashValue(jackson2JsonRedisSerializer)
            .build()
        return ReactiveRedisTemplate(factory, context)
    }

    @Bean
    fun <T> reactiveCacheManager(redisOperations: ReactiveRedisOperations<String, Any>,
                                  cacheProperties: CacheProperties): ReactiveRedisCacheManager<T> {
        return ReactiveRedisCacheManager(redisOperations, cacheProperties)
    }
}
