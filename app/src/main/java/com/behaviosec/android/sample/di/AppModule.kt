package com.behaviosec.android.sample.di

import org.koin.dsl.module
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import android.app.Application
import com.behaviosec.android.sample.loggers.CustomLogger
import com.behaviosec.android.sample.loggers.CustomOrhanLogger
import com.behaviosec.android.sample.loggers.TimberLogger

val appModule = module {
    single { (app: Application) ->
        UserBehaviorCoreSDK.Builder(app.applicationContext)
            .addLogger(CustomLogger())
            .addLogger(TimberLogger())
            .addLogger(CustomOrhanLogger())
            //TODO set default configs if needed
            .build();
    }
}
