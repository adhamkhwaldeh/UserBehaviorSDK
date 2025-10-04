package com.behaviosec.android.sample;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK;
import com.github.adhamkhwaldeh.userBehaviorSDK.config.AccelerometerConfig;
import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig;
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener;
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener;
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener;
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.IAccelerometerManager;
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.ITouchManager;
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel;
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel;
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerErrorModel;
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel;

import org.jetbrains.annotations.NotNull;


public class SampleApp extends Application {

    static UserBehaviorCoreSDK userBehaviorCoreSDK;


    @Override
    public void onCreate() {
        super.onCreate();
        userBehaviorCoreSDK =
                UserBehaviorCoreSDK.Companion.getInstance(this);

        //#region AccelerometerManager
        IAccelerometerManager accelerometerManager = userBehaviorCoreSDK.getAccelerometerManager(new AccelerometerConfig());

        accelerometerManager.setDebugMode(true).setLoggingEnabled(true);
        accelerometerManager.start();

        accelerometerManager.addListener(new AccelerometerListener() {
            @Override
            public void onAccuracyChanged(@NonNull AccuracyChangedModel model) {
                Log.d("SampleApp Accuracy changed:", "Accuracy changed: " + model.getAccuracy() + " at " + model.getDate());

            }

            @Override
            public void onSensorChanged(@NonNull AccelerometerEventModel model) {
                Log.d("SampleApp Sensor changed:", "Sensor changed: " + model.getEvent().values[0] + ", " + model.getEvent().values[1] + ", " + model.getEvent().values[2] + " at " + model.getDate());

            }

        });
        //#endregion

        //#region AppTouchManager

        ITouchManager appTouchManager = userBehaviorCoreSDK.fetchOrCreateApplicationTouchManager(this, new TouchConfig());

        appTouchManager.addListener(new TouchListener() {
            @Override
            public boolean dispatchTouchEvent(@NonNull MotionEventModel model) {
                Log.d("SampleApp", "Global touch event: " + model.getEvent() + " at " + model.getDate());
                return true;
            }
        });

        appTouchManager.addErrorListener(new TouchErrorListener() {
            @Override
            public void onError(@NotNull ManagerErrorModel error) {

            }
        });
        //#endregion

    }

}