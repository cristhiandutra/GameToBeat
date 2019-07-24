package com.cristhian.gametobeat.ui.list;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GameListPageAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private List<GameListFragment> mFragmentList;

    public GameListPageAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentList.get(position).getTitle();
    }

    public void addFragment(GameListFragment fragment) {
        if (mFragmentList == null) {
            mFragmentList = new ArrayList<>();
        }
        mFragmentList.add(fragment);
    }
}
