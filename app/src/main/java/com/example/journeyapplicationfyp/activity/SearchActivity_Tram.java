package com.example.journeyapplicationfyp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.journeyapplicationfyp.Constant;
import com.example.journeyapplicationfyp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class SearchActivity_Tram extends AppCompatActivity {

    private final String LOG_TAG = SearchActivity_Tram.class.getSimpleName();
    ViewPager viewpager;
    TabLayout tablayout;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView1;
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    final int previousItem = bottomNavigationView1.getSelectedItemId();
                    final int nextItem = menuItem.getItemId();
                    Fragment selectedFragment = null;
                    Intent intent;

                    if (previousItem != nextItem) {
                        switch (nextItem) {
                            case R.id.homehere:
                                intent = new Intent(SearchActivity_Tram.this, MainActivityHome.class);
                                startActivity(intent);
                                break;

                            case R.id.timetable:
                                intent = new Intent(SearchActivity_Tram.this, TimetableActivity.class);
                                startActivity(intent);
                                break;

                            case R.id.farenav:
                                Toast.makeText(SearchActivity_Tram.this, "HELLOOO WORLD", Toast.LENGTH_SHORT).show();
                                /* selectedFragment = new Fragment_Faresv();
                                getSupportFragmentManager().beginTransaction().replace(R.id.Frame_container,
                                        selectedFragment).commit();*/
                                break;

                        }

                    }


                    return true;
                }
            };


    public SearchActivity_Tram() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tram);
        bottomNavigationView1 = findViewById(R.id.BNV);
        bottomNavigationView1.setSelectedItemId(R.id.rtpi);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final ViewPager viewPager = findViewById(R.id.viewpager);
        final TabLayout tabLayout = findViewById(R.id.tablayout);

        if (tabLayout != null && viewPager != null) {
            tabLayout.addTab(
                    tabLayout.newTab().setTag(Constant.RED_LINE).setText(
                            getString(R.string.tab_red_line)
                    )
            );
            tabLayout.addTab(
                    tabLayout.newTab().setTag(Constant.GREEN_LINE).setText(
                            getString(R.string.tab_green_line)
                    )
            );
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            tabLayout.setBackgroundColor(
                    ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)
            );

            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });

            final PageAdapterClass pagerAdapter = new PageAdapterClass(
                    getApplicationContext(),
                    getSupportFragmentManager(),
                    tabLayout.getTabCount()
            );

            viewPager.setAdapter(pagerAdapter);
            viewPager.addOnPageChangeListener(
                    new TabLayout.TabLayoutOnPageChangeListener(tabLayout)
            );

        } else {
            Log.wtf(LOG_TAG, "tabLayout or viewPager is null.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolsbarmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ((item.getItemId())) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }


}











