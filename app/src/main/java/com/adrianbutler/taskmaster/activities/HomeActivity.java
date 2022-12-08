package com.adrianbutler.taskmaster.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adrianbutler.taskmaster.R;
import com.adrianbutler.taskmaster.adapters.TaskRecyclerViewAdapter;
import com.adrianbutler.taskmaster.models.Task;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
{
	public static final String DATABASE_NAME = "task_master_db";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		setupDatabase();
		setupRecyclerView();
		assignButtonHandlers();
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		setupGreeting();
	}

	private void setupDatabase()
	{

	}

	private void setupRecyclerView()
	{
		List<Task> tasks = new ArrayList<>();

		tasks.add(new Task("Vacuum", "Vacuum the living room and the bedroom", Task.State.ASSIGNED));
		tasks.add(new Task("Cook", "Cook lunch", Task.State.ASSIGNED));
		tasks.add(new Task("Homework", "Finish code fellows homework", Task.State.ASSIGNED));
		tasks.add(new Task("Wash Car", "Use hose and rag to wash off car", Task.State.ASSIGNED));
		tasks.add(new Task("File taxes", "File taxes before the end of March", Task.State.ASSIGNED));
		tasks.add(new Task("Bathe Dog", "The dog stinks. Give him a bath soon.", Task.State.ASSIGNED));
		tasks.add(new Task("Clean shower", "Scrub out the shower with cleaner", Task.State.ASSIGNED));

		RecyclerView recyclerView = findViewById(R.id.HomeActivityTaskRecyclerView);

		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(layoutManager);

		TaskRecyclerViewAdapter taskRecyclerViewAdapter = new TaskRecyclerViewAdapter(tasks, this);
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

		setupIndividualTaskButtons();
	}

	private void setupIndividualTaskButtons()
	{
//		Button vacuumTaskButton = findViewById(R.id.HomeVacuumTaskButton);
//
//		Intent goToTaskDetailIntent = new Intent(this, TaskDetailActivity.class);
//
//		vacuumTaskButton.setOnClickListener(view ->
//		{
//			goToTaskDetailIntent.putExtra(TASK_TITLE_EXTRA_TAG, vacuumTaskButton.getText().toString());
//			startActivity(goToTaskDetailIntent);
//		});
//
//		Button mowLawnTaskButton = findViewById(R.id.HomeMowLawnTaskButton);
//
//		mowLawnTaskButton.setOnClickListener(view ->
//		{
//			goToTaskDetailIntent.putExtra(TASK_TITLE_EXTRA_TAG, mowLawnTaskButton.getText().toString());
//			startActivity(goToTaskDetailIntent);
//		});
//
//		Button cleanRoomTaskButton = findViewById(R.id.HomeCleanRoomTaskButton);
//
//		cleanRoomTaskButton.setOnClickListener(view ->
//		{
//			goToTaskDetailIntent.putExtra(TASK_TITLE_EXTRA_TAG, cleanRoomTaskButton.getText().toString());
//			startActivity(goToTaskDetailIntent);
//		});
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