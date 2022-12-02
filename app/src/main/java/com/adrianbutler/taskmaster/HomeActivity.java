package com.adrianbutler.taskmaster;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

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

    }

}