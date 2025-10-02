package com.behaviosec.android.userBehaviorSDK.managers

import com.behaviosec.android.userBehaviorSDK.config.TouchTrackerConfig
import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.IDataListener
import com.behaviosec.android.userBehaviorSDK.listeners.errors.IErrorListener

/**
 * Base manager
 *
 * @property config
 * @constructor Create empty Base manager
 */
abstract class BaseManager<TCall, TError>(
    config: TouchTrackerConfig = TouchTrackerConfig()
) : BaseConfigurableManager(config), IBaseManager, IBaseCallbackManager<TCall>,
    IBaseErrorManager<TError> where TCall : IDataListener, TError : IErrorListener {

}
