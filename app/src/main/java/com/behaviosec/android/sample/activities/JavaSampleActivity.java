package com.behaviosec.android.sample.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.behaviosec.android.sample.databinding.ActivityXmlSampleBinding;
import com.behaviosec.android.sample.helpers.Helper;
import com.github.adhamkhwaldeh.userBehaviorSDK.UserBehaviorCoreSDK;
import com.github.adhamkhwaldeh.userBehaviorSDK.config.AccelerometerConfig;
import com.github.adhamkhwaldeh.userBehaviorSDK.config.TouchConfig;
import com.github.adhamkhwaldeh.commonsdk.exceptions.BaseUserBehaviorException;
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.AccelerometerErrorListener;
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.AccelerometerListener;
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.errors.TouchErrorListener;
import com.github.adhamkhwaldeh.userBehaviorSDK.listeners.callbacks.TouchListener;
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.accelerometer.IAccelerometerManager;
import com.github.adhamkhwaldeh.userBehaviorSDK.managers.touchs.ITouchManager;
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccelerometerEventModel;
import com.github.adhamkhwaldeh.userBehaviorSDK.models.AccuracyChangedModel;
import com.github.adhamkhwaldeh.userBehaviorSDK.models.MotionEventModel;

import org.koin.java.KoinJavaComponent;

public class JavaSampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityXmlSampleBinding binding = ActivityXmlSampleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 2. Get the UserBehaviorCoreSDK instance from Koin
        UserBehaviorCoreSDK userBehaviorCoreSDK = KoinJavaComponent.get(UserBehaviorCoreSDK.class);

        //#region AccelerometerManager
        IAccelerometerManager accelerometerManager = userBehaviorCoreSDK.getAccelerometerManager();
        accelerometerManager.setEnabled(true).setDebugMode(true).setLoggingEnabled(true);

        accelerometerManager.addListener(new AccelerometerListener() {
            @Override
            public void onAccuracyChanged(@NonNull AccuracyChangedModel model) {
                binding.accelerometerAccuracy.setText(Helper.INSTANCE.accuracyChangedMessage(model));
            }

            @Override
            public void onSensorChanged(@NonNull AccelerometerEventModel model) {
                binding.accelerometerSensor.setText(Helper.INSTANCE.accelerometerEventMessage(model));
                Log.d("AccelerometerManager", Helper.INSTANCE.accelerometerEventMessage(model));
            }
        });

        accelerometerManager.addErrorListener(new AccelerometerErrorListener() {
            @Override
            public void onError(@NonNull BaseUserBehaviorException error) {
                var msg = Helper.INSTANCE.managerErrorMessage(error);
                Log.e("AccelerometerManager", msg);
                binding.accelerometerAccuracy.setText(msg);
                binding.accelerometerSensor.setText(msg);
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

        ITouchManager activityTouchManager = userBehaviorCoreSDK.fetchOrCreateActivityTouchManager(this);

        activityTouchManager.addListener(new TouchListener() {
            @Override
            public boolean dispatchTouchEvent(@NonNull MotionEventModel model) {
                var msg = Helper.INSTANCE.motionEventMessage(model);
                Log.d("ActivityTouchManager", msg);
                binding.touchDetails.setText(msg);
                return true;
            }
        });

        activityTouchManager.addErrorListener(new TouchErrorListener() {
            @Override
            public void onError(@NonNull BaseUserBehaviorException error) {
                var msg = Helper.INSTANCE.managerErrorMessage(error);
                Log.e("ActivityTouchManager", msg);
                binding.touchDetails.setText(msg);
            }
        });

        binding.startTouchButton.setOnClickListener(v -> {
            activityTouchManager.start();
        });

        binding.stopTouchButton.setOnClickListener(v -> {
            activityTouchManager.stop();
        });

        //#endregion

        //#region ViewTouchManager

        ITouchManager viewTouchManager = userBehaviorCoreSDK.fetchOrCreateViewTouchManager(binding.greenView);

        viewTouchManager.addListener(new TouchListener() {
            @Override
            public boolean dispatchTouchEvent(@NonNull MotionEventModel model) {
                var msg = Helper.INSTANCE.motionEventMessage(model);
                Log.d("ViewTouchManager", msg);
                binding.touchViewDetails.setText(msg);
                return true;
            }
        });

        viewTouchManager.addErrorListener(new TouchErrorListener() {
            @Override
            public void onError(@NonNull BaseUserBehaviorException error) {
                var msg = Helper.INSTANCE.managerErrorMessage(error);
                Log.e("ActivityTouchManager", msg);
                binding.touchViewDetails.setText(msg);
            }
        });

        binding.startTouchViewButton.setOnClickListener(v -> {
            viewTouchManager.start();
        });

        binding.stopTouchViewButton.setOnClickListener(v -> {
            viewTouchManager.stop();
        });
        //#endregion

    }

}