package com.behaviosec.android.sample.di

import org.koin.dsl.module
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import android.app.Application
import com.behaviosec.android.sample.loggers.CustomLogger
import com.behaviosec.android.sample.loggers.CustomOrhanLogger
import com.behaviosec.android.sample.loggers.TimberLogger

import com.github.adhamkhwaldeh.commonsdk.logging.LogLevel
import com.github.adhamkhwaldeh.userBehaviorSDK.options.UserBehaviorSDKOptions

val appModule = module {

    single {
        UserBehaviorSDKOptions.Builder().setDebugMode(true).setLogLevel(
            LogLevel.DEBUG
        ).setLoggingEnabled(true).build()
    }

    single { (app: Application) ->
        UserBehaviorCoreSDK.Builder(app.applicationContext)
            .setupOptions(get())
            .addLogger(CustomLogger())
            .addLogger(TimberLogger())
            .addLogger(CustomOrhanLogger())
            .build()
    }

}
