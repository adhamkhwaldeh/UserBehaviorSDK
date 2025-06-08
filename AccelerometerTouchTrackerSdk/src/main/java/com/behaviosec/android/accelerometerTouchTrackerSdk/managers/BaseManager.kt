package com.behaviosec.android.accelerometerTouchTrackerSdk.managers


abstract class BaseManager {

    protected var isEnabled: Boolean = true
    protected var isDebugMode: Boolean = false
    protected var isLoggingEnabled: Boolean = false

    fun setEnabled(enabled: Boolean): BaseManager {
        this.isEnabled = enabled
        return this
    }

    fun setDebugMode(debugMode: Boolean): BaseManager {
        this.isDebugMode = debugMode
        return this
    }

    fun setLoggingEnabled(loggingEnabled: Boolean): BaseManager {
        this.isLoggingEnabled = loggingEnabled
        return this
    }

//    abstract fun build(): BaseManager
}