package com.behaviosec.android.sample.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.behaviosec.android.sample.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStartBinding binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.javaSampleButton.setOnClickListener(v -> {
            startActivity(new Intent(this, JavaSampleActivity.class));
        });

        binding.sdkActionsButton.setOnClickListener(v -> {
            startActivity(new Intent(this, SdkActionsActivity.class));
        });

        binding.kotlinSampleButton.setOnClickListener(v -> {
            startActivity(new Intent(this, KotlinSampleActivity.class));
        });

        binding.composeSampleButton.setOnClickListener(v -> {
            startActivity(new Intent(this, ComposeSampleActivity.class));
        });

        binding.coroutineSampleButton.setOnClickListener(v -> {
            startActivity(new Intent(this, CoroutineSampleActivity.class));
        });

        binding.livedataSampleButton.setOnClickListener(v -> {
            startActivity(new Intent(this, LiveDataSampleActivity.class));
        });

        binding.sensorSampleButton.setOnClickListener(v -> {
            startActivity(new Intent(this, SensorSampleActivity.class));
        });


    }

}