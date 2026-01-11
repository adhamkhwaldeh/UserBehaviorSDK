package com.github.adhamkhwaldeh.userBehaviorSDK.config

import com.github.adhamkhwaldeh.commonsdk.logging.LogLevel
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions

@Deprecated(
    "AccelerometerConfig is deprecated and will be removed in a future release. Please use SensorConfig instead.",
    ReplaceWith("SensorConfig")
)
class AccelerometerConfig private constructor(
    // Properties from BaseManagerConfig
    isEnabled: Boolean,
    isDebugMode: Boolean,
    isLoggingEnabled: Boolean,
    overridable: Boolean,
    logLevel: LogLevel,
    // Accelerometer-specific properties
    val sensorDelay: Int,
) : BaseSDKOptions(isEnabled, isDebugMode, isLoggingEnabled, overridable, logLevel) {

    /**
     * The Builder for creating AccelerometerConfig instances.
     */
    class Builder : BaseBuilder<Builder, AccelerometerConfig>() {
        private var sensorDelay: Int = 1000 // Default value for this specific config

        /**
         * Sets the sensor delay for the accelerometer.
         */
        fun setSensorDelay(delay: Int) = apply {
            this.sensorDelay = delay
        }

        /**
         * Creates the final AccelerometerConfig object.
         */
        override fun build(): AccelerometerConfig {
            return AccelerometerConfig(
                isEnabled = isEnabled,
                isDebugMode = isDebugMode,
                isLoggingEnabled = isLoggingEnabled,
                logLevel = logLevel,
                overridable = overridable,
                sensorDelay = sensorDelay
            )
        }
    }
}