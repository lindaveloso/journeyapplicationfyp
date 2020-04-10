package com.example.journeyapplicationfyp.activity;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.journeyapplicationfyp.Constant;
import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.fragment.LineFragment;

public class PageAdapterClass extends FragmentStatePagerAdapter {
    private int numTabs;
    private static final int[] TAB_TITLES =
            new int[]{R.string.tab_red_line, R.string.tab_green_line};
    private final Context mContext;

    public PageAdapterClass(Context context, FragmentManager fm, int n) {
        super(fm);
        numTabs = n;
        mContext = context;
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

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return numTabs;
    }


}