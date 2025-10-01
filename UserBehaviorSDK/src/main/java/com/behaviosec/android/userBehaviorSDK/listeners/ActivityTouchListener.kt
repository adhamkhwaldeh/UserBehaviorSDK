package com.behaviosec.android.userBehaviorSDK.listeners

import com.behaviosec.android.userBehaviorSDK.models.MotionEventModel

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
