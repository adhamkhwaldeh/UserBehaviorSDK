package com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs

import com.github.adhamkhwaldeh.commonsdk.managers.IBaseManager
import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener

interface ITouchManager : IBaseManager<TouchListener, TouchErrorListener, TouchConfig>