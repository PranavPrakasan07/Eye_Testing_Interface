package com.example.eyetestinginterface;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTPActivity extends AppCompatActivity {

    TextView verify_button;

    private EditText digit1, digit2, digit3, digit4, digit5, digit6;
    private String d1, d2, d3, d4, d5, d6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        String mobile = getIntent().getStringExtra("mobile");
        String otp = getIntent().getStringExtra("otp");

        verify_button = findViewById(R.id.verify_button);

        digit1 = findViewById(R.id.digit1);
        digit2 = findViewById(R.id.digit2);
        digit3 = findViewById(R.id.digit3);
        digit4 = findViewById(R.id.digit4);
        digit5 = findViewById(R.id.digit5);
        digit6 = findViewById(R.id.digit6);

        verify_button.setOnClickListener(v -> {

            d1 = digit1.getText().toString().trim();
            d2 = digit2.getText().toString().trim();
            d3 = digit3.getText().toString().trim();
            d4 = digit4.getText().toString().trim();
            d5 = digit5.getText().toString().trim();
            d6 = digit6.getText().toString().trim();

            String otp_entered = d1 + d2 + d3 + d4 + d5 + d6;

            if(otp!= null) {
                Toast.makeText(this, otp + " : " + otp_entered, Toast.LENGTH_SHORT).show();

                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(otp, otp_entered);
                startActivity(new Intent(getApplicationContext(), Home.class));

            }
            else{
                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
            }


            numberOTPMove();

        });

    }

    private void numberOTPMove() {

        digit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    digit2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        digit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    digit3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        digit3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    digit4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        digit4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    digit5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        digit5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    digit6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}