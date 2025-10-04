package com.github.adhamkhwaldeh.userBehaviorSDK.managers.base

import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.ICallbackListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.configs.IManagerConfigInterface
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.IErrorListener

/**
 * Base manager
 *
 * @constructor Create empty Base manager
 */
interface IBaseManager<TCall : ICallbackListener, TError : IErrorListener,
        TConfig : IManagerConfigInterface> :
    IBaseCallbackManager<TCall>, IBaseErrorManager<TError>,
    IBaseConfigurableManager<TConfig> {

    fun isStarted(): Boolean
    fun start()
    fun stop()
    fun pause()
    fun resume()
}
