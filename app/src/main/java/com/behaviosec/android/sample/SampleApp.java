package com.behaviosec.android.sample;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.behaviosec.android.userBehaviorSDK.UserBehaviorCoreSDK;
import com.behaviosec.android.userBehaviorSDK.config.TouchTrackerConfig;
import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.AccelerometerListener;
import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.TouchListener;
import com.behaviosec.android.userBehaviorSDK.managers.AccelerometerManager;
import com.behaviosec.android.userBehaviorSDK.managers.touchs.AppTouchManager;
import com.behaviosec.android.userBehaviorSDK.models.AccelerometerEventModel;
import com.behaviosec.android.userBehaviorSDK.models.AccuracyChangedModel;
import com.behaviosec.android.userBehaviorSDK.models.MotionEventModel;
import com.behaviosec.android.userBehaviorSDK.repositories.HelpersRepository;


public class SampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new UserBehaviorCoreSDK().initialize();

        //#region AccelerometerManager
        AccelerometerManager accelerometerManager = new AccelerometerManager(this, new HelpersRepository(), new TouchTrackerConfig());

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

        AppTouchManager appTouchManager = new AppTouchManager(this,new TouchTrackerConfig());

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