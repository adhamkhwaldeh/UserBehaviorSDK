package com.behaviosec.android.sample.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.util.Log;

import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.AccelerometerListener;
import com.behaviosec.android.accelerometerTouchTrackerSdk.managers.AccelerometerManager;
import com.behaviosec.android.accelerometerTouchTrackerSdk.managers.ActivityTouchManager;
import com.behaviosec.android.sample.databinding.ActivityMainBinding;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //#region AccelerometerManager
        AccelerometerManager accelerometerManager = new AccelerometerManager(this);

        accelerometerManager.setDebugMode(true).setLoggingEnabled(true);

        accelerometerManager.addListener(new AccelerometerListener() {
            @Override
            public void onAccuracyChanged(@Nullable Sensor sensor, int accuracy, @Nullable Date date) {
                binding.accelerometerAccuracy.setText("Accuracy changed: " + accuracy + " at " + date);
            }

            @Override
            public void onSensorChanged(@NonNull SensorEvent event, @Nullable Date date) {
                binding.accelerometerSensor.setText("Sensor changed: " + event.values[0] + ", " + event.values[1] + ", " + event.values[2] + " at " + date);
            }
        });

        binding.startAccelerometerButton.setOnClickListener(v -> {
            accelerometerManager.start();
        });

        binding.stopAccelerometerButton.setOnClickListener(v -> {
            accelerometerManager.stop();
        });
        //#endregion


        //#region ActivityTouchManager
        ActivityTouchManager activityTouchManager = new ActivityTouchManager(this);

        activityTouchManager.setListener((event, date) -> {
            Log.d("ActivityTouchManager", "Touch event: " + event + " at " + date);
            binding.touchDetails.setText("Touch event: " + event + " at " + date);
            return true; // Return false to indicate the event was not handled
        });

        binding.startTouchButton.setOnClickListener(v -> {
            activityTouchManager.setEnabled(true);
        });

        binding.stopTouchButton.setOnClickListener(v -> {
            activityTouchManager.setEnabled(false);
        });

        //#endregion


        binding.logoutButton.setOnClickListener(v -> {
            finishActivity();
        });
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    private void finishActivity() {
        Intent intent = new Intent(this, StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}