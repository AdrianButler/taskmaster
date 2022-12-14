package com.adrianbutler.taskmaster.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adrianbutler.taskmaster.R;
import com.adrianbutler.taskmaster.adapters.TaskRecyclerViewAdapter;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HomeActivity extends AppCompatActivity
{
	public static final String TAG = "HomeActivity";
	private List<Task> tasks = new ArrayList<>();
	private TaskRecyclerViewAdapter taskRecyclerViewAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		setupRecyclerView();
		assignButtonHandlers();
	}

	@Override
	protected void onResume()
	{
		super.onResume();


		Amplify.Auth.fetchUserAttributes(
				success ->
				{
					Optional<AuthUserAttribute> test = success.stream().filter(value -> value.getKey().getKeyString().equals("email")).findFirst();
					test.ifPresent(authUserAttribute -> setUpEmailText(authUserAttribute.getValue()));
					Button signOutButton = findViewById(R.id.HomeActivitySignOutButton);
					signOutButton.setVisibility(View.VISIBLE);
				},
				failure -> {
					Log.i(TAG, "No email found: " + failure.getMessage());
					hideSignOutButton();
				}
		);

		Amplify.API.query(
				ModelQuery.list(Task.class),
				success ->
				{
					Log.i(TAG, "Queried Tasks successfully");
					tasks.removeAll(tasks);

					SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
					String teamName = preferences.getString(SettingsActivity.TEAM_TAG, "");

					for (Task taskFromDB : success.getData())
					{
						if (taskFromDB.getTeam().getName().equals(teamName))
						{
							tasks.add(taskFromDB);
						}
					}
					runOnUiThread(() -> taskRecyclerViewAdapter.notifyDataSetChanged());
				},
				failure ->
				{
					Log.e(TAG, "Failed to query tasks");
				});

		setupGreeting();
	}

	private void setUpEmailText(String email)
	{
		TextView emailTextView = findViewById(R.id.HomeEmailTextView);
		emailTextView.setText(email);
	}

	private void setupRecyclerView()
	{
		RecyclerView recyclerView = findViewById(R.id.HomeActivityTaskRecyclerView);

		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);

		taskRecyclerViewAdapter = new TaskRecyclerViewAdapter(tasks, this);
		recyclerView.setAdapter(taskRecyclerViewAdapter);
	}

	private void assignButtonHandlers()
	{
		Button addTaskButton = this.findViewById(R.id.HomeActivityAddTaskButton);

		addTaskButton.setOnClickListener(view ->
		{
			Intent goToAddTaskActivity = new Intent(this, AddTaskActivity.class);

			startActivity(goToAddTaskActivity);
		});

		ImageButton settingsButton = findViewById(R.id.HomeActivitySettingsButton);

		settingsButton.setOnClickListener(view ->
		{
			Intent goToSettingsActivity = new Intent(this, SettingsActivity.class);

			startActivity(goToSettingsActivity);
		});

		Button signUpButton = findViewById(R.id.HomeActivitySignUpButton);
		signUpButton.setOnClickListener(view ->
		{
			Intent goToSignUpIntent = new Intent(this, SignUpActivity.class);
			startActivity(goToSignUpIntent);
		});

		Button signInButton = findViewById(R.id.HomeActivitySignInButton);
		signInButton.setOnClickListener(view ->
		{
			Intent goToSignInIntent = new Intent(this, SignInActivity.class);
			startActivity(goToSignInIntent);
		});

		Button signOutButton = findViewById(R.id.HomeActivitySignOutButton);
		signOutButton.setOnClickListener(view ->
		{
			Amplify.Auth.signOut(signOutResult ->
			{
				Log.i(TAG, "Attempting to sign out: " + signOutResult);
				hideSignOutButton();
			});
		});
	}

	private void hideSignOutButton()
	{
		Button signOutButton = findViewById(R.id.HomeActivitySignOutButton);
		runOnUiThread(() -> signOutButton.setVisibility(View.GONE));
	}


	private void setupGreeting()
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		String username = preferences.getString(SettingsActivity.USERNAME_TAG, "");

		TextView nameTextView = findViewById(R.id.HomeNameTextView);
		if (!username.equals(""))
		{
			String greeting = username + "'s  tasks:";
			nameTextView.setText(greeting);
		}
	}

}