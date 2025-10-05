package com.behaviosec.android.sample.loggers

import com.github.adhamkhwaldeh.userBehaviorSDK.config.BaseBehaviorConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.config.UserBehaviorSDKConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.logging.ILogger
import com.orhanobut.logger.Logger as OrhanLogger

class CustomOrhanLogger : ILogger {
    override fun d(
        tag: String,
        message: String,
        config: BaseBehaviorConfig
    ) {
        OrhanLogger.t(tag).d(message)
    }

    override fun e(
        tag: String,
        message: String,
        config: BaseBehaviorConfig,
        throwable: Throwable?
    ) {
        OrhanLogger.t(tag).e(throwable, message)
    }

    override fun w(
        tag: String,
        message: String,
        config: BaseBehaviorConfig
    ) {

    }

    override fun i(
        tag: String,
        message: String,
        config: BaseBehaviorConfig
    ) {

    }
}