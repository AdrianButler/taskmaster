package com.adrianbutler.taskmaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity
{

	public static final String TASK_TITLE_EXTRA_TAG = "taskTitle";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		assignButtonHandlers();
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		setupGreeting();
	}

	private void assignButtonHandlers()
	{
		Button addTaskButton = this.findViewById(R.id.HomeActivityAddTaskButton);

		addTaskButton.setOnClickListener(view ->
		{
			Intent goToAddTaskActivity = new Intent(this, AddTaskActivity.class);

			startActivity(goToAddTaskActivity);
		});

		Button allTasksButton = this.findViewById(R.id.HomeActivityAllTasksButton);

		allTasksButton.setOnClickListener(view ->
		{
			Intent goToAllTasksActivity = new Intent(this, AllTasksActivity.class);

			startActivity(goToAllTasksActivity);
		});

		ImageButton settingsButton = findViewById(R.id.HomeActivitySettingsButton);

		settingsButton.setOnClickListener(view ->
		{
			Intent goToSettingsActivity = new Intent(this, SettingsActivity.class);

			startActivity(goToSettingsActivity);
		});

		setupIndividualTaskButtons();
	}

	private void setupIndividualTaskButtons()
	{
		Button vacuumTaskButton = findViewById(R.id.HomeVacuumTaskButton);

		Intent goToTaskDetailIntent = new Intent(this, TaskDetailActivity.class);

		vacuumTaskButton.setOnClickListener(view ->
		{
			goToTaskDetailIntent.putExtra(TASK_TITLE_EXTRA_TAG, vacuumTaskButton.getText().toString());
			startActivity(goToTaskDetailIntent);
		});

		Button mowLawnTaskButton = findViewById(R.id.HomeMowLawnTaskButton);

		mowLawnTaskButton.setOnClickListener(view ->
		{
			goToTaskDetailIntent.putExtra(TASK_TITLE_EXTRA_TAG, mowLawnTaskButton.getText().toString());
			startActivity(goToTaskDetailIntent);
		});

		Button cleanRoomTaskButton = findViewById(R.id.HomeCleanRoomTaskButton);

		cleanRoomTaskButton.setOnClickListener(view ->
		{
			goToTaskDetailIntent.putExtra(TASK_TITLE_EXTRA_TAG, cleanRoomTaskButton.getText().toString());
			startActivity(goToTaskDetailIntent);
		});
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