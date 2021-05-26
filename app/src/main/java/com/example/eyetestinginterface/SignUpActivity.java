package com.example.eyetestinginterface;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;

    EditText email, password, contact;
    TextView login_link;

    Button signup_button;
    FirebaseAuth auth;

    public static String userid = null;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Map<String, String> user_details = new HashMap<>();

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        contact = findViewById(R.id.contact_number);

        login_link = findViewById(R.id.login_link);

        signup_button = findViewById(R.id.signup_button);
        auth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Intent intent = getIntent();

        try {
            String email_text = intent.getStringExtra("email");
            String password_text = intent.getStringExtra("password");

            email.setText(email_text);
            password.setText(password_text);

        } catch (Exception e) {
            e.printStackTrace();
        }

        findViewById(R.id.google_button).setOnClickListener(view -> {
            if (view.getId() == R.id.google_button) {
                signIn();
            }
        });

        signup_button.setOnClickListener(v -> {

            String email_text = email.getText().toString();
            String password_text = password.getText().toString();

            signup(email_text, password_text);
        });

        login_link.setOnClickListener(v -> {
            Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);

            Bundle bundle = new Bundle();

            String email_text = email.getText().toString();
            String password_text = password.getText().toString();

            bundle.putString("email", email_text);
            bundle.putString("password", password_text);

            intent1.putExtras(bundle);

            startActivity(intent1);
        });
    }

    private void signup(String email_text, String password_text) {
        auth.createUserWithEmailAndPassword(email_text, password_text).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {

                userid = FirebaseAuth.getInstance().getUid();

                user_details.put("userid", userid);
                user_details.put("email", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());

                SharedPreferences sharedPreferences = getSharedPreferences("VERIFIED", MODE_PRIVATE);

                boolean isVerified = sharedPreferences.getBoolean("mobile_verified", false);
                String mobile_number = sharedPreferences.getString("mobile_number", null);

                if (isVerified) {
                    user_details.put("mobile", mobile_number);
                }

                assert userid != null;
                db.collection("users").document(userid)
                        .set(user_details, SetOptions.merge())
                        .addOnSuccessListener(unused -> Log.d("TAG", "Successful document write"))
                        .addOnFailureListener(e -> Log.d("TAG", "Failed document write"));

                startActivity(new Intent(getApplicationContext(), MoreInfoActivity.class));
                Toast.makeText(SignUpActivity.this, "Signed up successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SignUpActivity.this, "Signed up failed", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("TAG", "signInWithCredential:success");

                        userid = FirebaseAuth.getInstance().getUid();

                        user_details.put("userid", userid);
                        user_details.put("email", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());
                        user_details.put("photo", String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()));
                        user_details.put("phone", Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getPhoneNumber());

                        SharedPreferences sharedPreferences = getSharedPreferences("VERIFIED", MODE_PRIVATE);

                        boolean isVerified = sharedPreferences.getBoolean("mobile_verified", false);
                        String mobile_number = sharedPreferences.getString("mobile_number", null);

                        if (isVerified) {
                            user_details.put("mobile", mobile_number);
                        }

                        assert userid != null;
                        db.collection("users").document(userid)
                                .set(user_details, SetOptions.merge())
                                .addOnSuccessListener(unused -> Log.d("TAG", "Successful document write"))
                                .addOnFailureListener(e -> Log.d("TAG", "Failed document write"));

                        startActivity(new Intent(getApplicationContext(), MoreInfoActivity.class));

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithCredential:failure", task.getException());
                        Toast.makeText(getApplicationContext(), "Invalid Login", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}