package com.example.eyetestinginterface;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Home extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        chipNavigationBar = findViewById(R.id.bottom_nav_bar);

        chipNavigationBar.setItemSelected(R.id.nav_test, true);

        Log.d("TAG : i", String.valueOf(chipNavigationBar.getSelectedItemId()));

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

            }
        });

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {

            @Override
            public void onItemSelected(int i) {

                Fragment selectedFragment = null;

                if (chipNavigationBar.getSelectedItemId() == R.id.nav_find_doctor) {
                    Toast.makeText(getApplicationContext(), "Add Selected", Toast.LENGTH_SHORT).show();
                    Log.d("TAG : i", String.valueOf(i));

                    selectedFragment = new FindDoctorFragment();
                }

                if (chipNavigationBar.getSelectedItemId() == R.id.nav_test) {
                    Toast.makeText(getApplicationContext(), "Search Selected", Toast.LENGTH_SHORT).show();
                    Log.d("TAG : i", String.valueOf(i));

                    selectedFragment = new TestFragment();
                }

                if (chipNavigationBar.getSelectedItemId() == R.id.nav_profile) {
                    Toast.makeText(getApplicationContext(), "Task Selected", Toast.LENGTH_SHORT).show();
                    Log.d("TAG : i", String.valueOf(i));

                    selectedFragment = new ProfileFragment();
                }

                assert selectedFragment != null;
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, selectedFragment).commit();
            }
        });

    }
}