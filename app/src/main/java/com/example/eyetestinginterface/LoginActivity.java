package com.example.eyetestinginterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    TextView signup_link;

    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        signup_link = findViewById(R.id.signup_link);

        login_button = findViewById(R.id.login_button);

        Intent intent = getIntent();

        try {
            String email_text = intent.getStringExtra("email");
            String password_text = intent.getStringExtra("password");

            email.setText(email_text);
            password.setText(password_text);

        } catch (Exception e) {
            e.printStackTrace();
        }

        signup_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);

                Bundle bundle = new Bundle();

                String email_text = email.getText().toString();
                String password_text = password.getText().toString();

                bundle.putString("email", email_text);
                bundle.putString("password", password_text);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
}