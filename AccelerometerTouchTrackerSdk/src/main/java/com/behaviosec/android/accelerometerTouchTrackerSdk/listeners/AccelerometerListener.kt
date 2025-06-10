package com.behaviosec.android.accelerometerTouchTrackerSdk.listeners

import com.behaviosec.android.accelerometerTouchTrackerSdk.models.AccelerometerEventModel
import com.behaviosec.android.accelerometerTouchTrackerSdk.models.AccuracyChangedModel

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
