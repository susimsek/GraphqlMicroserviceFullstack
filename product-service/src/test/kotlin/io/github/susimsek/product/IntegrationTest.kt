package io.github.susimsek.product



import io.github.susimsek.product.config.EmbeddedMongo
import io.github.susimsek.product.config.EmbeddedRedis
import io.github.susimsek.product.config.TestSecurityConfig
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester
import org.springframework.boot.test.context.SpringBootTest

/**
 * Base composite annotation for integration tests.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(classes = [ProductApplication::class, TestSecurityConfig::class])
@EmbeddedMongo
@EmbeddedRedis
@AutoConfigureHttpGraphQlTester
annotation class IntegrationTest