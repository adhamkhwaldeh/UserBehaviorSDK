package com.behaviosec.android.userBehaviorSDK.managers

import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.IDataListener
import com.behaviosec.android.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
import java.util.concurrent.CopyOnWriteArrayList

interface IBaseCallbackManager<T> where T : IDataListener {
    val listeners: CopyOnWriteArrayList<T>
        get() = CopyOnWriteArrayList<T>()

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