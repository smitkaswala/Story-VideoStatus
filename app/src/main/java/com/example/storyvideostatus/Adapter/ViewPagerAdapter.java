package com.example.storyvideostatus.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.storyvideostatus.Fregment.TrendingFragment;
import com.example.storyvideostatus.Fregment.CategoryFragment;
import com.example.storyvideostatus.Fregment.NewFragment;
import com.example.storyvideostatus.Utils.Constant;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_ITEMS = 3;
    private final String[] tabTitles = new String[]{Constant.NAV_SELECTED_ITEM, "Trending Status", "Category"};
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior)   {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new NewFragment();
            case 1:
                return new TrendingFragment();
            case 2:
                return new CategoryFragment();
            default:
                return new NewFragment();
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
