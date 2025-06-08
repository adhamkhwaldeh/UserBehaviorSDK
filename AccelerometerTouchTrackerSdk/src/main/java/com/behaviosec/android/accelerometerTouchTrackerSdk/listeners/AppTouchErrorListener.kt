package com.behaviosec.android.accelerometerTouchTrackerSdk.listeners

import com.behaviosec.android.accelerometerTouchTrackerSdk.model.ManagerErrorModel

interface AppTouchErrorListener {
    fun onAppTouchError(error: ManagerErrorModel)
}
