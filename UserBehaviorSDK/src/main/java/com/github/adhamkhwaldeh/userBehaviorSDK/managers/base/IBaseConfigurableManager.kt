package com.github.adhamkhwaldeh.userBehaviorSDK.managers.base

import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.configs.IManagerConfigInterface

/**
 * IBaseConfigurableManager interface for managers that support configuration.
 */
interface IBaseConfigurableManager<T : IManagerConfigInterface> {

    /**
     * Set enabled
     *
     * @param enabled
     * @return
     */
    fun setEnabled(enabled: Boolean): IBaseConfigurableManager<T>

    /**
     * Set debug mode
     *
     * @param debugMode
     * @return
     */
    fun setDebugMode(debugMode: Boolean): IBaseConfigurableManager<T>

    /**
     * Set logging enabled
     *
     * @param loggingEnabled
     * @return
     */
    fun setLoggingEnabled(loggingEnabled: Boolean): IBaseConfigurableManager<T>


}

