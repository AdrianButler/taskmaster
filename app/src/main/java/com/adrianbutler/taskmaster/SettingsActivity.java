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

    private void assignSaveButtonHandler()
    {
        Button saveButton = findViewById(R.id.SettingsSaveButton);

        saveButton.setOnClickListener(view -> // save username and display a toast
        {
            EditText usernameText = findViewById(R.id.SettingsUsernameEditText);
            saveUsername(usernameText.getText().toString());

            Toast.makeText(this, "Username Saved!", Toast.LENGTH_SHORT).show();
        });

    }

    private void saveUsername(String username)
    {
        SharedPreferences.Editor preferenceEditor = preferences.edit();

        preferenceEditor.putString(USERNAME_TAG, username);
        preferenceEditor.apply();
    }
}