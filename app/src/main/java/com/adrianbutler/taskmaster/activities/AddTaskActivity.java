package com.adrianbutler.taskmaster.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.room.Room;

import com.adrianbutler.taskmaster.R;
import com.adrianbutler.taskmaster.database.TaskMasterDatabase;
import com.adrianbutler.taskmaster.models.Task;

public class AddTaskActivity extends AppCompatActivity {

    TaskMasterDatabase taskMasterDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        taskMasterDatabase = Room.databaseBuilder(getApplicationContext(),
                        TaskMasterDatabase.class,
                        HomeActivity.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        assignButtonHandler();
        setupStateDropdown();
    }

    private void assignButtonHandler() {
        Button submitTaskButton = findViewById(R.id.AddTaskSubmitButton);


        submitTaskButton.setOnClickListener(view ->
        {
            TextView titleTextView = findViewById(R.id.AddTaskTitleTextEdit);
            TextView bodyTextView = findViewById(R.id.AddTaskTaskInfoTextEdit);
            Spinner stateDropdown = findViewById(R.id.AddTaskActivitySelectStateDropdown);
            Task task = new Task(
                    titleTextView.getText().toString(),
                    bodyTextView.getText().toString(),
                    Task.State.fromString(stateDropdown.getSelectedItem().toString())
            );

            taskMasterDatabase.taskDao().insertATask(task);

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
                Task.State.values()
        ));


    }


}