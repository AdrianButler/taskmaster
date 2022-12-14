package com.adrianbutler.taskmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.adrianbutler.taskmaster.R;
import com.amplifyframework.core.Amplify;

public class VerifyActivity extends AppCompatActivity
{
	Intent callingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verify);
		callingIntent = getIntent();
		setupVerifyForm();
	}

	private void setupVerifyForm()
	{
		String userEmail = callingIntent.getStringExtra("signup_email");
		findViewById(R.id.VerifyButton).setOnClickListener(view ->
		{
			EditText verificationCodeEditText = findViewById(R.id.VerifyEmailTextEdit);
			String verificationCode = verificationCodeEditText.getText().toString();

			Amplify.Auth.confirmSignUp(
					userEmail,
					verificationCode,
					success -> {
						Log.i("verify", "Succesfully verified email");
						Intent goToLoginIntent = new Intent(this, SignInActivity.class);
						goToLoginIntent.putExtra("signup_email", userEmail);
						startActivity(goToLoginIntent);
					},
					failure -> Log.w("verify", "Failed to verify email")
			);
		});

	}
}