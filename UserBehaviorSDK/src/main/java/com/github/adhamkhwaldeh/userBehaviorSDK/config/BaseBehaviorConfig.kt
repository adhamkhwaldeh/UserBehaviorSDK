package com.github.adhamkhwaldeh.userBehaviorSDK.config

import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.configs.IManagerConfigBuilder
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.configs.IManagerConfigInterface
import com.github.adhamkhwaldeh.userBehaviorSDK.logging.LogLevel

abstract class BaseBehaviorConfig(
    override var isEnabled: Boolean = true,
    override var isDebugMode: Boolean = false,
    override var isLoggingEnabled: Boolean = true,
    override var overridable: Boolean = true,
    override var logLevel: LogLevel = LogLevel.DEBUG,
) : IManagerConfigInterface {


    /**
     * Set enabled
     *
     * @param enabled
     * @return
     */
    fun setEnabled(enabled: Boolean): BaseBehaviorConfig {
        this.isEnabled = enabled
        return this
    }

    /**
     * Set debug mode
     *
     * @param debugMode
     * @return
     */
    fun setDebugMode(debugMode: Boolean): BaseBehaviorConfig {
        this.isDebugMode = debugMode
        return this
    }

    /**
     * Set logging enabled
     *
     * @param loggingEnabled
     * @return
     */
    fun setLoggingEnabled(loggingEnabled: Boolean): BaseBehaviorConfig {
        this.isLoggingEnabled = loggingEnabled
        return this
    }

    /**
     * Set log level
     *
     * @param logLevel
     * @return
     */
    fun setLogLevel(logLevel: LogLevel): BaseBehaviorConfig {
        this.logLevel = logLevel
        return this
    }

    /**
     * An abstract base builder that implements the common configuration logic.
     * Concrete configuration classes should extend this builder.
     *
     * @param T The concrete type of the Builder (e.g., AccelerometerConfig.Builder)
     * @param C The type of the configuration object to be built (e.g., AccelerometerConfig)
     */
    @Suppress("UNCHECKED_CAST")
    abstract class BaseBuilder<T : BaseBuilder<T, C>, C : IManagerConfigInterface> :
        IManagerConfigBuilder<T, C> {

        // Default values
        protected var isEnabled: Boolean = true
        protected var isDebugMode: Boolean = false
        protected var isLoggingEnabled: Boolean = true
        protected var logLevel: LogLevel = LogLevel.DEBUG
        protected var overridable: Boolean = true

        fun fromConfig(sdkConfig: IManagerConfigInterface): T {
            this.isEnabled = sdkConfig.isEnabled
            this.isDebugMode = sdkConfig.isDebugMode
            this.isLoggingEnabled = sdkConfig.isLoggingEnabled
            this.logLevel = sdkConfig.logLevel
            return this as T
        }

        override fun setEnabled(enabled: Boolean): T {
            this.isEnabled = enabled
            return this as T
        }

        override fun setDebugMode(debugMode: Boolean): T {
            this.isDebugMode = debugMode
            return this as T
        }

        override fun setLoggingEnabled(loggingEnabled: Boolean): T {
            this.isLoggingEnabled = loggingEnabled
            return this as T
        }

        override fun setLogLevel(logLevel: LogLevel): T {
            this.logLevel = logLevel
            return this as T
        }

        override fun setOverridable(overridable: Boolean): T {
            this.overridable = overridable
            return this as T
        }

        /**
         * The build function must be implemented by concrete builders.
         */
        abstract override fun build(): C
    }

}