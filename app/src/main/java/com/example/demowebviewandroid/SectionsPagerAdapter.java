package com.example.demowebviewandroid;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    int[] mPages;
    String[] mTitles;

    public SectionsPagerAdapter(FragmentManager fm, int[] pages, String[] titles) {
        super(fm);
        mPages = pages;
        mTitles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
