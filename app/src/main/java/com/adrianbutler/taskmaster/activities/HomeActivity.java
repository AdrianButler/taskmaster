package com.adrianbutler.taskmaster.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adrianbutler.taskmaster.R;
import com.adrianbutler.taskmaster.adapters.TaskRecyclerViewAdapter;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Task;

import java.util.ArrayList;
import java.util.List;

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
		Amplify.API.query(
				ModelQuery.list(Task.class),
				success ->
				{
					Log.i(TAG, "Queried Tasks successfully");
					tasks.removeAll(tasks);
					for (Task taskFromDB : success.getData())
					{
						tasks.add(taskFromDB);
					}
					runOnUiThread(() -> taskRecyclerViewAdapter.notifyDataSetChanged());
				},
				failure ->
				{
					Log.e(TAG, "Failed to query tasks");
				});

		setupGreeting();
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