package com.example.journeyapplicationfyp.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.util.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Main_HomeActivity extends AppCompatActivity {
    Toolbar toolsbarmenu;
    BottomNavigationView navigation;
    private NavController navController;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Settings();
        navigation = findViewById(R.id.BNV);
        initializeViews();
        sessionManager = new SessionManager();
        getSupportActionBar().setElevation(0);


    }

    private void initializeViews() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.rtpi_nav_host);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(navigation, navHostFragment.getNavController());
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.map_search_fragment, R.id.rtpi_nav_fragment).build();
        NavigationUI.setupActionBarWithNavController(this, navHostFragment.getNavController(), appBarConfiguration);
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
            case R.id.favourites:
                sessionManager.setFavourites(sessionManager.getStationNme());
                Toast.makeText(this, "Added to Favourites", Toast.LENGTH_SHORT).show();
                break;
            case R.id.refresh:

        }
        return super.onOptionsItemSelected(item);
    }

  /*  @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }*/



}
