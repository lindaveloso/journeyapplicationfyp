package com.example.journeyapplicationfyp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.journeyapplicationfyp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityHome extends AppCompatActivity {

    BottomNavigationView bottomNavigationView1;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;


                    switch (menuItem.getItemId()) {

                        case R.id.homenav:
                            selectedFragment = new Fragment_Homev();
                            break;

                        case R.id.searchnav:
                            selectedFragment = new Fragment_Searchv();
                            break;

                        case R.id.routenav:
                            Intent intent = new Intent(MainActivityHome.this, MapActivity_Routesnv.class);
                            startActivity(intent);
                            finish();
                            return true;

                        case R.id.farenav:
                            selectedFragment = new Fragment_Faresv();

                            break;
                        case R.id.profilenav:
                            selectedFragment = new Fragment_Profilev();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.Frame_container,
                            selectedFragment).commit();
                    return true;


                }


            };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView1 = findViewById(R.id.BNV);
        bottomNavigationView1.setOnNavigationItemSelectedListener(navListener);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar0);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_exit, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ((item.getItemId())) {
            case R.id.signmeout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this, MainActivityLogin.class));
                break;


        }
        return true;
    }
}
