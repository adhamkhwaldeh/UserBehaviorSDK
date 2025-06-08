package com.behaviosec.android.sample;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.behaviosec.android.accelerometerTouchTrackerSdk.AccelerometerTouchTrackerCore;
import com.behaviosec.android.accelerometerTouchTrackerSdk.config.TouchTrackerConfig;
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.AccelerometerListener;
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.ActivityTouchListener;
import com.behaviosec.android.accelerometerTouchTrackerSdk.managers.AccelerometerManager;
import com.behaviosec.android.accelerometerTouchTrackerSdk.managers.AppTouchManager;
import com.behaviosec.android.accelerometerTouchTrackerSdk.model.AccelerometerEventModel;
import com.behaviosec.android.accelerometerTouchTrackerSdk.model.AccuracyChangedModel;
import com.behaviosec.android.accelerometerTouchTrackerSdk.model.MotionEventModel;


public class SampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new AccelerometerTouchTrackerCore().initialize();

        //#region AccelerometerManager
        AccelerometerManager accelerometerManager = new AccelerometerManager(this, new TouchTrackerConfig());

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

        AppTouchManager appTouchManager = new AppTouchManager(this);

        appTouchManager.setGlobalTouchListener(new ActivityTouchListener() {
            @Override
            public boolean dispatchTouchEvent(@NonNull MotionEventModel model) {
                Log.d("SampleApp", "Global touch event: " + model.getEvent() + " at " + model.getDate());
                return true;
            }
        });

        //#endregion

    }

}