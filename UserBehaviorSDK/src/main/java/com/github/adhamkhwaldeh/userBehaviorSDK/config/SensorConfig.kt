package com.github.adhamkhwaldeh.userBehaviorSDK.config

import com.github.adhamkhwaldeh.userBehaviorSDK.logging.LogLevel

class SensorConfig private constructor(
    // Properties from BaseManagerConfig
    isEnabled: Boolean = true,
    isDebugMode: Boolean = true,
    isLoggingEnabled: Boolean = true,
    overridable: Boolean = true,
    logLevel: LogLevel = LogLevel.DEBUG,
    // Accelerometer-specific properties
    val sensorDelay: Int,
) : BaseBehaviorConfig(isEnabled, isDebugMode, isLoggingEnabled, overridable, logLevel) {

    /**
     * The Builder for creating AccelerometerConfig instances.
     */
    class Builder : BaseBuilder<Builder, SensorConfig>() {
        private var sensorDelay: Int = 1000 // Default value for this specific config

        /**
         * Sets the sensor delay for the accelerometer.
         */
        fun setSensorDelay(delay: Int) = apply {
            this.sensorDelay = delay
        }

        /**
         * Creates the final SensorConfig object.
         */
        override fun build(): SensorConfig {
            return SensorConfig(
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