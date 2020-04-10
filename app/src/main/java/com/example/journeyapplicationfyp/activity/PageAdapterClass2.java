package com.example.journeyapplicationfyp.activity;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.journeyapplicationfyp.R;
import com.example.journeyapplicationfyp.fragment.Dart_Fragment;
import com.example.journeyapplicationfyp.fragment.Mainline_Fragment;
import com.example.journeyapplicationfyp.fragment.Suburban_Fragment;

public class PageAdapterClass2 extends FragmentStatePagerAdapter {
    @StringRes
    private static final int[] TAB_TITLES =
            new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;
    private int numTabs;

    public PageAdapterClass2(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Mainline_Fragment();

            case 1:
                return new Dart_Fragment();

            case 2:
                return new Suburban_Fragment();

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