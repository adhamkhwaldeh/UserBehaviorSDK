package com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks

import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel

/**
 * Accelerometer listener
 *
 * @constructor Create empty Accelerometer listener
 */

@Deprecated(
    "AccelerometerListener is deprecated and will be removed in a future release. Please use SensorConfig instead.",
    ReplaceWith("SensorListener")
)
interface AccelerometerListener : ICallbackListener {
    /**
     * On sensor changed
     *
     * @param model
     */
    fun onSensorChanged(model: AccelerometerEventModel) {}

    /**
     * On accuracy changed
     *
     * @param model
     */
    fun onAccuracyChanged(model: AccuracyChangedModel) {}
}
