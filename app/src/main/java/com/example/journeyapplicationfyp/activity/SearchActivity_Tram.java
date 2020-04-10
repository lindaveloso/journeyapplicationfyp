package com.example.journeyapplicationfyp.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.journeyapplicationfyp.Constant;
import com.example.journeyapplicationfyp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class SearchActivity_Tram extends AppCompatActivity {

    private final String LOG_TAG = SearchActivity_Tram.class.getSimpleName();
    ViewPager viewpager;
    TabLayout tablayout;
    BottomNavigationView bottomNavigationView1;
    Toolbar toolbar;

    public SearchActivity_Tram() {
        /* Required empty public constructor. */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tram);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomNavigationView1 = findViewById(R.id.BNV);

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











