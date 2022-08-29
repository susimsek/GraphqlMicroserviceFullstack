package io.github.susimsek.mscommonweb.logging

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.Logger
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.spi.LoggerContextListener
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.spi.ContextAwareBase
import net.logstash.logback.appender.LogstashTcpSocketAppender
import net.logstash.logback.composite.ContextJsonProvider
import net.logstash.logback.composite.GlobalCustomFieldsJsonProvider
import net.logstash.logback.composite.loggingevent.ArgumentsJsonProvider
import net.logstash.logback.composite.loggingevent.LogLevelJsonProvider
import net.logstash.logback.composite.loggingevent.LoggerNameJsonProvider
import net.logstash.logback.composite.loggingevent.LoggingEventFormattedTimestampJsonProvider
import net.logstash.logback.composite.loggingevent.LoggingEventJsonProviders
import net.logstash.logback.composite.loggingevent.LoggingEventPatternJsonProvider
import net.logstash.logback.composite.loggingevent.LoggingEventThreadNameJsonProvider
import net.logstash.logback.composite.loggingevent.MdcJsonProvider
import net.logstash.logback.composite.loggingevent.MessageJsonProvider
import net.logstash.logback.composite.loggingevent.StackTraceJsonProvider
import net.logstash.logback.encoder.LoggingEventCompositeJsonEncoder
import net.logstash.logback.encoder.LogstashEncoder
import net.logstash.logback.stacktrace.ShortenedThrowableConverter
import org.slf4j.LoggerFactory
import java.net.InetSocketAddress

object LoggingUtils {
    private val log = LoggerFactory.getLogger(LoggingUtils::class.java)
    private const val CONSOLE_APPENDER_NAME = "CONSOLE"
    private const val ASYNC_LOGSTASH_APPENDER_NAME = "ASYNC_LOGSTASH"

    /**
     *
     * addJsonConsoleAppender.
     *
     * @param context a [ch.qos.logback.classic.LoggerContext] object.
     * @param customFields a [java.lang.String] object.
     */
    fun addJsonConsoleAppender(context: LoggerContext, customFields: String) {
        log.info("Initializing Console loggingProperties")

        // More documentation is available at: https://github.com/logstash/logstash-logback-encoder
        val consoleAppender = ConsoleAppender<ILoggingEvent>()
        consoleAppender.context = context
        consoleAppender.encoder =
            compositeJsonEncoder(context, customFields)
        consoleAppender.name = CONSOLE_APPENDER_NAME
        consoleAppender.start()
        context.getLogger(Logger.ROOT_LOGGER_NAME).detachAppender(CONSOLE_APPENDER_NAME)
        context.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(consoleAppender)
    }

    fun addLogstashTcpSocketAppender(
        context: LoggerContext,
        customFields: String,
        logstash: Logstash
    ) {
        log.info("Initializing Logstash loggingProperties")

        // More documentation is available at: https://github.com/logstash/logstash-logback-encoder
        val logstashAppender = LogstashTcpSocketAppender()
        logstashAppender.addDestinations(InetSocketAddress(logstash.host, logstash.port))
        logstashAppender.context = context
        logstashAppender.encoder = logstashEncoder(customFields)
        logstashAppender.name = ASYNC_LOGSTASH_APPENDER_NAME
        logstashAppender.ringBufferSize = logstash.ringBufferSize
        logstashAppender.start()
        context.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(logstashAppender)
    }

    /**
     *
     * addContextListener.
     *
     * @param context a [ch.qos.logback.classic.LoggerContext] object.
     * @param customFields a [java.lang.String] object.
     * @param properties a [LoggingProperties] object.
     */
    fun addContextListener(context: LoggerContext, customFields: String, properties: LoggingProperties) {
        val loggerContextListener = LogbackLoggerContextListener(properties, customFields)
        loggerContextListener.context = context
        context.addListener(loggerContextListener)
    }

