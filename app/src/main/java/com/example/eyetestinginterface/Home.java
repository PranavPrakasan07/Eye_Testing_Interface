package com.example.eyetestinginterface;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Home extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    public static String userid = null;

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
        setContentView(R.layout.activity_home);

        chipNavigationBar = findViewById(R.id.bottom_nav_bar);
        chipNavigationBar.setItemSelected(R.id.nav_test, true);

        try {
            Bundle extras = getIntent().getExtras();
            String toOpen = extras.getString("toOpen");

            if (toOpen.equals("Test")) {
                chipNavigationBar.setItemSelected(R.id.nav_test, true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new TestFragment()).commit();
            }

            if (toOpen.equals("Maps")) {
                chipNavigationBar.setItemSelected(R.id.nav_find_doctor, true);
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new MapsFragment()).commit();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        chipNavigationBar.setOnItemSelectedListener(i -> {

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
        });

    }
}