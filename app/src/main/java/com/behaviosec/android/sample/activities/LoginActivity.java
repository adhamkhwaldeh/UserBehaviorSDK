package com.behaviosec.android.sample.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.behaviosec.android.sample.R;
import com.behaviosec.android.sample.SampleApp;
import com.behaviosec.android.sample.fragments.InfoDialogFragment;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText usernameField;
    private TextInputEditText passwordField;
    private AppCompatButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        usernameField = findViewById(R.id.username);
        passwordField = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryToLogin();
            }
        });
    }

    private void tryToLogin() {
        final String username = usernameField.getText().toString();
        final String password = passwordField.getText().toString();

        //TODO: Validate input: not empty, minimum length, etc.

        // Check credentials with the server, together with the used journeyId
        checkCredentials(username, password);

        // Clean fields
        usernameField.setText("");
        passwordField.setText("");
    }

    private void checkCredentials(final String username, final String password) {
        // In a real app, this is done server-side. For this sample, we only accept the following:
        // username: behaviouser
        // password: password
        final String VALID_USERNAME = "behaviouser";
        final String VALID_PASSWORD = "password";

        onLoginSuccessful();
//        if (VALID_USERNAME.equals(username) && VALID_PASSWORD.equals(password)) {
//            onLoginSuccessful();
//        } else {
//            onLoginFailed();
//        }
    }

    // Callback method when entered credentials are correct
    public void onLoginSuccessful() {
        // Move to next Activity
        Intent nextActivityIntent = new Intent(this, MainActivity.class);
        startActivity(nextActivityIntent);
    }

    // Callback method when entered credentials are not correct
    public void onLoginFailed() {
        // Show wrong credentials dialog
        InfoDialogFragment msgDialog = InfoDialogFragment.newInstance(R.string.login_failed, R.string.wrong_usernamepassword);
        msgDialog.show(getSupportFragmentManager(), InfoDialogFragment.class.getSimpleName());
    }
}