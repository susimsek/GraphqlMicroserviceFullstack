package io.github.susimsek.mscommonweb.test.config

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.beans.factory.InitializingBean
import org.testcontainers.containers.MongoDBContainer
import org.testcontainers.containers.output.Slf4jLogConsumer
import java.util.Collections

class MongoDbTestContainer : InitializingBean, DisposableBean {
    var mongoDBContainer: MongoDBContainer? = null
        private set

    override fun destroy() {
        if (null != mongoDBContainer && mongoDBContainer!!.isRunning) {
            mongoDBContainer!!.stop()
        }
    }

    override fun afterPropertiesSet() {
        if (null == mongoDBContainer) {
            mongoDBContainer = MongoDBContainer("mongo:5.0")
                .withTmpFs(Collections.singletonMap("/testtmpfs", "rw"))
                .withLogConsumer(Slf4jLogConsumer(log))
                .withReuse(true)
        }
        if (!mongoDBContainer!!.isRunning) {
            mongoDBContainer!!.start()
        }
    }

    companion object {
        /* private final long memoryInBytes = Math.round(1024 * 1024 * 1024 * 0.6);
    private final long memorySwapInBytes = Math.round(1024 * 1024 * 1024 * 0.8);
    private final long nanoCpu = Math.round(1_000_000_000L * 0.1); */
        private val log = LoggerFactory.getLogger(MongoDbTestContainer::class.java)
    }
}
