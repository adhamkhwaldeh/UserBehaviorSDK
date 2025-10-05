package com.behaviosec.android.sample.loggers

import com.github.adhamkhwaldeh.userBehaviorSDK.config.BaseManagerConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.logging.ILogger
import com.orhanobut.logger.Logger as OrhanLogger

class CustomOrhanLogger : ILogger {
    override fun d(
        tag: String,
        message: String,
        config: BaseManagerConfig
    ) {
        OrhanLogger.t(tag).d(message)
    }

    override fun e(
        tag: String,
        message: String,
        config: BaseManagerConfig,
        throwable: Throwable?
    ) {
        OrhanLogger.t(tag).e(throwable, message)
    }

    override fun w(
        tag: String,
        message: String,
        config: BaseManagerConfig
    ) {

    }

    override fun i(
        tag: String,
        message: String,
        config: BaseManagerConfig
    ) {

    }
}