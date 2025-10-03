package com.behaviosec.android.sample;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.github.adhamkhwaldeh.userBehaviorSDK.config.AccelerometerConfig;
import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig;
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener;
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener;
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.AccelerometerManager;
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs.AppTouchManager;
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel;
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel;
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel;
import com.github.adhamkhwaldeh.userBehaviorSDK.repositories.HelpersRepository;


public class SampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

//        new UserBehaviorCoreSDK(this).initialize();

        //#region AccelerometerManager
        AccelerometerManager accelerometerManager = new AccelerometerManager(this, new HelpersRepository(), new AccelerometerConfig());

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

        AppTouchManager appTouchManager = new AppTouchManager(this, new TouchConfig());

        appTouchManager.addListener(new TouchListener() {
            @Override
            public boolean dispatchTouchEvent(@NonNull MotionEventModel model) {
                Log.d("SampleApp", "Global touch event: " + model.getEvent() + " at " + model.getDate());
                return true;
            }
        });

        //#endregion

    }

}