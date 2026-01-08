package com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks

import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.ICallbackListener
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel

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
