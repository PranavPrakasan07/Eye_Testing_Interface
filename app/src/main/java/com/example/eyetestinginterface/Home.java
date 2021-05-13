package com.example.eyetestinginterface;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Home extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    Map<String, Object> details = new Map<String, Object>() {

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(@Nullable @org.jetbrains.annotations.Nullable Object key) {
            return false;
        }

        @Override
        public boolean containsValue(@Nullable @org.jetbrains.annotations.Nullable Object value) {
            return false;
        }

        @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        public Object get(@Nullable @org.jetbrains.annotations.Nullable Object key) {
            return null;
        }

        @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        public Object put(String key, Object value) {
            return null;
        }

        @Nullable
        @org.jetbrains.annotations.Nullable
        @Override
        public Object remove(@Nullable @org.jetbrains.annotations.Nullable Object key) {
            return null;
        }

        @Override
        public void putAll(@NonNull @NotNull Map<? extends String, ?> m) {

        }

        @Override
        public void clear() {

        }

        @NonNull
        @NotNull
        @Override
        public Set<String> keySet() {
            return null;
        }

        @NonNull
        @NotNull
        @Override
        public Collection<Object> values() {
            return null;
        }

        @NonNull
        @NotNull
        @Override
        public Set<Entry<String, Object>> entrySet() {
            return null;
        }
    };

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        chipNavigationBar = findViewById(R.id.bottom_nav_bar);

        chipNavigationBar.setItemSelected(R.id.nav_test, true);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));


        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Log.d("TAG", document.getId() + " => " + document.getData());

                                details = (document.getData());

                                if(document.getId().equals(FirebaseAuth.getInstance().getUid())){
                                    UserDetails.setUser_email(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());
                                    UserDetails.setUser_name(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getDisplayName());
//                                    UserDetails.setUser_phone(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).());
                                    UserDetails.setUser_id(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()));
                                }
                            }

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        try {
            Bundle extras = getIntent().getExtras();
            String toOpen = extras.getString("toOpen");

            if(toOpen.equals("Test")){
                chipNavigationBar.setItemSelected(R.id.nav_test, true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new TestFragment()).commit();
            }

            if(toOpen.equals("Maps")){
                chipNavigationBar.setItemSelected(R.id.nav_find_doctor, true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MapsFragment()).commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("TAG : i", String.valueOf(chipNavigationBar.getSelectedItemId()));

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {

            @Override
            public void onItemSelected(int i) {

                Fragment selectedFragment = null;

                if (chipNavigationBar.getSelectedItemId() == R.id.nav_find_doctor) {
                    selectedFragment = new MapsFragment();
                }

                if (chipNavigationBar.getSelectedItemId() == R.id.nav_test) {
                    selectedFragment = new TestFragment();
                }

                if (chipNavigationBar.getSelectedItemId() == R.id.nav_profile) {
                    selectedFragment = new ProfileFragment();
                }

                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selectedFragment).commit();
            }
        });

    }
}