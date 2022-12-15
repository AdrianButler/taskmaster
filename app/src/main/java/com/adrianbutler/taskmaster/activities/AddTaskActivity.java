package com.adrianbutler.taskmaster.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AddTaskActivity extends AppCompatActivity
{
	public final static String TAG = "AddTaskActivity";
	private final CompletableFuture<List<Team>> teamsFuture = new CompletableFuture<>();
	private ActivityResultLauncher<Intent> activityResultLauncher;
	private String s3ImageKey;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		activityResultLauncher = getImagePickingActivityResultLauncher();
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
					.s3ImageKey(s3ImageKey)
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

		FloatingActionButton addImageButton = findViewById(R.id.AddTaskAddImageButton);
		addImageButton.setOnClickListener(view -> launchImageIntent());

	}

	private void launchImageIntent()
	{
		Intent imageFilePickingIntent = new Intent(Intent.ACTION_GET_CONTENT);
		imageFilePickingIntent.setType("*/*");
		imageFilePickingIntent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/jpeg", "image/png"});
		activityResultLauncher.launch(imageFilePickingIntent);
	}

	private ActivityResultLauncher<Intent> getImagePickingActivityResultLauncher()
	{

		return registerForActivityResult(
				new ActivityResultContracts.StartActivityForResult(),
				result ->
				{
					Uri pickedImageFileUri = result.getData().getData();
					try
					{
						InputStream pickedImageInputStream = getContentResolver().openInputStream(pickedImageFileUri);
						String pickedImageFilename = getFileNameFromUri(pickedImageFileUri);
						Log.i(TAG, "Successfully retrieved input stream from file on device.");
						uploadInputStreamToS3(pickedImageInputStream, pickedImageFilename, pickedImageFileUri);
					}
					catch (FileNotFoundException exception)
					{
						Log.e(TAG, "Could not retrieve file from file picker: " + exception.getMessage());
					}
				}
		);
	}

	private void uploadInputStreamToS3(InputStream imageInputStream, String imageFilename, Uri imageFileUri)
	{
		Amplify.Storage.uploadInputStream(
				imageFilename,
				imageInputStream,
				success ->
				{
					Log.i(TAG, "File upload was successful");
					s3ImageKey = success.getKey();

					ImageView previewImage = findViewById(R.id.AddTaskImagePreview);
					InputStream imageStreamCopy = null;

					try {
						imageStreamCopy = getContentResolver().openInputStream(imageFileUri);
					}
					catch (FileNotFoundException exception)
					{
						Log.e(TAG, "Could not copy file stream: " + exception.getMessage());
					}
					previewImage.setImageBitmap(BitmapFactory.decodeStream(imageStreamCopy));
				},
				failure ->
				{
					Log.w(TAG, "Failed to upload image: " + failure.getMessage());
				}
		);
	}

	@SuppressLint("Range")
	private String getFileNameFromUri(Uri uri)
	{
		String result = null;

		if (uri.getScheme().equals("content"))
		{
			try (Cursor cursor = getContentResolver().query(uri, null, null, null, null))
			{
				if (cursor != null && cursor.moveToFirst())
				{
					result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
				}
			}
		}
		if (result == null) {
			result = uri.getPath();
			int cut = result.lastIndexOf('/');
			if (cut != -1) {
				result = result.substring(cut + 1);
			}
		}
		return result;
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