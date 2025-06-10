package com.behaviosec.android.accelerometerTouchTrackerSdk.config

import com.behaviosec.android.accelerometerTouchTrackerSdk.logging.LogLevel

/**
 * Touch tracker config
 *
 * @property isEnabled
 * @property isDebugMode
 * @property isLoggingEnabled
 * @property logLevel
 * @constructor Create empty Touch tracker config
 */
data class TouchTrackerConfig(
    var isEnabled: Boolean = true,
    var isDebugMode: Boolean = false,
    val isLoggingEnabled: Boolean = false,
    val logLevel: LogLevel = LogLevel.INFO,
) {

    /**
     * Set enabled
     *
     * @param enabled
     * @return
     */
    fun setEnabled(enabled: Boolean): TouchTrackerConfig {
        this.isEnabled = enabled
        return this
    }

    /**
     * Set debug mode
     *
     * @param debugMode
     * @return
     */
    fun setDebugMode(debugMode: Boolean): TouchTrackerConfig {
        this.isDebugMode = debugMode
        return this
    }

    /**
     * Set logging enabled
     *
     * @param loggingEnabled
     * @return
     */
    fun setLoggingEnabled(loggingEnabled: Boolean): TouchTrackerConfig {
        return copy(isLoggingEnabled = loggingEnabled)
    }

    /**
     * Set log level
     *
     * @param logLevel
     * @return
     */
    fun setLogLevel(logLevel: LogLevel): TouchTrackerConfig {
        return copy(logLevel = logLevel)
    }
}
