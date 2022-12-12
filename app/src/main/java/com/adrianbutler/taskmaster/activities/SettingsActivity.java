package com.adrianbutler.taskmaster.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.adrianbutler.taskmaster.R;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity
{

	SharedPreferences preferences;
	public static final String USERNAME_TAG = "username";
	public static final String TEAM_TAG = "team";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		setupTeamSpinner();
		assignSaveButtonHandler();
	}

	private void assignSaveButtonHandler()
	{
		Button saveButton = findViewById(R.id.SettingsSaveButton);

		saveButton.setOnClickListener(view -> // save username and display a toast
		{
			EditText usernameText = findViewById(R.id.SettingsUsernameEditText);
			saveUsername(usernameText.getText().toString());

			Spinner teamDropdown = findViewById(R.id.SettingsActivitySelectTeamDropdown);
			saveTeam(teamDropdown.getSelectedItem().toString());

			Toast.makeText(this, "Username Saved!", Toast.LENGTH_SHORT).show();
		});

	}

	private void saveUsername(String username)
	{
		SharedPreferences.Editor preferenceEditor = preferences.edit();

		preferenceEditor.putString(USERNAME_TAG, username);
		preferenceEditor.apply();
	}

	private void saveTeam(String teamName)
	{
		SharedPreferences.Editor preferenceEditor = preferences.edit();

		preferenceEditor.putString(TEAM_TAG, teamName);
		preferenceEditor.apply();
	}

	private void setupTeamSpinner()
	{
		Amplify.API.query(ModelQuery.list(Team.class),
				success ->
				{
					Log.i("Settings", "Successfully queried team names");
					Spinner selectTeamDropdown = findViewById(R.id.SettingsActivitySelectTeamDropdown);
					ArrayList<String> teamNames = new ArrayList<>();
					for (Team team : success.getData())
					{
						teamNames.add(team.getName());
					}
					runOnUiThread(() ->
					{
						selectTeamDropdown.setAdapter(new ArrayAdapter<>(
										this,
										android.R.layout.simple_spinner_item,
										teamNames
								)
						);
					});

				},
				failure -> Log.e("Settings", "Failed to query team names")
		);
	}
}