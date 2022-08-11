package io.github.susimsek.product.config

import org.slf4j.LoggerFactory
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.annotation.AnnotatedElementUtils
import org.springframework.test.context.ContextConfigurationAttributes
import org.springframework.test.context.ContextCustomizer
import org.springframework.test.context.ContextCustomizerFactory
import org.springframework.test.context.MergedContextConfiguration

class TestContainersSpringContextCustomizerFactory : ContextCustomizerFactory {
    private val log = LoggerFactory.getLogger(TestContainersSpringContextCustomizerFactory::class.java)
    override fun createContextCustomizer(
        testClass: Class<*>,
        configAttributes: List<ContextConfigurationAttributes>
    ): ContextCustomizer {
        return ContextCustomizer { context: ConfigurableApplicationContext, mergedConfig: MergedContextConfiguration? ->
            val beanFactory = context.beanFactory
            var testValues = TestPropertyValues.empty()
            val mongoAnnotation = AnnotatedElementUtils.findMergedAnnotation(testClass, EmbeddedMongo::class.java)
            if (null != mongoAnnotation) {
                log.debug("detected the EmbeddedMongo annotation on class {}", testClass.name)
                log.info("Warming up the mongo database")
                if (null == mongoDbBean) {
                    mongoDbBean = beanFactory.createBean(
                        MongoDbTestContainer::class.java
                    )
                    beanFactory.registerSingleton(MongoDbTestContainer::class.java.name, mongoDbBean)
                }
                testValues = testValues.and("spring.data.mongodb.uri=" + mongoDbBean!!.mongoDBContainer!!.replicaSetUrl)
            }
            val redisAnnotation = AnnotatedElementUtils.findMergedAnnotation(testClass, EmbeddedRedis::class.java)
            if (null != redisAnnotation) {
                log.debug("detected the EmbeddedRedis annotation on class {}", testClass.name)
                log.info("Warming up the redis database")
                if (null == redisBean) {
                    redisBean = beanFactory.createBean(RedisTestContainer::class.java)
                    beanFactory.registerSingleton(RedisTestContainer::class.java.name, redisBean)
                }
                testValues = testValues.and(
                    "spring.redis.host=" + redisBean!!.redisContainer!!.host,
                    "spring.redis.port=" + redisBean!!.redisContainer!!.port,
                    "spring.redis.database=" + redisBean!!.redisContainer!!.database
                )
            }
            testValues.applyTo(context)
        }
    }

    companion object {
        private var mongoDbBean: MongoDbTestContainer? = null
        private var redisBean: RedisTestContainer? = null
    }
}