package com.example.giftsandroid;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.giftsandroid.Fragments.GiftsListFragment;
import com.example.giftsandroid.Fragments.LikedGiftsFragment;

public class SectionsPagerAdapret extends FragmentPagerAdapter {

    public SectionsPagerAdapret(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new GiftsListFragment();
            case 1 :
                return new LikedGiftsFragment();
        }

        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Список подарков";
            case 1:
                return "Понравившиеся подарки";
        }
        return null;
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;
    }
}
