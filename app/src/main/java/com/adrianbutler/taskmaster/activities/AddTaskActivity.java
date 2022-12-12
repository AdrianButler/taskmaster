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
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Task;
import com.amplifyframework.datastore.generated.model.TaskEnum;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AddTaskActivity extends AppCompatActivity
{
	public final static String TAG = "AddTaskActivity";
	CompletableFuture<List<Team>> teamsFuture = new CompletableFuture<>();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
//		Team taskMonsters = Team.builder()
//				.name("TaskMonsters")
//				.build();
//		Team cleanFreaks = Team.builder()
//				.name("CleanFreaks")
//				.build();
//		Team scrubbers = Team.builder()
//				.name("Scrubbers")
//				.build();
//
//		Amplify.API.mutate(
//				ModelMutation.create(taskMonsters),
//				success -> {},
//				failure -> {}
//		);
//
//		Amplify.API.mutate(
//				ModelMutation.create(cleanFreaks),
//				success -> {},
//				failure -> {}
//		);
//
//		Amplify.API.mutate(
//				ModelMutation.create(scrubbers),
//				success -> {},
//				failure -> {}
//		);
		setupStateDropdown();
		getTeamsFromDatabase();
		assignButtonHandler();
	}

	private void assignButtonHandler()
	{
		Button submitTaskButton = findViewById(R.id.AddTaskSubmitButton);


		submitTaskButton.setOnClickListener(view ->
		{
			TextView titleTextView = findViewById(R.id.AddTaskTitleTextEdit);
			TextView bodyTextView = findViewById(R.id.AddTaskTaskInfoTextEdit);
			Spinner stateDropdown = findViewById(R.id.AddTaskActivitySelectStateDropdown);
			Spinner teamDropdown = findViewById(R.id.AddTaskActivitySelectTeamDropdown);
			String selectedTeamName = teamDropdown.getSelectedItem().toString();
			List<Team> teamList = null;
			try
			{
				teamList = teamsFuture.get();
			}
			catch (InterruptedException exception)
			{
				Log.e(TAG, "InterruptedException while getting teams");
				Thread.currentThread().interrupt();
			}
			catch (ExecutionException exception)
			{
				Log.e(TAG, "Error while getting teams");
				Thread.currentThread().interrupt();
			}

			Team selectedTeam = teamList.stream().filter(team -> team.getName().equals(selectedTeamName))
					.findAny()
					.orElseThrow(RuntimeException::new);
			Task newTask = Task.builder()
					.title(titleTextView.getText().toString())
					.state((TaskEnum) stateDropdown.getSelectedItem())
					.dateCreated(new Temporal.DateTime(new Date(), 0))
					.body(bodyTextView.getText().toString())
					.team(selectedTeam)
					.build();

			Amplify.API.mutate(ModelMutation.create(newTask), success -> Log.i(TAG, "Saved task to db successfully"), failure -> Log.w(TAG, "Failed to save task to db"));


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
		stateDropdown.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, TaskEnum.values()));
	}

	private void setupTeamDropdown(ArrayList<String> teamNames)
	{
		Spinner teamDropdown = findViewById(R.id.AddTaskActivitySelectTeamDropdown);
		teamDropdown.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teamNames));
	}

	private void getTeamsFromDatabase()
	{
		Amplify.API.query(ModelQuery.list(Team.class), success ->
		{
			Log.i(TAG, "Read teams from db successfully");
			ArrayList<String> teamNames = new ArrayList<>();
			ArrayList<Team> teams = new ArrayList<>();
			for (Team team : success.getData())
			{
				teamNames.add(team.getName());
				teams.add(team);
			}
			teamsFuture.complete(teams);
			runOnUiThread(() -> setupTeamDropdown(teamNames));
		}, failure ->
		{
			Log.w(TAG, "Failed to read teams from db");
			teamsFuture.complete(null);
		});
	}


}