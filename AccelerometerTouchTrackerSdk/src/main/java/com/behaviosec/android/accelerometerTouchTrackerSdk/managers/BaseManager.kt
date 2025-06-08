package com.behaviosec.android.accelerometerTouchTrackerSdk.managers

import com.behaviosec.android.accelerometerTouchTrackerSdk.config.TouchTrackerConfig

open class BaseManager(
    protected val config: TouchTrackerConfig = TouchTrackerConfig()
) {

    protected var isLoggingEnabled: Boolean = config.isLoggingEnabled

    fun setEnabled(enabled: Boolean): BaseManager {
        this.config.isEnabled = enabled
        return this
    }

    fun setDebugMode(debugMode: Boolean): BaseManager {
        this.config.isDebugMode = debugMode
        return this
    }

    fun setLoggingEnabled(loggingEnabled: Boolean): BaseManager {
        this.isLoggingEnabled = loggingEnabled
        return this
    }

}
