package io.github.susimsek.mscommonweb.test.config

import io.github.susimsek.mscommonweb.test.config.RedisContainer
import org.slf4j.LoggerFactory
import org.springframework.lang.NonNull
import org.testcontainers.containers.GenericContainer
import org.testcontainers.utility.DockerImageName

class RedisContainer(dockerImageName: DockerImageName) : GenericContainer<RedisContainer>(dockerImageName) {
    constructor(@NonNull dockerImageName: String?) : this(DockerImageName.parse(dockerImageName))

    init {
        dockerImageName.assertCompatibleWith(DEFAULT_IMAGE_NAME)
        withExposedPorts(REDIS_INTERNAL_PORT)
    }

    val port: Int
        get() = getMappedPort(REDIS_INTERNAL_PORT)

    val database: String
        get() = REDIS_DATABASE_NAME_DEFAULT

    companion object {
        private val log = LoggerFactory.getLogger(RedisContainer::class.java)
        private val DEFAULT_IMAGE_NAME = DockerImageName.parse("redis")
        private const val DEFAULT_TAG = "7.0.4"
        private const val CONTAINER_EXIT_CODE_OK = 0
        private const val REDIS_INTERNAL_PORT = 6379
        private const val AWAIT_INIT_REPLICA_SET_ATTEMPTS = 60
        private const val REDIS_DATABASE_NAME_DEFAULT = "0"
    }
}