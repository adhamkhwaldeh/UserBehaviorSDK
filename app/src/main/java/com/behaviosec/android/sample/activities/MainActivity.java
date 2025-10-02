package com.behaviosec.android.sample.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.behaviosec.android.userBehaviorSDK.UserBehaviorCoreSDK;
import com.behaviosec.android.userBehaviorSDK.config.TouchTrackerConfig;
import com.behaviosec.android.userBehaviorSDK.listeners.errors.AccelerometerErrorListener;
import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.AccelerometerListener;
import com.behaviosec.android.userBehaviorSDK.listeners.errors.TouchErrorListener;
import com.behaviosec.android.userBehaviorSDK.listeners.callbacks.TouchListener;
import com.behaviosec.android.userBehaviorSDK.managers.AccelerometerManager;
import com.behaviosec.android.userBehaviorSDK.managers.TouchManager;
import com.behaviosec.android.userBehaviorSDK.models.AccelerometerEventModel;
import com.behaviosec.android.userBehaviorSDK.models.AccuracyChangedModel;
import com.behaviosec.android.userBehaviorSDK.models.ManagerErrorModel;
import com.behaviosec.android.userBehaviorSDK.models.MotionEventModel;
import com.behaviosec.android.userBehaviorSDK.repositories.HelpersRepository;
import com.behaviosec.android.sample.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //#region AccelerometerManager
        UserBehaviorCoreSDK.Companion.getInstance(this).;

        AccelerometerManager accelerometerManager = new AccelerometerManager(this, new HelpersRepository(), new TouchTrackerConfig());
        accelerometerManager.start();
        accelerometerManager.setEnabled(true).setDebugMode(true).setLoggingEnabled(true);

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
            public void onError(@NonNull ManagerErrorModel error) {
                Log.e("AccelerometerManager", "Error: " + error.getMessage());
                binding.accelerometerAccuracy.setText("Error: " + error.getMessage());
                binding.accelerometerSensor.setText("Error: " + error.getMessage());
            }
        });

        binding.startAccelerometerButton.setOnClickListener(v -> {
            accelerometerManager.start();
        });

        binding.stopAccelerometerButton.setOnClickListener(v -> {
            accelerometerManager.stop();
        });
        //#endregion

        TouchManager activityTouchManager = new TouchManager.Builder().forActivity(this).build();

        //        touchManager.start();
        //#region ActivityTouchManager
//        ActivityTouchManager activityTouchManager = new ActivityTouchManager(this, new TouchTrackerConfig());

        activityTouchManager.addListener(new TouchListener() {
            @Override
            public boolean dispatchTouchEvent(@NonNull MotionEventModel model) {
                Log.d("ActivityTouchManager", "Touch event: " + model.getEvent() + " at " + model.getDate());
                binding.touchDetails.setText("Touch event: " + model.getEvent() + " at " + model.getDate());
                return true;
            }
        });

        activityTouchManager.addErrorListener(new TouchErrorListener() {
            @Override
            public void onError(@NonNull ManagerErrorModel error) {
                Log.e("ActivityTouchManager", "Error: " + error.getMessage());
                binding.touchDetails.setText("Error: " + error.getMessage());
            }
        });

        binding.startTouchButton.setOnClickListener(v -> {
            activityTouchManager.start();
//            activityTouchManager.setEnabled(true);
        });

        binding.stopTouchButton.setOnClickListener(v -> {
            activityTouchManager.stop();
//            activityTouchManager.setEnabled(false);
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