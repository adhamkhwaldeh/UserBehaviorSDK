package com.behaviosec.android.sample.loggers

import com.github.adhamkhwaldeh.commonsdk.logging.ILogger
import com.github.adhamkhwaldeh.commonsdk.options.BaseSDKOptions
import timber.log.Timber

class TimberLogger : ILogger {

    override fun d(
        tag: String,
        message: String,
        config: BaseSDKOptions
    ) {
        Timber.tag(tag).d(message)
    }

    override fun e(
        tag: String,
        message: String,
        config: BaseSDKOptions,
        throwable: Throwable?
    ) {
        Timber.tag(tag).e(throwable, message)
    }

    override fun w(
        tag: String,
        message: String,
        config: BaseSDKOptions
    ) {
        Timber.tag(tag).w(message)
    }

    override fun i(
        tag: String,
        message: String,
        config: BaseSDKOptions
    ) {
        Timber.tag(tag).i(message)
    }
}