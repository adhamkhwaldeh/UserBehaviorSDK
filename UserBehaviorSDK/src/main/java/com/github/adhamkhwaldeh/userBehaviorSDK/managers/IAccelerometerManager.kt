package com.github.adhamkhwaldeh.userBehaviorSDK.managers

import com.github.adhamkhwaldeh.userBehaviorSDK.config.AccelerometerConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.AccelerometerErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.base.IBaseManager

/**
 * A specialized interface for the AccelerometerManager.
 * This simplifies type declarations for Java consumers.
 */
interface IAccelerometerManager :
    IBaseManager<AccelerometerListener, AccelerometerErrorListener, AccelerometerConfig>