package com.example.eyetestinginterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class TestEvaluate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_evaluate);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        String distance = bundle.getString("distance");


        Toast.makeText(this, distance, Toast.LENGTH_SHORT).show();
    }
}