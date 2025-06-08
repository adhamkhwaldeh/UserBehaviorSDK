package com.behaviosec.android.accelerometerTouchTrackerSdk.logging

import android.util.Log

enum class LogLevel { NONE, ERROR, WARN, INFO, DEBUG }

//You can Integrate with external logging/analytics frameworks (e.g., Timber, Crashlytics).
object Logger {
    var logLevel: LogLevel = LogLevel.INFO
    private var customLogger: ((level: LogLevel, tag: String, message: String, throwable: Throwable?) -> Unit)? =
        null

    fun d(tag: String, message: String) = log(LogLevel.DEBUG, tag, message)
    fun i(tag: String, message: String) = log(LogLevel.INFO, tag, message)
    fun w(tag: String, message: String) = log(LogLevel.WARN, tag, message)
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
