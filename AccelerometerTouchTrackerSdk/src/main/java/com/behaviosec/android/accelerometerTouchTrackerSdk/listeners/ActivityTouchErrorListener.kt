package com.behaviosec.android.accelerometerTouchTrackerSdk.listeners

import com.behaviosec.android.accelerometerTouchTrackerSdk.model.ManagerErrorModel

interface ActivityTouchErrorListener {
    fun onActivityTouchError(error: ManagerErrorModel)
}