    private fun compositeJsonEncoder(context: LoggerContext, customFields: String): LoggingEventCompositeJsonEncoder {
        val compositeJsonEncoder = LoggingEventCompositeJsonEncoder()
        compositeJsonEncoder.context = context
        compositeJsonEncoder.providers = jsonProviders(context, customFields)
        compositeJsonEncoder.start()
        return compositeJsonEncoder
    }

    private fun logstashEncoder(customFields: String): LogstashEncoder {
        val logstashEncoder = LogstashEncoder()
        logstashEncoder.throwableConverter = throwableConverter()
        logstashEncoder.customFields = customFields
        return logstashEncoder
    }

    private fun jsonProviders(context: LoggerContext, customFields: String): LoggingEventJsonProviders {
        val jsonProviders = LoggingEventJsonProviders()
        jsonProviders.addArguments(ArgumentsJsonProvider())
        jsonProviders.addContext(ContextJsonProvider())
        jsonProviders.addGlobalCustomFields(customFieldsJsonProvider(customFields))
        jsonProviders.addLogLevel(LogLevelJsonProvider())
        jsonProviders.addLoggerName(loggerNameJsonProvider())
        jsonProviders.addMdc(MdcJsonProvider())
        jsonProviders.addMessage(MessageJsonProvider())
        jsonProviders.addPattern(LoggingEventPatternJsonProvider())
        jsonProviders.addStackTrace(stackTraceJsonProvider())
        jsonProviders.addThreadName(LoggingEventThreadNameJsonProvider())
        jsonProviders.addTimestamp(timestampJsonProvider())
        jsonProviders.setContext(context)
        return jsonProviders
    }

    private fun customFieldsJsonProvider(customFields: String): GlobalCustomFieldsJsonProvider<ILoggingEvent> {
        val customFieldsJsonProvider = GlobalCustomFieldsJsonProvider<ILoggingEvent>()
        customFieldsJsonProvider.customFields = customFields
        return customFieldsJsonProvider
    }

    private fun loggerNameJsonProvider(): LoggerNameJsonProvider {
        val loggerNameJsonProvider = LoggerNameJsonProvider()
        loggerNameJsonProvider.shortenedLoggerNameLength = 20
        return loggerNameJsonProvider
    }

    private fun stackTraceJsonProvider(): StackTraceJsonProvider {
        val stackTraceJsonProvider = StackTraceJsonProvider()
        stackTraceJsonProvider.throwableConverter = throwableConverter()
        return stackTraceJsonProvider
    }

    private fun throwableConverter(): ShortenedThrowableConverter {
        val throwableConverter = ShortenedThrowableConverter()
        throwableConverter.isRootCauseFirst = true
        return throwableConverter
    }

    private fun timestampJsonProvider(): LoggingEventFormattedTimestampJsonProvider {
        val timestampJsonProvider = LoggingEventFormattedTimestampJsonProvider()
        timestampJsonProvider.timeZone = "UTC"
        timestampJsonProvider.fieldName = "timestamp"
        return timestampJsonProvider
    }

    /**
     * Logback configuration is achieved by configuration file and API.
     * When configuration file change is detected, the configuration is reset.
     * This listener ensures that the programmatic configuration is also re-applied after reset.
     */
    private class LogbackLoggerContextListener(
        private val loggingProperties: LoggingProperties,
        private val customFields: String
    ) : ContextAwareBase(), LoggerContextListener {
        override fun isResetResistant(): Boolean {
            return true
        }

        override fun onStart(context: LoggerContext) {
            if (loggingProperties.useJsonFormat) {
                addJsonConsoleAppender(context, customFields)
            }
            if (loggingProperties.logstash.enabled) {
                addLogstashTcpSocketAppender(context, customFields, loggingProperties.logstash)
            }
        }

        override fun onReset(context: LoggerContext) {
            if (loggingProperties.useJsonFormat) {
                addJsonConsoleAppender(context, customFields)
            }
            if (loggingProperties.logstash.enabled) {
                addLogstashTcpSocketAppender(context, customFields, loggingProperties.logstash)
            }
        }

        override fun onStop(context: LoggerContext) {
            // Nothing to do.
        }

        override fun onLevelChange(logger: Logger, level: Level) {
            // Nothing to do.
        }
    }
}
