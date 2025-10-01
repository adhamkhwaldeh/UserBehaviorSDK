package com.behaviosec.android.userBehaviorSDK.listeners

import com.behaviosec.android.userBehaviorSDK.models.ManagerErrorModel

/**
 * Activity touch error listener
 *
 * @constructor Create empty Activity touch error listener
 */
interface ActivityTouchErrorListener {
    /**
     * On activity touch error
     *
     * @param error
     */
    fun onActivityTouchError(error: ManagerErrorModel)
}
