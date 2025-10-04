package com.github.adhamkhwaldeh.userBehaviorSDK.managers

import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.base.IBaseManager

interface ITouchManager : IBaseManager<TouchListener, TouchErrorListener, TouchConfig>