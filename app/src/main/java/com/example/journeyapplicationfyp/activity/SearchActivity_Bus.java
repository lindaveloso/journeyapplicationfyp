/*
package com.example.journeyapplicationfyp.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.fragment.TimetableFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class SearchActivity_Bus extends AppCompatActivity {

    ProgressDialog progressDialog;
    String url1 = "https://data.smartdublin.ie/cgi-bin/rtpi/realtimebusinformation?stopid=";
    String busstop = "";
    String url2 = "&format=json";
    String fullURL = "";
    private EditText editText;
    private Button search_button;
    BottomNavigationView bottomNavigationView1;

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    final int previousItem = bottomNavigationView1.getSelectedItemId();
                    final int nextItem = item.getItemId();

                    switch (item.getItemId()) {
                        case R.id.homehere:
                            Intent intent = new Intent(SearchActivity_Bus.this, MainActivityHome.class);
                            startActivity(intent);
                            break;

                        case R.id.timetable:
                            Intent i = new Intent(SearchActivity_Bus.this, TimetableFragment.class);
                            startActivity(i);
                            break;

                    }
                    return true;
                }
            };
    //Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_master);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        bottomNavigationView1 = findViewById(R.id.BNV);
        bottomNavigationView1.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView1.setSelectedItemId(R.id.rtpi);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        DisplayOption1();
        Button search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationDetails()) {
                    fullURL = url1 + busstop + url2;
                    Intent i = new Intent(SearchActivity_Bus.this, SearchActivityBus_results.class);
                    i.putExtra("link", fullURL);
                    SearchActivity_Bus.this.startActivity(i);
                }
            }
        });


    }


    private boolean validationDetails() {
        EditText editText = findViewById(R.id.editText);
        busstop = "";
        busstop = editText.getText().toString();

        if (editText.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Empty Field for Dublin Bus Stop", Toast.LENGTH_SHORT).show();
            return false;
        } else if (editText.getText().toString().equals("0")) {
            Toast.makeText(getApplicationContext(), "Not a valid Dublin Bus stop", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void DisplayOption1() {

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

*/
