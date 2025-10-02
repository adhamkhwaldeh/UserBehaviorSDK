package com.behaviosec.android.userBehaviorSDK.managers.base

import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.ICallbackListener
import java.util.concurrent.CopyOnWriteArrayList

interface IBaseCallbackManager<T> where T : ICallbackListener {
    val listeners: CopyOnWriteArrayList<T>

    /**
     * Add listener
     *
     * @param listener
     */
    fun addListener(listener: T) {
        listeners.addIfAbsent(listener)
    }

    /**
     * Remove listener
     *
     * @param listener
     */
    fun removeListener(listener: T) {
        listeners.remove(listener)
    }

    fun clearListeners() {
        listeners.clear()
    }

}