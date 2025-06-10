package com.behaviosec.android.accelerometerTouchTrackerSdk.listeners

import com.behaviosec.android.accelerometerTouchTrackerSdk.models.ManagerErrorModel

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
