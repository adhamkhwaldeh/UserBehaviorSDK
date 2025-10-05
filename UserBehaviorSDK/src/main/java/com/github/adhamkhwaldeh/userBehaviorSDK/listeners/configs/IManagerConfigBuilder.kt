package com.github.adhamkhwaldeh.userBehaviorSDK.listeners.configs

import com.github.adhamkhwaldeh.userBehaviorSDK.logging.LogLevel

/**
* Generic Builder interface for manager configurations.
* @param T The concrete type of the Builder (e.g., AccelerometerConfig.Builder)
* @param C The type of the configuration object to be built (e.g., AccelerometerConfig)
*/
interface IManagerConfigBuilder<T, C : IManagerConfigInterface> {
    fun setEnabled(enabled: Boolean): T
    fun setDebugMode(debugMode: Boolean): T
    fun setLoggingEnabled(loggingEnabled: Boolean): T
    fun setLogLevel(logLevel: LogLevel): T
    fun setOverridable(overridable: Boolean): T
    fun build(): C
}