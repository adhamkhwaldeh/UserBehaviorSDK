package com.behaviosec.android.accelerometerTouchTrackerSdk.listeners

import com.behaviosec.android.accelerometerTouchTrackerSdk.models.MotionEventModel

/**
 * Activity touch listener
 *
 * @constructor Create empty Activity touch listener
 */
interface ActivityTouchListener {
    /**
     * Dispatch touch event
     *
     * @param model
     * @return
     */
    fun dispatchTouchEvent(model: MotionEventModel): Boolean{
        return true //return true to continue executing the base or original event
    }
}
