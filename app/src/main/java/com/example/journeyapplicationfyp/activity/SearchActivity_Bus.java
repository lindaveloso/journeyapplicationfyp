package com.example.journeyapplicationfyp.activity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.journeyapplicationfyp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class SearchActivity_Bus extends AppCompatActivity {

    ProgressBar progressBar;
    String url1 = "https://data.smartdublin.ie/cgi-bin/rtpi/realtimebusinformation?stopid=";
    String busstop = "";
    String url2 = "&format=json";
    String fullURL = "";
    private EditText editText;
    private Button search_button;
    BottomNavigationView bottomNavigationView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus);
        bottomNavigationView1 = findViewById(R.id.BNV);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        DisplayOption1();

        Button search_button = findViewById(R.id.search_button);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.search_button:
                        EditText editText = findViewById(R.id.editText);
                        busstop = "";
                        busstop = editText.getText().toString();
                        if (busstop != null) {
                            fullURL = url1 + busstop + url2;
                            Intent i = new Intent(SearchActivity_Bus.this, Activity_busresults.class);
                            i.putExtra("link", fullURL);
                            SearchActivity_Bus.this.startActivity(i);

                        } else {
                            Toast.makeText(getApplicationContext(), "BIG_ERROR", Toast.LENGTH_SHORT);
                        }
                        break;
                    default:
                        throw new RuntimeException("UNKNOWN_BUTTONID");
                }
            }
        });
    }

    private void DisplayOption1() {

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(
                    ContextCompat.getColor(
                            getApplicationContext(),
                            R.color.luas_purple_statusbar
                    )
            );

            window.setNavigationBarColor(
                    ContextCompat.getColor(
                            getApplicationContext(),
                            R.color.luas_purple_statusbar
                    ));
        }

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
            /*case android.R.id.home:
                onBackPressed();
                return true;*/

           /* case R.id.signmeout:
                FirebaseAuth.getInstance().signOut();
                finish();
                //startActivity(new Intent(this, MainActivityLogin.class));
                return true;*/
        }

        return super.onOptionsItemSelected(item);
    }


}

