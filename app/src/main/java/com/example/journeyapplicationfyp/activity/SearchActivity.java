package com.example.journeyapplicationfyp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.journeyapplicationfyp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    BottomNavigationView bottomNavigationView1;
    private CardView cardView1, cardView2, cardView3;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {

                        case R.id.farenav:
                            Toast.makeText(SearchActivity.this, "Hello Javatpoint", Toast.LENGTH_SHORT).show();
                            break;


                        case R.id.timetable:
                            Intent i = new Intent(SearchActivity.this, TimetableActivity.class);
                            startActivity(i);
                            break;


                        case R.id.homehere:
                            Intent w = new Intent(SearchActivity.this, MainActivityHome.class);
                            startActivity(w);
                            break;

                        //FARE

                    }
                    return true;
                }
            };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchnav);
        bottomNavigationView1 = findViewById(R.id.BNV);
        bottomNavigationView1.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView1.setSelectedItemId(R.id.rtpi);
        cardView1 = findViewById(R.id.card1);
        cardView2 = findViewById(R.id.card2);
        cardView3 = findViewById(R.id.card3);
        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);


        //((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("RTPI");


    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.card1:
                intent = new Intent(SearchActivity.this, SearchActivity_Bus.class);
                startActivity(intent);
                break;
            case R.id.card2:
                intent = new Intent(SearchActivity.this, SearchActivity_Tram.class);
                startActivity(intent);
                break;

            case R.id.card3:
                intent = new Intent(SearchActivity.this, SearchActivity_Train.class);
                startActivity(intent);
                break;

        }
    }
}






