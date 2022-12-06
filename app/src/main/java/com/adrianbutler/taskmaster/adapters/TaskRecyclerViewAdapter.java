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
import com.adrianbutler.taskmaster.models.Task;

import java.util.List;

public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.TaskViewHolder>
{
	private List<Task> tasks;
	private Context callingActivity;

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

