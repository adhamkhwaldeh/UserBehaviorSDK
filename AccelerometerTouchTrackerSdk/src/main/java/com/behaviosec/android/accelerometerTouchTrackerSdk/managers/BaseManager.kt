package com.behaviosec.android.accelerometerTouchTrackerSdk.managers

import com.behaviosec.android.accelerometerTouchTrackerSdk.config.TouchTrackerConfig

/**
 * Base manager
 *
 * @property config
 * @constructor Create empty Base manager
 */
open class BaseManager(
    protected val config: TouchTrackerConfig = TouchTrackerConfig()
) {

    protected var isLoggingEnabled: Boolean = config.isLoggingEnabled

    /**
     * Set enabled
     *
     * @param enabled
     * @return
     */
    fun setEnabled(enabled: Boolean): BaseManager {
        this.config.isEnabled = enabled
        return this
    }

    /**
     * Set debug mode
     *
     * @param debugMode
     * @return
     */
    fun setDebugMode(debugMode: Boolean): BaseManager {
        this.config.isDebugMode = debugMode
        return this
    }

    /**
     * Set logging enabled
     *
     * @param loggingEnabled
     * @return
     */
    fun setLoggingEnabled(loggingEnabled: Boolean): BaseManager {
        this.isLoggingEnabled = loggingEnabled
        return this
    }

}
