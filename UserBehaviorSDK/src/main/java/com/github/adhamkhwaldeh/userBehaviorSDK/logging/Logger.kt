package com.github.adhamkhwaldeh.userBehaviorSDK.logging

import com.github.adhamkhwaldeh.userBehaviorSDK.config.BaseBehaviorConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.config.UserBehaviorSDKConfig
import java.util.concurrent.CopyOnWriteArrayList

/**
 * A static proxy object that dispatches logging calls to multiple `ILogger` implementations.
 *
 * By default, it includes the `DefaultLogger`. Clients can add or remove loggers at any time.
 * This allows the SDK's logging to be broadcast to multiple destinations, such as Logcat,
 * a remote analytics service, and a crash reporter simultaneously.
 *
 * Example Usage in an Application's `onCreate`:
 * ```
 * // Add a custom logger for remote analytics
 * Logger.addLogger(MyRemoteAnalyticsLogger())
 *
 * // In debug builds, you might also want a Timber logger
 * if (BuildConfig.DEBUG) {
 *     Logger.addLogger(MyTimberLogger())
 * }
 * ```
 */
class Logger : ILogger {
    private val loggers = CopyOnWriteArrayList<ILogger>()

    init {
        // Add the default logger so logging works out of the box.
        loggers.add(DefaultLogger())
    }

    fun setLoggers(newLoggers: List<ILogger>) {
        loggers.clear()
        loggers.addAll(newLoggers)
    }

    /**
     * Adds a new logger implementation to the list of loggers.
     * @param newLogger The new `ILogger` instance to add.
     */
    fun addLogger(newLogger: ILogger) {
        loggers.add(newLogger)
    }

    /**
     * Removes a logger implementation from the list.
     * @param logger The `ILogger` instance to remove.
     */
    fun removeLogger(logger: ILogger) {
        loggers.remove(logger)
    }

    /**
     * Removes all registered loggers, including the default one.
     */
    fun clearLoggers() {
        loggers.clear()
    }

    override fun d(tag: String, message: String, config: BaseBehaviorConfig) {
        for (logger in loggers) {
            logger.d(tag, message, config)
        }
    }

    override fun e(tag: String, message: String, config: BaseBehaviorConfig, throwable: Throwable?) {
        for (logger in loggers) {
            logger.e(tag, message, config, throwable)
        }
    }

    override fun w(tag: String, message: String, config: BaseBehaviorConfig) {
        for (logger in loggers) {
            logger.w(tag, message, config)
        }
    }

    override fun i(tag: String, message: String, config: BaseBehaviorConfig) {
        for (logger in loggers) {
            logger.i(tag, message, config)
        }
    }
}
