package com.behaviosec.android.userBehaviorSDK.listeners.errors

import com.behaviosec.android.userBehaviorSDK.models.ManagerErrorModel

interface IErrorListener {
    /**
     * On error
     *
     * @param error
     */
    fun onError(error: ManagerErrorModel)
}