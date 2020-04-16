package com.example.journeyapplicationfyp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.journeyapplicationfyp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class SearchActivity_Train extends AppCompatActivity {

    ViewPager viewpager;
    TabLayout tablayout;
    FragmentManager childFragmentManager;
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
                                intent = new Intent(SearchActivity_Train.this, MainActivityHome.class);
                                startActivity(intent);
                                finish();
                                break;

                            case R.id.rtpi:
                                //Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
                                //selectedFragment = new SearchActivity();
                                // getSupportFragmentManager().beginTransaction().replace(R.id.content, selectedFragment).commit();
                                //getSupportFragmentManager().beginTransaction().replace(R.id.Frame_container,
                                //  selectedFragment).commit();
                                break;

                            case R.id.timetable:
                                Toast.makeText(SearchActivity_Train.this, "HELLOOO WORLD", Toast.LENGTH_SHORT).show();

                                //selectedFragment = new TimetableActivity();

                               /* selectedFragment.getChildFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.Frame_container,selectedFragment)
                                        .commitNowAllowingStateLoss();


                             /*   selectedFragment = new TimetableActivity();
                                getSupportFragmentManager().beginTransaction().replace(R.id.Frame_container,
                                        selectedFragment).commit();


                             /*    getSupportFragmentManager().beginTransaction().replace(R.id.content, selectedFragment).commit();
                                getSupportFragmentManager().beginTransaction().replace(R.id.Frame_container,
                                 selectedFragment).commit();

                             /*   intent = new Intent(SearchActivity_Train.this, TimetableActivity.class);
                                startActivity(intent);
                                finish();*/
                                break;

                            case R.id.farenav:
                                Toast.makeText(SearchActivity_Train.this, "HELLOOO WORLD", Toast.LENGTH_SHORT).show();

                               /* selectedFragment = new Fragment_Faresv();
                                getSupportFragmentManager().beginTransaction().replace(R.id.Frame_container,
                                        selectedFragment).commit();*/
                                break;
                        }
                    }
                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
        bottomNavigationView1 = findViewById(R.id.BNV);
        bottomNavigationView1.setOnNavigationItemSelectedListener(navListener);
        Settings();
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
        PageAdapterClass2 tabsPagerAdapter = new PageAdapterClass2(this, getSupportFragmentManager());

        final ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(tabsPagerAdapter);

        final TabLayout tabs = findViewById(R.id.tablayout);
        tabs.setupWithViewPager(viewPager);


        tabs.setBackgroundColor(
                ContextCompat.getColor(getApplicationContext(), R.color.luas_purple)
        );
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        final PageAdapterClass2 pagerAdapter2 = new PageAdapterClass2(this,
                getSupportFragmentManager()

        );

        viewPager.setAdapter(pagerAdapter2);
        viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabs)
        );


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
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
