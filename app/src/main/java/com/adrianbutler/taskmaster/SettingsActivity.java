package com.adrianbutler.taskmaster;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    SharedPreferences preferences;
    public static final String USERNAME_TAG = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        assignSaveButtonHandler();
    }

    public void assignSaveButtonHandler()
    {
        // shared preferences is read only by default

        SharedPreferences.Editor preferenceEditor = preferences.edit();

        Button saveButton = findViewById(R.id.SettingsSaveButton);

        EditText usernameText = findViewById(R.id.SettingsUsernameEditText);

        saveButton.setOnClickListener(view ->
        {
            preferenceEditor.putString(USERNAME_TAG, usernameText.getText().toString());
            preferenceEditor.apply();

            Toast.makeText(this, "Username Saved!", Toast.LENGTH_SHORT).show();
        });

    }
}