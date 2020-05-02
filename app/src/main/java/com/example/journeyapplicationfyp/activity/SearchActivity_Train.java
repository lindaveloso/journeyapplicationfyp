package com.example.journeyapplicationfyp.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.journeyapplicationfyp.R;
import com.google.android.material.tabs.TabLayout;

public class SearchActivity_Train extends Fragment {

    Toolbar toolsbarmenu;
    ViewPager viewpager;
    TabLayout tablayout;
    FragmentManager childFragmentManager;

    public SearchActivity_Train() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_train_master, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Settings(view);

    }

    private void Settings(View view) {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = requireActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(
                    ContextCompat.getColor(
                            requireActivity(),
                            R.color.system
                    )
            );

            window.setNavigationBarColor(
                    ContextCompat.getColor(
                            requireActivity(),
                            R.color.system
                    ));
        }
        PageAdapterClass2 tabsPagerAdapter = new PageAdapterClass2(requireActivity(), requireActivity().getSupportFragmentManager());

        final ViewPager viewPager = view.findViewById(R.id.viewpager);
        viewPager.setAdapter(tabsPagerAdapter);

        final TabLayout tabs = view.findViewById(R.id.tablayout);
        tabs.setupWithViewPager(viewPager);


        tabs.setBackgroundColor(
                ContextCompat.getColor(requireActivity(), R.color.luas_purple)
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

        final PageAdapterClass2 pagerAdapter2 = new PageAdapterClass2(requireActivity(),
                requireActivity().getSupportFragmentManager()

        );

        viewPager.setAdapter(pagerAdapter2);
        viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabs)
        );


    }


}



