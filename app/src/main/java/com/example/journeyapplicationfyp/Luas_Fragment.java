package com.example.journeyapplicationfyp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.journeyapplicationfyp.activity.PageAdapterClass;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class Luas_Fragment extends Fragment {

    public Luas_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_luas_, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Settings(view);

    }

    private void Settings(View view) {
        final ViewPager viewPager = view.findViewById(R.id.viewpager);
        final TabLayout tabLayout = view.findViewById(R.id.tablayout);

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
                    ContextCompat.getColor(requireContext(), R.color.colorPrimary)
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
                    requireContext(),
                    requireActivity().getSupportFragmentManager(),
                    tabLayout.getTabCount()
            );

            viewPager.setAdapter(pagerAdapter);
            viewPager.addOnPageChangeListener(
                    new TabLayout.TabLayoutOnPageChangeListener(tabLayout)
            );

        } else {

        }
    }

}

