package com.adrianbutler.taskmaster.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adrianbutler.taskmaster.R;
import com.adrianbutler.taskmaster.activities.TaskDetailActivity;
import com.amplifyframework.datastore.generated.model.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder>
{
	private final List<Task> tasks;
	private final Context callingActivity;

	public TaskRecyclerViewAdapter(List<Task> tasks, Context callingActivity)
	{
		this.tasks = tasks;
		this.callingActivity = callingActivity;
	}

	@NonNull
	@Override
	public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		View taskFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task, parent, false);
		return new TaskViewHolder(taskFragment);
	}

	@Override
	public void onBindViewHolder(@NonNull TaskViewHolder holder, int position)
	{
		String taskTitle = tasks.get(position).getTitle();
		String taskBody = tasks.get(position).getBody();
		String taskState = tasks.get(position).getState().toString();

		DateFormat dateCreatedIso8601InputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
		dateCreatedIso8601InputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

		View taskItemView = holder.itemView;
		taskItemView.setOnClickListener(view ->
		{
			Intent goToTaskIntent = new Intent(callingActivity, TaskDetailActivity.class);
			goToTaskIntent.putExtra(TaskDetailActivity.TASK_TITLE_EXTRA_TAG, taskTitle);
			goToTaskIntent.putExtra(TaskDetailActivity.TASK_BODY_EXTRA_TAG, taskBody);
			goToTaskIntent.putExtra(TaskDetailActivity.TASK_STATUS_EXTRA_TAG, taskState);
			callingActivity.startActivity(goToTaskIntent);
		});

		TextView titleTextView = holder.itemView.findViewById(R.id.TaskFragmentTitle);
		TextView bodyTextView = holder.itemView.findViewById(R.id.TaskFragmentBody);



		String formattedTaskTitle = position + ". " + taskTitle;

		titleTextView.setText(formattedTaskTitle);
		bodyTextView.setText(taskBody);
	}

	@Override
	public int getItemCount()
	{
		return tasks.size();
	}

	public static class TaskViewHolder extends RecyclerView.ViewHolder
	{
		public TaskViewHolder(@NonNull View itemView)
		{
			super(itemView);
		}
	}
}

