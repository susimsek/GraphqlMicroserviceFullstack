package io.github.susimsek.mscommonweb.test

import io.github.susimsek.mscommonweb.test.config.EmbeddedMongo
import io.github.susimsek.mscommonweb.test.config.EmbeddedRedis
import io.github.susimsek.mscommonweb.test.config.TestSecurityConfig
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureHttpGraphQlTester
import org.springframework.boot.test.context.SpringBootTest

/**
 * Base composite annotation for integration tests.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(classes = [TestSecurityConfig::class])
@EmbeddedMongo
@EmbeddedRedis
@AutoConfigureHttpGraphQlTester
annotation class IntegrationTest
