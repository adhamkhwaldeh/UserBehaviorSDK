package com.behaviosec.android.userBehaviorSDK.managers.base

import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.ICallbackListener
import com.behaviosec.android.userBehaviorSDK.listeners.errors.IErrorListener

/**
 * Base manager
 *
 * @constructor Create empty Base manager
 */
interface IBaseManager<TCall, TError> : IBaseConfigurableManager,
    IBaseCallbackManager<TCall>, IBaseErrorManager<TError>
        where TCall : ICallbackListener, TError : IErrorListener {

    fun start()
    fun stop()
    fun pause()
    fun resume()
}
