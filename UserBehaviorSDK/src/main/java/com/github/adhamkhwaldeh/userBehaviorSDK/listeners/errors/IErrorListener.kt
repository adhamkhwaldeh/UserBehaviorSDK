package com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors

import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerErrorModel

interface IErrorListener {
    /**
     * On error
     *
     * @param error
     */
    fun onError(error: ManagerErrorModel)
}