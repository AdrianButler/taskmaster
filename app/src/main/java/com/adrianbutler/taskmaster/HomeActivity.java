package com.adrianbutler.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        assignButtonHandlers();
    }

    private void assignButtonHandlers()
    {
        Button addTaskButton = this.findViewById(R.id.HomeActivityAddTaskButton);

        addTaskButton.setOnClickListener(view ->
        {
            Intent goToAddTaskActivity = new Intent(this, AddTaskActivity.class);

            startActivity(goToAddTaskActivity);
        });

        Button allTasksButton = this.findViewById(R.id.HomeActivityAllTasksButton);

        allTasksButton.setOnClickListener(view ->
        {
            Intent goToAllTasksActivity = new Intent(this, AllTasksActivity.class);

            startActivity(goToAllTasksActivity);
        });

        ImageButton settingsButton = findViewById(R.id.HomeActivitySettingsButton);

        settingsButton.setOnClickListener(view ->
        {
            Intent goToSettingsActivity = new Intent(this, SettingsActivity.class);

            startActivity(goToSettingsActivity);
        });

    }

}