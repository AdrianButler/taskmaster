package com.adrianbutler.taskmaster.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.adrianbutler.taskmaster.R;

public class TaskDetailActivity extends AppCompatActivity
{
	public static final String TASK_TITLE_EXTRA_TAG = "taskTitle";
	public static final String TASK_BODY_EXTRA_TAG = "taskBody";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_detail);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		consumeTitleExtra();
	}

	private void consumeTitleExtra()
	{
		Intent callingIntent = getIntent();
		String taskTitle = callingIntent.getStringExtra(TASK_TITLE_EXTRA_TAG);
		String taskBody = callingIntent.getStringExtra(TASK_BODY_EXTRA_TAG);

		TextView titleTextView = findViewById(R.id.TaskDetailTitleText);
		titleTextView.setText(taskTitle);

		TextView bodyTextView = findViewById(R.id.TaskDetailDescriptionText);
		bodyTextView.setText(taskBody);
	}
}