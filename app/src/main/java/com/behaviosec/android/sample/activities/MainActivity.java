package com.behaviosec.android.sample.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK;
import com.github.adhamkhwaldeh.userBehaviorSDK.config.AccelerometerConfig;
import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig;
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.AccelerometerErrorListener;
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener;
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener;
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener;
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.IAccelerometerManager;
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.ITouchManager;
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.base.IBaseManager;
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs.ActivityTouchManager;
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel;
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel;
import com.github.adhamkhwaldeh.userBehaviorSDK.models.ManagerErrorModel;
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel;
import com.behaviosec.android.sample.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        UserBehaviorCoreSDK userBehaviorCoreSDK = UserBehaviorCoreSDK.Companion.getInstance(this);
        //#region AccelerometerManager
        IAccelerometerManager accelerometerManager = userBehaviorCoreSDK.getAccelerometerManager(new AccelerometerConfig());
//        AccelerometerManager accelerometerManager = new AccelerometerManager(this, new HelpersRepository(),new AccelerometerConfig() );
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

        //#region ActivityTouchManager

        ITouchManager activityTouchManager = userBehaviorCoreSDK.fetchOrCreateActivityTouchManager(this, new TouchConfig());

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