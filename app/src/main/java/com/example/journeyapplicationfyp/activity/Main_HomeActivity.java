package com.example.journeyapplicationfyp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.fragment.SearchFragment;
import com.example.journeyapplicationfyp.fragment.TimetableFragment;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main_HomeActivity extends AppCompatActivity {
    Toolbar toolsbarmenu;
    BottomNavigationView navigation;
    final Fragment fragment1 = new MainActivityMap();
    final Fragment fragment2 = new SearchFragment();
    final Fragment fragment3 = new TimetableFragment();
    // final Fragment fragment4 = new Fragment_Faresv(); //FARES
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.timetable:
                    fm.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                    return true;

               /* case R.id.rtpi:
                    fm.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    return true;*/

                case R.id.homehere:
                    fm.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Settings();
        BottomNavigationView navigation = findViewById(R.id.BNV);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BadgeDrawable badge = navigation.getOrCreateBadge(R.id.rtpi);
        badge.setVisible(true);

        fm.beginTransaction().add(R.id.main_container, fragment2, "3").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.main_container, fragment3, "2").hide(fragment3).commit();
        // fm.beginTransaction().add(R.id.main_container, fragment1, "1").hide(fragment1).commit(); FARES
        fm.beginTransaction().add(R.id.main_container, fragment1, "1").commit();
        getSupportActionBar().setElevation(0);


    }

    private void Settings() {

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(
                    ContextCompat.getColor(
                            getApplicationContext(),
                            R.color.system
                    )
            );

            window.setNavigationBarColor(
                    ContextCompat.getColor(
                            getApplicationContext(),
                            R.color.system
                    ));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolsbarmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ((item.getItemId())) {
            case R.id.refresh:
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;

        }
        return true;
    }
}
