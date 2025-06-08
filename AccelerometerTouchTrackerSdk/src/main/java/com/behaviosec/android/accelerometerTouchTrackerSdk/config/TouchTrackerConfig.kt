package com.behaviosec.android.accelerometerTouchTrackerSdk.config

import com.behaviosec.android.accelerometerTouchTrackerSdk.logging.LogLevel

data class TouchTrackerConfig(
    var isEnabled: Boolean = true,
    var isDebugMode: Boolean = false,
    val isLoggingEnabled: Boolean = false,
    val logLevel: LogLevel = LogLevel.INFO,
) {

    fun setEnabled(enabled: Boolean): TouchTrackerConfig {
        this.isEnabled = enabled
        return this
    }

    fun setDebugMode(debugMode: Boolean): TouchTrackerConfig {
        this.isDebugMode = debugMode
        return this
    }

    fun setLoggingEnabled(loggingEnabled: Boolean): TouchTrackerConfig {
        return copy(isLoggingEnabled = loggingEnabled)
    }

    fun setLogLevel(logLevel: LogLevel): TouchTrackerConfig {
        return copy(logLevel = logLevel)
    }
}
