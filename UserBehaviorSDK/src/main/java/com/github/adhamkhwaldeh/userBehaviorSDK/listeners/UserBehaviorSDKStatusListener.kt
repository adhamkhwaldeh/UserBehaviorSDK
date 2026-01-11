package com.github.adhamkhwaldeh.userBehaviorSDK.listeners

import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.ICallbackListener

interface UserBehaviorSDKStatusListener : ICallbackListener {
    fun onInitialized()
}