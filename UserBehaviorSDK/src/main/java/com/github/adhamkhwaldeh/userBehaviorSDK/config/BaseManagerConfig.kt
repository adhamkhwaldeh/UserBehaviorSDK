package com.github.adhamkhwaldeh.userBehaviorSDK.config

import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.configs.IManagerConfigInterface
import com.github.adhamkhwaldeh.userBehaviorSDK.logging.LogLevel

abstract class BaseManagerConfig() : IManagerConfigInterface {

    override var isEnabled: Boolean = true
    override var isDebugMode: Boolean = false
    override var isLoggingEnabled: Boolean = true
    override var logLevel: LogLevel = LogLevel.DEBUG


    /**
     * Set enabled
     *
     * @param enabled
     * @return
     */
    fun setEnabled(enabled: Boolean): BaseManagerConfig {
        this.isEnabled = enabled
        return this
    }

    /**
     * Set debug mode
     *
     * @param debugMode
     * @return
     */
    fun setDebugMode(debugMode: Boolean): BaseManagerConfig {
        this.isDebugMode = debugMode
        return this
    }

    /**
     * Set logging enabled
     *
     * @param loggingEnabled
     * @return
     */
    fun setLoggingEnabled(loggingEnabled: Boolean): BaseManagerConfig {
        this.isLoggingEnabled = loggingEnabled
        return this
    }

    /**
     * Set log level
     *
     * @param logLevel
     * @return
     */
    fun setLogLevel(logLevel: LogLevel): BaseManagerConfig {
        this.logLevel = logLevel
        return this
    }


}