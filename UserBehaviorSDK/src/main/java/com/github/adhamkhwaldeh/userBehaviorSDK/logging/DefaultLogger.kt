package com.github.adhamkhwaldeh.userBehaviorSDK.logging

import android.util.Log
import com.github.adhamkhwaldeh.userBehaviorSDK.config.BaseBehaviorConfig

/**
 * The default implementation of `ILogger` which writes to Android's standard `Log` class.
 */
internal class DefaultLogger : ILogger {
    override fun d(tag: String, message: String, config: BaseBehaviorConfig) {
        if (config.isLoggingEnabled && config.isDebugMode) {
            Log.d(tag, message)
        }
    }

    override fun e(tag: String, message: String, config: BaseBehaviorConfig, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }

    override fun w(tag: String, message: String, config: BaseBehaviorConfig) {
        Log.w(tag, message)
    }

    override fun i(tag: String, message: String, config: BaseBehaviorConfig) {
        Log.i(tag, message)
    }
}