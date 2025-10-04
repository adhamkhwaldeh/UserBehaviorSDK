package com.behaviosec.android.sample.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.behaviosec.android.sample.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

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