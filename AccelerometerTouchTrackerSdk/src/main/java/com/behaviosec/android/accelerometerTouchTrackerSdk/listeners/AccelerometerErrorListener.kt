package com.behaviosec.android.accelerometerTouchTrackerSdk.listeners

import com.behaviosec.android.accelerometerTouchTrackerSdk.models.ManagerErrorModel

/**
 * Accelerometer error listener
 *
 * @constructor Create empty Accelerometer error listener
 */
interface AccelerometerErrorListener {
    /**
     * On accelerometer error
     *
     * @param error
     */
    fun onAccelerometerError(error: ManagerErrorModel)
}
