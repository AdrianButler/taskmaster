package com.adrianbutler.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button submitButton = MainActivity.this.findViewById(R.id.MainActivitySubmitButton);

        submitButton.setOnClickListener(view ->
        {
            System.out.println("Hello");
            Log.v("", "Verbose");
            Log.d("", "Debug");
            Log.i("", "Information");
            Log.w("", "Warning");
            Log.e("", "Error");
            Log.wtf("", "What a terrible failure");

            TextView greeting = MainActivity.this.findViewById(R.id.textView);
            greeting.setText(R.string.newGreeting);
        });

        Button goToOrderFormButton = MainActivity.this.findViewById(R.id.MainActivityOrderFormButton);
        goToOrderFormButton.setOnClickListener(view ->
        {
            Intent goToOrderFormActivity = new Intent(this, OrderForm.class);

            startActivity(goToOrderFormActivity);
        });
    }
}