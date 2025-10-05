package com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors

import com.github.adhamkhwaldeh.userBehaviorSDK.exceptions.BaseUserBehaviorException

interface IErrorListener {
    /**
     * On error
     *
     * @param error
     */
    fun onError(error: BaseUserBehaviorException)
}