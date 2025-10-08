package com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors

/**
 * Accelerometer error listener
 *
 * @constructor Create empty Accelerometer error listener
 */
@Deprecated(
    "AccelerometerErrorListener is deprecated and will be removed in a future release. Please use SensorConfig instead.",
    ReplaceWith("SensorErrorListener")
)
interface AccelerometerErrorListener : IErrorListener