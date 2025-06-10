package com.behaviosec.android.sample.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.behaviosec.android.accelerometerTouchTrackerSdk.config.TouchTrackerConfig;
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.AccelerometerErrorListener;
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.AccelerometerListener;
import com.behaviosec.android.accelerometerTouchTrackerSdk.listeners.ActivityTouchListener;
import com.behaviosec.android.accelerometerTouchTrackerSdk.managers.AccelerometerManager;
import com.behaviosec.android.accelerometerTouchTrackerSdk.managers.ActivityTouchManager;
import com.behaviosec.android.accelerometerTouchTrackerSdk.models.AccelerometerEventModel;
import com.behaviosec.android.accelerometerTouchTrackerSdk.models.AccuracyChangedModel;
import com.behaviosec.android.accelerometerTouchTrackerSdk.models.ManagerErrorModel;
import com.behaviosec.android.accelerometerTouchTrackerSdk.models.MotionEventModel;
import com.behaviosec.android.accelerometerTouchTrackerSdk.repositories.HelpersRepository;
import com.behaviosec.android.sample.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //#region AccelerometerManager
        AccelerometerManager accelerometerManager = new AccelerometerManager(this, new HelpersRepository(), new TouchTrackerConfig());

        accelerometerManager.setDebugMode(true).setLoggingEnabled(true);

        accelerometerManager.addListener(new AccelerometerListener() {

            @Override
            public void onAccuracyChanged(@NonNull AccuracyChangedModel model) {
                binding.accelerometerAccuracy.setText("Accuracy changed: " + model.getAccuracy() + " at " + model.getDate());
            }

            @Override
            public void onSensorChanged(@NonNull AccelerometerEventModel model) {
                binding.accelerometerSensor.setText("Sensor changed: " + model.getEvent().values[0] + ", " + model.getEvent().values[1] + ", " + model.getEvent().values[2] + " at " + model.getDate());
            }
        });

        accelerometerManager.addErrorListener(new AccelerometerErrorListener() {
            @Override
            public void onAccelerometerError(@NonNull ManagerErrorModel error) {
                Log.e("AccelerometerManager", "Error: " + error.getMessage() + " at " + error.getDate());
                binding.accelerometerAccuracy.setText("Error: " + error.getMessage() + " at " + error.getDate());
                binding.accelerometerSensor.setText("Error: " + error.getMessage() + " at " + error.getDate());
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
        ActivityTouchManager activityTouchManager = new ActivityTouchManager(this, new TouchTrackerConfig());

        activityTouchManager.setListener(new ActivityTouchListener() {
            @Override
            public boolean dispatchTouchEvent(@NonNull MotionEventModel model) {
                Log.d("ActivityTouchManager", "Touch event: " + model.getEvent() + " at " + model.getDate());
                binding.touchDetails.setText("Touch event: " + model.getEvent() + " at " + model.getDate());

                return true;
            }
        });

        activityTouchManager.setErrorListener(new AccelerometerErrorListener() {
            @Override
            public void onAccelerometerError(@NonNull ManagerErrorModel error) {
                Log.e("ActivityTouchManager", "Error: " + error.getMessage() + " at " + error.getDate());
                binding.touchDetails.setText("Error: " + error.getMessage() + " at " + error.getDate());
            }
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
        super.onBackPressed();
        finishActivity();
    }

    private void finishActivity() {
        Intent intent = new Intent(this, StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}