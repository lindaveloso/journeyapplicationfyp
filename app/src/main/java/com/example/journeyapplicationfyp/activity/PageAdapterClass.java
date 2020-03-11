package com.example.journeyapplicationfyp.activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.journeyapplicationfyp.Constant;
import com.example.journeyapplicationfyp.fragment.LineFragment;

public class PageAdapterClass extends FragmentStatePagerAdapter {
    private int numTabs;

    public PageAdapterClass(FragmentManager fm, int n) {
        super(fm);
        numTabs = n;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return LineFragment.newInstance(Constant.RED_LINE);

            case 1:
                return LineFragment.newInstance(Constant.GREEN_LINE);

            default:

                return null;
        }
    }

    @Override
    public int getCount() {
        return numTabs;
    }


}