package com.behaviosec.android.userBehaviorSDK.listeners.callbacks

import com.behaviosec.android.userBehaviorSDK.models.MotionEventModel

/**
 * Activity touch listener
 *
 * @constructor Create empty Activity touch listener
 */
interface TouchListener : ICallbackListener {
    /**
     * Dispatch touch event
     *
     * @param event
     * @return
     */
    fun dispatchTouchEvent(event: MotionEventModel): Boolean {
        return true //return true to continue executing the base or original event
    }
}
