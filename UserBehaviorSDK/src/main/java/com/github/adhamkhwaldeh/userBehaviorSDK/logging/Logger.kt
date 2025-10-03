package com.github.adhamkhwaldeh.userBehaviorSDK.logging

import android.util.Log

/**
 * Log level
 *
 * @constructor Create empty Log level
 */
enum class LogLevel {
    /**
     * None
     *
     * @constructor Create empty None
     */
    NONE,

    /**
     * Error
     *
     * @constructor Create empty Error
     */
    ERROR,

    /**
     * Warn
     *
     * @constructor Create empty Warn
     */
    WARN,

    /**
     * Info
     *
     * @constructor Create empty Info
     */
    INFO,

    /**
     * Debug
     *
     * @constructor Create empty Debug
     */
    DEBUG }

/**
 * Logger
 *
 * @constructor Create empty Logger
 *///You can Integrate with external logging/analytics frameworks (e.g., Timber, Crashlytics).
object Logger {
    
    var logLevel: LogLevel = LogLevel.INFO
    
    private var customLogger: ((level: LogLevel, tag: String, message: String, throwable: Throwable?) -> Unit)? =
        null

    /**
     * D
     *
     * @param tag
     * @param message
     */
    fun d(tag: String, message: String) = log(LogLevel.DEBUG, tag, message)

    /**
     * I
     *
     * @param tag
     * @param message
     */
    fun i(tag: String, message: String) = log(LogLevel.INFO, tag, message)

    /**
     * W
     *
     * @param tag
     * @param message
     */
    fun w(tag: String, message: String) = log(LogLevel.WARN, tag, message)

    /**
     * E
     *
     * @param tag
     * @param message
     * @param throwable
     */
    fun e(tag: String, message: String, throwable: Throwable? = null) =
        log(LogLevel.ERROR, tag, message, throwable)

    private fun log(level: LogLevel, tag: String, message: String, throwable: Throwable? = null) {
        if (level.ordinal > logLevel.ordinal || logLevel == LogLevel.NONE) return
        customLogger?.invoke(level, tag, message, throwable) ?: run {
            when (level) {
                LogLevel.ERROR -> Log.e(tag, message, throwable)
                LogLevel.WARN -> Log.w(tag, message)
                LogLevel.INFO -> Log.i(tag, message)
                LogLevel.DEBUG -> Log.d(tag, message)
                else -> {}
            }
        }
    }

}
