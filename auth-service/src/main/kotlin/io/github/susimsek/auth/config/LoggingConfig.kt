package io.github.susimsek.auth.config

import ch.qos.logback.classic.LoggerContext
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.susimsek.auth.config.LoggingUtils.addContextListener
import io.github.susimsek.auth.config.LoggingUtils.addJsonConsoleAppender
import io.github.susimsek.auth.config.LoggingUtils.addLogstashTcpSocketAppender
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(LoggingProperties::class)
class LoggingConfig(
    env: Environment,
    loggingProperties: LoggingProperties,
    objectMapper: ObjectMapper
) {
    init {
        val context = LoggerFactory.getILoggerFactory() as LoggerContext

        val map = mutableMapOf<String, String?>()
        map["app_name"] = env.getProperty("spring.application.name")
        map["app_port"] = env.getProperty("server.port")
        val customFields = objectMapper.writeValueAsString(map)

        val logstashProperties = loggingProperties.logstash

        if (loggingProperties.useJsonFormat) {
            addJsonConsoleAppender(context, customFields)
        }
        if (logstashProperties.enabled) {
            addLogstashTcpSocketAppender(context, customFields, logstashProperties)
        }
        if (loggingProperties.useJsonFormat || logstashProperties.enabled) {
            addContextListener(context, customFields, loggingProperties)
        }
    }
}
