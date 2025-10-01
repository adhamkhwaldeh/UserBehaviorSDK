package com.behaviosec.android.userBehaviorSDK.listeners

import com.behaviosec.android.userBehaviorSDK.models.ManagerErrorModel

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
