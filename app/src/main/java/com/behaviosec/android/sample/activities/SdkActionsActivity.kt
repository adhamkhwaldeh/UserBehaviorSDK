package com.behaviosec.android.sample.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.behaviosec.android.sample.databinding.ActivitySdkActionsBinding;

public class SdkActionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySdkActionsBinding binding = ActivitySdkActionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

}