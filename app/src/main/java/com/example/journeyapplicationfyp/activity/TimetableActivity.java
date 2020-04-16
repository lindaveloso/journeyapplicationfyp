package com.example.journeyapplicationfyp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.journeyapplicationfyp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class TimetableActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView1;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.homehere:
                            Intent intent = new Intent(TimetableActivity.this, MainActivityHome.class);
                            startActivity(intent);
                            break;

                        case R.id.rtpi:
                            Intent i = new Intent(TimetableActivity.this, SearchActivity.class);
                            startActivity(i);
                            break;

                        //FARE
                            /* case R.id.rtpi:
                            Intent i = new Intent(TimetableActivity.this, SearchActivity.class);
                            startActivity(i);
                            break;*/

                    }
                    return true;
                }

            };

    public TimetableActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetables);
        bottomNavigationView1 = findViewById(R.id.BNV);
        bottomNavigationView1.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView1.setSelectedItemId(R.id.timetable);
        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("RTPI");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolsbarmenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}