package com.example.eyetestinginterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MoreInfoActivity extends AppCompatActivity {

    Button continue_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        continue_button = findViewById(R.id.continue_button);

        continue_button.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), Home.class));
        });
    }
}