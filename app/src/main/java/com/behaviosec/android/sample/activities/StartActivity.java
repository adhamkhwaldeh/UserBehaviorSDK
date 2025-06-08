package com.behaviosec.android.sample.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.behaviosec.android.sample.R;
import com.behaviosec.android.sample.SampleApp;

public class StartActivity extends AppCompatActivity {
    private SampleApp application;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        application = (SampleApp) getApplication();

        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(v -> {
            goToLogin();
        });
    }

    private void goToLogin() {
        Intent nextActivityIntent = new Intent(this, LoginActivity.class);
        startActivity(nextActivityIntent);
    }
}