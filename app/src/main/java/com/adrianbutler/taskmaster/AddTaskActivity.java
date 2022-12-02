package com.adrianbutler.taskmaster;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class AddTaskActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        assignButtonHandler();
    }

    private void assignButtonHandler() {
        Button submitTaskButton = findViewById(R.id.AddTaskSubmitButton);


        submitTaskButton.setOnClickListener(view ->
        {
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

            TextView taskCount = findViewById(R.id.AddTaskCountText);

            String count = (Integer.parseInt(taskCount.getText().toString()) + 1) + "";
            taskCount.setText(count);
            System.out.println(count);
        });

    }


}