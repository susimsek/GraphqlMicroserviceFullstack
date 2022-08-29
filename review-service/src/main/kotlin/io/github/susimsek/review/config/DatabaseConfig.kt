package io.github.susimsek.review.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration(proxyBeanMethods = false)
@EnableReactiveMongoRepositories("io.github.susimsek.review.repository")
class DatabaseConfig
