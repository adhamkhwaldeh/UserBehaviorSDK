package com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors

import com.github.adhamkhwaldeh.commonsdk.listeners.errors.ErrorListener

/**
 * Accelerometer error listener
 *
 * @constructor Create empty Accelerometer error listener
 */
@Deprecated(
    "AccelerometerErrorListener is deprecated and will be removed in a future release. Please use SensorConfig instead.",
    ReplaceWith("SensorErrorListener")
)
interface AccelerometerErrorListener : ErrorListener