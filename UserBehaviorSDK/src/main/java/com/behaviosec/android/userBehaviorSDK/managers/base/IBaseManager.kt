package com.behaviosec.android.userBehaviorSDK.managers.base

import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.IDataListener
import com.behaviosec.android.userBehaviorSDK.listeners.errors.IErrorListener
import java.util.concurrent.CopyOnWriteArrayList

/**
 * Base manager
 *
 * @constructor Create empty Base manager
 */
interface IBaseManager<TCall, TError> : IBaseConfigurableManager,
    IBaseCallbackManager<TCall>, IBaseErrorManager<TError>
        where TCall : IDataListener, TError : IErrorListener {

    fun start()
    fun stop()
    fun pause()
    fun resume()
}
