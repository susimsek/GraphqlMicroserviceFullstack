package io.github.susimsek.mscommonweb.test.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.testcontainers.containers.output.Slf4jLogConsumer

class RedisTestContainer : InitializingBean, DisposableBean {
    var redisContainer: RedisContainer? = null
        private set

    override fun destroy() {
        if (null != redisContainer && redisContainer!!.isRunning) {
            redisContainer!!.stop()
        }
    }

    override fun afterPropertiesSet() {
        if (null == redisContainer) {
            redisContainer = RedisContainer("redis:7.0.4")
                .withLogConsumer(Slf4jLogConsumer(log))
                .withReuse(true)
        }
        if (!redisContainer!!.isRunning) {
            redisContainer!!.start()
        }
    }

    companion object {
        /* private final long memoryInBytes = Math.round(1024 * 1024 * 1024 * 0.6);
    private final long memorySwapInBytes = Math.round(1024 * 1024 * 1024 * 0.8);
    private final long nanoCpu = Math.round(1_000_000_000L * 0.1); */
        private val log = LoggerFactory.getLogger(RedisTestContainer::class.java)
    }
}
