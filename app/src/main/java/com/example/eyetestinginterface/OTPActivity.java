package com.example.eyetestinginterface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    TextView verify_button;

    private EditText digit1, digit2, digit3, digit4, digit5, digit6;
    private String d1, d2, d3, d4, d5, d6;

    String userid = "";

    @Override
    protected void onStart() {
        super.onStart();

        if (LoginActivity.userid != null) {
            userid = LoginActivity.userid;
        } else {
            userid = SignUpActivity.userid;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);

        String mobile = getIntent().getStringExtra("phone");
        String address = getIntent().getStringExtra("address");

        final String[] otp = {getIntent().getStringExtra("otp")};

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

            if (otp[0] != null) {

                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(otp[0], otp_entered);

                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d("TAG-true", otp_entered);

                                FirebaseFirestore db = FirebaseFirestore.getInstance();

                                Map<String, String> user_details = new HashMap<>();

                                user_details.put("address", address);
                                user_details.put("mobile", mobile);



                                db.collection("users").document(userid)
                                        .set(user_details, SetOptions.merge())
                                        .addOnSuccessListener(unused -> {
                                            Log.d("TAG", "Added successfully");
                                            SharedPreferences sharedPreferences = getSharedPreferences("VERIFIED", MODE_PRIVATE);
                                            sharedPreferences.edit().putBoolean("mobile_verified", true).apply();
                                            sharedPreferences.edit().putString("mobile_number", mobile).apply();
                                        })
                                        .addOnFailureListener(e -> Log.d("TAG", "Add failed!"));

                                Intent intent = new Intent(getApplicationContext(), Home.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            } else {
                                Log.d("TAG-false", otp_entered);
                                Toast.makeText(OTPActivity.this, "Incorrect OTP", Toast.LENGTH_SHORT).show();
                            }
                        });

            } else {
                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
            }

            numberOTPMove();

        });

        TextView resendLabel = findViewById(R.id.resend_button);

        resendLabel.setOnClickListener(v -> PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + getIntent().getStringExtra("mobile"),
                60,
                TimeUnit.SECONDS,
                OTPActivity.this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String new_otp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                        otp[0] = new_otp;

//                                Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
//                                intent.putExtra("mobile", mobile);
//                                intent.putExtra("otp", otp);
//                                startActivity(intent);
                    }
                }));

    }

    private void numberOTPMove() {

        digit1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().trim().isEmpty()) {
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
                if (!s.toString().trim().isEmpty()) {
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
                if (!s.toString().trim().isEmpty()) {
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
                if (!s.toString().trim().isEmpty()) {
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
                if (!s.toString().trim().isEmpty()) {
                    digit6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}