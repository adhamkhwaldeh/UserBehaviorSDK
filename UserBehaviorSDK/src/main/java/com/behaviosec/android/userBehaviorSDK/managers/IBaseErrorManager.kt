package com.behaviosec.android.userBehaviorSDK.managers

import com.behaviosec.android.userBehaviorSDK.listeners.errors.IErrorListener
import com.behaviosec.android.userBehaviorSDK.models.ManagerErrorModel
import java.util.concurrent.CopyOnWriteArrayList

interface IBaseErrorManager<T> where T : IErrorListener {

    val errorListeners: CopyOnWriteArrayList<T>
        get() = CopyOnWriteArrayList<T>()

    /**
     * Add error listener
     *
     * @param listener
     */
    fun addErrorListener(listener: T) {
        errorListeners.addIfAbsent(listener)
    }

    /**
     * Remove error listener
     *
     * @param listener
     */
    fun removeErrorListener(listener: T) {
        errorListeners.remove(listener)
    }

    /**
     * Remove all error listeners.
     */
    fun clearErrorListeners() {
        errorListeners.clear()
    }

    fun notifyErrorListeners(error: ManagerErrorModel) {
        for (listener in errorListeners) {
            listener.onError(error)
        }
    }

    fun notifyErrorListeners(e: Exception) {
        notifyErrorListeners(ManagerErrorModel.fromException(e))
    }

}