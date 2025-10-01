package com.behaviosec.android.userBehaviorSDK.listeners

import com.behaviosec.android.userBehaviorSDK.models.AccelerometerEventModel
import com.behaviosec.android.userBehaviorSDK.models.AccuracyChangedModel

/**
 * Accelerometer listener
 *
 * @constructor Create empty Accelerometer listener
 */
interface AccelerometerListener {
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
