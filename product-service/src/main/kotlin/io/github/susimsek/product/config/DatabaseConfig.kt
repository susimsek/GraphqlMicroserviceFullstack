package io.github.susimsek.product.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@Configuration(proxyBeanMethods = false)
@EnableReactiveMongoRepositories("io.github.susimsek.product.repository")
class DatabaseConfig
