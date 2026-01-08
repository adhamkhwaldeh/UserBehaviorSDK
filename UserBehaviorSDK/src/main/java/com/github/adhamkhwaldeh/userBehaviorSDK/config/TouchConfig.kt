package com.github.adhamkhwaldeh.userBehaviorSDK.config

import com.github.adhamkhwaldeh.commonsdk.config.BaseBehaviorConfig
import com.github.adhamkhwaldeh.commonsdk.logging.LogLevel


class TouchConfig private constructor(
    isEnabled: Boolean,
    isDebugMode: Boolean,
    isLoggingEnabled: Boolean,
    overridable: Boolean,
    logLevel: LogLevel,
) : BaseBehaviorConfig(isEnabled, isDebugMode, isLoggingEnabled, overridable, logLevel) {

    /**
     * The Builder for creating AccelerometerConfig instances.
     */
    class Builder : BaseBuilder<Builder, TouchConfig>() {
        /**
         * Creates the final TouchConfig object.
         */
        override fun build(): TouchConfig {
            return TouchConfig(
                isEnabled = isEnabled,
                isDebugMode = isDebugMode,
                isLoggingEnabled = isLoggingEnabled,
                logLevel = logLevel,
                overridable = overridable,
            )
        }
    }
}