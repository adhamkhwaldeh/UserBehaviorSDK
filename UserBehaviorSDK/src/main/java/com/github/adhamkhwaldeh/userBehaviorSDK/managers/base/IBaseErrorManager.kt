package com.github.adhamkhwaldeh.userBehaviorSDK.managers.base

import com.github.adhamkhwaldeh.userBehaviorSDK.exceptions.BaseUserBehaviorException
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.IErrorListener

interface IBaseErrorManager<T> where T : IErrorListener {


    /**
     * Add error listener
     *
     * @param listener
     */
    fun addErrorListener(listener: T)

    /**
     * Remove error listener
     *
     * @param listener
     */
    fun removeErrorListener(listener: T)

    /**
     * Remove all error listeners.
     */
    fun clearErrorListeners()

    fun notifyErrorListeners(error: BaseUserBehaviorException)

}