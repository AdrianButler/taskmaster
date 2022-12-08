package com.adrianbutler.taskmaster.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.adrianbutler.taskmaster.R;
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskEnum;

import java.util.Date;

public class AddTaskActivity extends AppCompatActivity
{
	public final static String TAG = "AddTaskActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);

		assignButtonHandler();
		setupStateDropdown();
	}

	private void assignButtonHandler()
	{
		Button submitTaskButton = findViewById(R.id.AddTaskSubmitButton);


		submitTaskButton.setOnClickListener(view ->
		{
			TextView titleTextView = findViewById(R.id.AddTaskTitleTextEdit);
			TextView bodyTextView = findViewById(R.id.AddTaskTaskInfoTextEdit);
			Spinner stateDropdown = findViewById(R.id.AddTaskActivitySelectStateDropdown);

			Task newTask = Task.builder()
					.title(titleTextView.getText().toString())
					.state((TaskEnum) stateDropdown.getSelectedItem())
					.dateCreated(new Temporal.DateTime(new Date(), 0))
					.body(bodyTextView.getText().toString())
					.build();
//            );

			Amplify.API.mutate(
					ModelMutation.create(newTask),
					success -> Log.i(TAG, "Saved task to db successfully"),
					failure -> Log.w(TAG, "Failed to save task to db")
			);


			TextView submittedTextView = new TextView(this);
			submittedTextView.setText(R.string.submitted);
			submittedTextView.setTextSize(40);
			submittedTextView.setTextColor(Color.GREEN);


			submittedTextView.setId(View.generateViewId()); // generate id for constraints

			// add the submitted text to the main activity and add constraints to it

			ConstraintLayout layout = findViewById(R.id.AddTask);
			ConstraintSet constraintSet = new ConstraintSet();

			layout.addView(submittedTextView, 0);

			constraintSet.clone(layout);
			constraintSet.connect(submittedTextView.getId(), ConstraintSet.START, layout.getId(), ConstraintSet.START);
			constraintSet.connect(submittedTextView.getId(), ConstraintSet.END, layout.getId(), ConstraintSet.END);
			constraintSet.connect(submittedTextView.getId(), ConstraintSet.TOP, layout.getId(), ConstraintSet.TOP);
			constraintSet.connect(submittedTextView.getId(), ConstraintSet.BOTTOM, layout.getId(), ConstraintSet.BOTTOM);
			constraintSet.applyTo(layout);
		});

	}

	private void setupStateDropdown()
	{
		Spinner stateDropdown = findViewById(R.id.AddTaskActivitySelectStateDropdown);
		stateDropdown.setAdapter(new ArrayAdapter<>(
				this,
				android.R.layout.simple_spinner_item,
				TaskEnum.values()
		));


	}


}