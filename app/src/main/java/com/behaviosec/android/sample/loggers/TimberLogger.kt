package com.behaviosec.android.sample.loggers

import com.github.adhamkhwaldeh.userBehaviorSDK.config.BaseBehaviorConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.config.UserBehaviorSDKConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.logging.ILogger
import timber.log.Timber

class TimberLogger : ILogger {

    override fun d(
        tag: String,
        message: String,
        config: BaseBehaviorConfig
    ) {
        Timber.tag(tag).d(message)
    }

    override fun e(
        tag: String,
        message: String,
        config: BaseBehaviorConfig,
        throwable: Throwable?
    ) {
        Timber.tag(tag).e(throwable, message)
    }

    override fun w(
        tag: String,
        message: String,
        config: BaseBehaviorConfig
    ) {
        Timber.tag(tag).w(message)
    }

    override fun i(
        tag: String,
        message: String,
        config: BaseBehaviorConfig
    ) {
        Timber.tag(tag).i(message)
    }
}