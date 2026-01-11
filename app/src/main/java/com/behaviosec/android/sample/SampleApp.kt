package com.behaviosec.android.sample

import android.app.Application
import android.util.Log
import com.behaviosec.android.sample.di.appModule
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK
import com.github.adhamkhwaldeh.userBehaviorSDK.config.AccelerometerConfig
import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig
import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseSDKException
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.accelerometer.IAccelerometerManager
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs.ITouchManager
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.parameter.parametersOf

class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()

        //#region DI
        startKoin {
            androidContext(this@SampleApp)
            modules(appModule)
        }

        //#endregion

        val userBehaviorCoreSDK: UserBehaviorCoreSDK =
            getKoin().get { parametersOf(this@SampleApp) }

        //#region AccelerometerManager
        val accelerometerManager: IAccelerometerManager =
            userBehaviorCoreSDK.getAccelerometerManager()

        accelerometerManager.setDebugMode(true).setLoggingEnabled(true)
//        accelerometerManager.start()

        accelerometerManager.addListener(object : AccelerometerListener {
            override fun onAccuracyChanged(model: AccuracyChangedModel) {
                Log.d(
                    "SampleApp Accuracy changed:",
                    "Accuracy changed: " + model.accuracy + " at " + model.date
                )
            }

            override fun onSensorChanged(model: AccelerometerEventModel) {
                Log.d(
                    "SampleApp Sensor changed:",
                    "Sensor changed: " + model.event!!.values[0] + ", " + model.event!!.values[1] + ", " + model.event!!.values[2] + " at " + model.date
                )
            }
        })
        //#endregion

        //#region AppTouchManager
        val appTouchManager: ITouchManager =
            userBehaviorCoreSDK.fetchOrCreateApplicationTouchManager(this)

        appTouchManager.addListener(object : TouchListener {
            override fun dispatchTouchEvent(event: MotionEventModel): Boolean {
                Log.d("SampleApp", "Global touch event: " + event.event + " at " + event.date)
                return true
            }
        })

        appTouchManager.addErrorListener(object : TouchErrorListener {
            override fun onError(error: BaseSDKException) {
                Log.d("SampleApp", "Global touch event: " + error.message)
            }
        })
        //#endregion

    }

}