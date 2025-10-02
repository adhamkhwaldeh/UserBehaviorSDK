package com.behaviosec.android.userBehaviorSDK.managers.base

import com.behaviosec.android.userBehaviorSDK.config.TouchTrackerConfig

/**
 * IBaseConfigurableManager interface for managers that support configuration.
 */
interface IBaseConfigurableManager {

    val config: TouchTrackerConfig

    /**
     * Configure the manager with the provided configuration object.
     * @param config configuration data
     */

    /**
     * Set enabled
     *
     * @param enabled
     * @return
     */
    fun setEnabled(enabled: Boolean): IBaseConfigurableManager {
        this.config.isEnabled = enabled
        return this
    }

    /**
     * Set debug mode
     *
     * @param debugMode
     * @return
     */
    fun setDebugMode(debugMode: Boolean): IBaseConfigurableManager {
        this.config.isDebugMode = debugMode
        return this
    }

    /**
     * Set logging enabled
     *
     * @param loggingEnabled
     * @return
     */
    fun setLoggingEnabled(loggingEnabled: Boolean): IBaseConfigurableManager {
        config.setEnabled(loggingEnabled)
        return this
    }


}

