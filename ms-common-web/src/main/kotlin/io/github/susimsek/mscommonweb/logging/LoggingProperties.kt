package io.github.susimsek.mscommonweb.logging

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("logging")
@ConstructorBinding
data class LoggingProperties(
    var useJsonFormat: Boolean = false,
    var logstash: Logstash = Logstash(false, "localhost", 5000, 512)
)

data class Logstash(
    var enabled: Boolean,
                    var host: String,
                    var port: Int,
                    var ringBufferSize: Int
)
