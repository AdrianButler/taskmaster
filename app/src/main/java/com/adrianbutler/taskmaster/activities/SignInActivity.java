package com.adrianbutler.taskmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.adrianbutler.taskmaster.R;
import com.amplifyframework.core.Amplify;

public class SignInActivity extends AppCompatActivity
{

	Intent callingIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		callingIntent = getIntent();
		setupSignInForm();
	}

	private void setupSignInForm()
	{
		final String[] userEmail = {callingIntent.getStringExtra("signup_email")};
		EditText emailEditText = findViewById(R.id.SignInEmailEditText);
		emailEditText.setText(userEmail[0]);

		findViewById(R.id.SigninButton).setOnClickListener(view ->
		{
			EditText passwordEditText = findViewById(R.id.SignInPasswordEditText);

			userEmail[0] = emailEditText.getText().toString();
			String userPassword = passwordEditText.getText().toString();

			Amplify.Auth.signIn(
					userEmail[0],
					userPassword,
					success ->
					{
						Log.i("signin", "Successfully signed in");
						Intent goHomeIntent = new Intent(this, HomeActivity.class);
						startActivity(goHomeIntent);
					},
					failure ->
					{
						Log.w("signin", "Failed to sign in" + failure.getMessage());
					}
			);
		});
	}
}