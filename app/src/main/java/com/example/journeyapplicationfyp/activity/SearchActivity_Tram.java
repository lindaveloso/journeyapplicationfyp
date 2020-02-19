package com.example.journeyapplicationfyp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.journeyapplicationfyp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class SearchActivity_Tram extends AppCompatActivity {

    ViewPager viewpager;
    TabLayout tablayout;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tram);
        //ToolBar
         Toolbar toolbar = findViewById(R.id.toolbar0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        PageAdapterClass pageAdapter = new PageAdapterClass(this, getSupportFragmentManager());

        ViewPager viewpager = findViewById(R.id.viewpager);
        viewpager.setAdapter(pageAdapter);

        TabLayout tabs = findViewById(R.id.tablayout);
        tabs.setupWithViewPager(viewpager);
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewpager));


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











