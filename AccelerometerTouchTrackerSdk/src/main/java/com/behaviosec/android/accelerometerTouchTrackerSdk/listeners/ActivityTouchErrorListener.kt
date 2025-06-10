package com.behaviosec.android.accelerometerTouchTrackerSdk.listeners

import com.behaviosec.android.accelerometerTouchTrackerSdk.models.ManagerErrorModel

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
