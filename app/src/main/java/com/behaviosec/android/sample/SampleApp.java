package com.behaviosec.android.sample;

import android.app.Application;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.behaviosec.android.accelerometerTouchTrackerSdk.AccelerometerTouchTrackerCore;
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.AccelerometerListener;
import com.behaviosec.android.accelerometerTouchTrackerSdk.managers.AccelerometerManager;
import com.behaviosec.android.accelerometerTouchTrackerSdk.managers.AppTouchManager;

import java.util.Date;

public class SampleApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        new AccelerometerTouchTrackerCore().initialize();

        //#region AccelerometerManager
        AccelerometerManager accelerometerManager = new AccelerometerManager(this);

        accelerometerManager.setDebugMode(true).setLoggingEnabled(true);
        accelerometerManager.start();

        accelerometerManager.addListener(new AccelerometerListener() {
            @Override
            public void onAccuracyChanged(@Nullable Sensor sensor, int accuracy, @Nullable Date date) {
                Log.d("SampleApp Accuracy changed:", "Accuracy changed: " + accuracy + " at " + date);
            }

            @Override
            public void onSensorChanged(@NonNull SensorEvent event, @Nullable Date date) {
                Log.d("SampleApp Sensor changed:", "Sensor changed: " + event.values[0] + ", " + event.values[1] + ", " + event.values[2] + " at " + date);
            }
        });

        //#endregion

        //#region AppTouchManager

        AppTouchManager appTouchManager = new AppTouchManager(this);

        appTouchManager.setGlobalTouchListener((event, date) -> {
            Log.d("SampleApp", "Global touch event: " + event.toString() + " at " + date.toString());
            return true;
        });

        //#endregion

    }

}