package com.github.adhamkhwaldeh.userBehaviorSDK.listeners

import com.github.adhamkhwaldeh.commonsdk.listeners.callbacks.CallbackListener

interface UserBehaviorSDKStatusListener : CallbackListener {
    fun onInitialized()
}
