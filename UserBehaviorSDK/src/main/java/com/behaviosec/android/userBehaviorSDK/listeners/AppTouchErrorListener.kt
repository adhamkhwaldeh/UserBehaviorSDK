package com.behaviosec.android.userBehaviorSDK.listeners

import com.behaviosec.android.userBehaviorSDK.models.ManagerErrorModel

/**
 * App touch error listener
 *
 * @constructor Create empty App touch error listener
 */
interface AppTouchErrorListener {
    /**
     * On app touch error
     *
     * @param error
     */
    fun onAppTouchError(error: ManagerErrorModel)
}
