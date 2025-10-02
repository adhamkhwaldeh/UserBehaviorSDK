package com.behaviosec.android.userBehaviorSDK.managers

import com.behaviosec.android.userBehaviorSDK.config.TouchTrackerConfig

/**
 * IBaseConfigurableManager interface for managers that support configuration.
 */
abstract class BaseConfigurableManager(val config: TouchTrackerConfig) {

    /**
     * Configure the manager with the provided configuration object.
     * @param config configuration data
     */

    protected var isLoggingEnabled: Boolean = config.isLoggingEnabled

    /**
     * Set enabled
     *
     * @param enabled
     * @return
     */
    fun setEnabled(enabled: Boolean): BaseConfigurableManager {
        this.config.isEnabled = enabled
        return this
    }

    /**
     * Set debug mode
     *
     * @param debugMode
     * @return
     */
    fun setDebugMode(debugMode: Boolean): BaseConfigurableManager {
        this.config.isDebugMode = debugMode
        return this
    }

    /**
     * Set logging enabled
     *
     * @param loggingEnabled
     * @return
     */
    fun setLoggingEnabled(loggingEnabled: Boolean): BaseConfigurableManager {
        this.isLoggingEnabled = loggingEnabled
        return this
    }

}

