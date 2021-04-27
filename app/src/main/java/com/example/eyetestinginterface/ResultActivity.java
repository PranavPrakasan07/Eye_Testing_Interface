package com.example.eyetestinginterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class ResultActivity extends AppCompatActivity {

    ImageView retry_button, finish_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        retry_button = findViewById(R.id.retry_button);
        finish_button = findViewById(R.id.finish_button);

        retry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.putExtra("toOpen", "Test");
                startActivity(intent);
            }
        });

        finish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Home.class);
                intent.putExtra("toOpen", "Maps");
                startActivity(intent);
            }
        });

    }
}