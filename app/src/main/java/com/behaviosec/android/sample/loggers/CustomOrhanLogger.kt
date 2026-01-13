package com.behaviosec.android.sample.loggers

import com.github.adhamkhwaldeh.commonsdk.logging.Logger
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions
import com.orhanobut.logger.Logger as OrhanLogger

class CustomOrhanLogger : Logger {
    override fun d(
        tag: String,
        message: String,
        config: BaseSDKOptions
    ) {
        OrhanLogger.t(tag).d(message)
    }

    override fun e(
        tag: String,
        message: String,
        config: BaseSDKOptions,
        throwable: Throwable?
    ) {
        OrhanLogger.t(tag).e(throwable, message)
    }

    override fun w(
        tag: String,
        message: String,
        config: BaseSDKOptions
    ) {

    }

    override fun i(
        tag: String,
        message: String,
        config: BaseSDKOptions
    ) {

    }
}