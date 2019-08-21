package com.bigohealth.ui.homeactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.bigohealth.R;
import com.bigohealth.ui.temp.BlankFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, new HomeFragment())
                .commit();

        BottomNavigationView navigationView = findViewById(R.id.bottom_nav_view);
        navigationView.setOnNavigationItemSelectedListener(menuItem -> {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.nav_bar_home:
                    fragment = new HomeFragment();
                    break;
                case R.id.nav_bar_dashboard:
                    fragment = new BlankFragment();
                    break;
                case R.id.nav_bar_notifications:
                    fragment = new BlankFragment();
                    break;
                case R.id.nav_bar_reminder:
                    fragment = new BlankFragment();
                    break;
                case R.id.nav_bar_feed:
                    fragment = new BlankFragment();
                    break;
                default:
                    fragment = null;
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame, fragment)
                    .commit();

            return true;
        });
    }
}
