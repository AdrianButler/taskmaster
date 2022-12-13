package com.adrianbutler.taskmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.adrianbutler.taskmaster.R;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

public class SignUpActivity extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		setupSignupForm();
	}

	private void setupSignupForm()
	{
		findViewById(R.id.SignUpButton).setOnClickListener(view ->
		{
			EditText emailEditText = findViewById(R.id.SignUpEmailEditText);
			EditText passwordEditText = findViewById(R.id.SignUpPasswordEditText);

			String userEmail = emailEditText.getText().toString();
			String userPassword = passwordEditText.getText().toString();

			Amplify.Auth.signUp(
					userEmail,
					userPassword,
					AuthSignUpOptions.builder()
							.userAttribute(AuthUserAttributeKey.email(), userEmail)
							.build(),
					success ->
					{
						Log.i("SignUp", "Signup successfully");
						Intent goToVerifyActivity = new Intent(this, VerifyActivity.class);
						goToVerifyActivity.putExtra("signup_email", userEmail);
						startActivity(goToVerifyActivity);
					},
					failure ->
					{
						Log.w("SignUp", "Failed to signup: " + failure.getMessage());
					}
			);
		});
	}
}