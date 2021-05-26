package com.example.eyetestinginterface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MoreInfoActivity extends AppCompatActivity {

    Button continue_button;
    TextInputEditText mobile_number, current_address;
    TextInputLayout mobile_layout, address_layout;
    private String mobile, address;
    TextView header;
    ProgressBar progressBar;

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPreferences = getSharedPreferences("VERIFIED", MODE_PRIVATE);

        boolean isVerified = sharedPreferences.getBoolean("mobile_verified", false);
        String mobile_number = sharedPreferences.getString("mobile_number", null);

        if (isVerified) {
            startActivity(new Intent(getApplicationContext(), Home.class));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        continue_button = findViewById(R.id.continue_button);
        mobile_number = findViewById(R.id.mobile_number);
        current_address = findViewById(R.id.address);
        header = findViewById(R.id.more_info_header);

        mobile_layout = findViewById(R.id.filled_mobile);
        address_layout = findViewById(R.id.filled_address);

        progressBar = findViewById(R.id.progressBar);

        header.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Home.class)));

        continue_button.setOnClickListener(v -> {

            progressBar.setVisibility(View.VISIBLE);

            mobile_layout.setErrorEnabled(false);
            address_layout.setErrorEnabled(false);

            mobile = Objects.requireNonNull(mobile_number.getText()).toString();
            address = Objects.requireNonNull(current_address.getText()).toString();


            if (mobile.equals("")) {
                mobile_layout.setErrorEnabled(true);
                mobile_layout.setErrorContentDescription("Enter your mobile number");
            }

            if (address.equals("")) {
                address_layout.setErrorEnabled(true);
            }

            if (!(mobile.equals("") || address.equals(""))) {

                PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + mobile,
                        60,
                        TimeUnit.SECONDS,
                        MoreInfoActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(MoreInfoActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
                                intent.putExtra("phone", mobile);
                                intent.putExtra("otp", s);
                                intent.putExtra("address", address);

                                startActivity(intent);
                            }
                        });
            }
        });
    }
}