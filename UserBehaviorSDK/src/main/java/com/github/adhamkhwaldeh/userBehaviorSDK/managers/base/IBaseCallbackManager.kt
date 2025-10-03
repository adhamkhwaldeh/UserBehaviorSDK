package com.github.adhamkhwaldeh.userBehaviorSDK.managers.base

import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.ICallbackListener

interface IBaseCallbackManager<T> where T : ICallbackListener {

    /**
     * Add listener
     *
     * @param listener
     */
    fun addListener(listener: T)

    /**
     * Remove listener
     *
     * @param listener
     */
    fun removeListener(listener: T)
    fun clearListeners()

}