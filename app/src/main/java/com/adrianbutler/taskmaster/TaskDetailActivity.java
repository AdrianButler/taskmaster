package com.adrianbutler.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TaskDetailActivity extends AppCompatActivity
{
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
		String productTitle = callingIntent.getStringExtra(HomeActivity.TASK_TITLE_EXTRA_TAG);

		TextView titleTextView = findViewById(R.id.TaskDetailTitleText);
		titleTextView.setText(productTitle);
	}
}