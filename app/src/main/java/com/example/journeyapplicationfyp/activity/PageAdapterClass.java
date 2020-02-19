package com.example.journeyapplicationfyp.activity;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.journeyapplicationfyp.R;

public class PageAdapterClass extends FragmentStatePagerAdapter {

    private static final int[] TAB_TITLES =
            new int[]{R.string.text_1, R.string.text_2, R.string.text_3};
    private final Context mContext;

    public PageAdapterClass(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                return Tab1.newInstance();
            case 1:
                return Tab2.newInstance();

            case 2:
                return Tab3.newInstance();

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
        return 3;
    }


}