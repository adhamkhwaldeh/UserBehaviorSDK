package com.behaviosec.android.sample.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.behaviosec.android.sample.R;

public class MainActivity extends AppCompatActivity
{
	private AppCompatButton logoutButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		logoutButton = findViewById(R.id.logout_button);
		logoutButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finishActivity();
			}
		});
	}

	@Override
	public void onBackPressed()
	{
		finishActivity();
	}

	private void finishActivity()
	{
		Intent intent = new Intent(this, StartActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}
}