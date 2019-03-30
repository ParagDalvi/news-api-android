package com.example.news;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerAdapter extends FragmentStatePagerAdapter {

    int mTabCount;

    public PagerAdapter(FragmentManager fm, int tabCount)
    {
        super(fm);
        this.mTabCount = tabCount;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:
                HeadlinesFragment headlinesFragment = new HeadlinesFragment();
                return headlinesFragment;
            case 1:
                TechnologyFragment technologyFragment = new TechnologyFragment();
                return technologyFragment;
            case 2:
                SportsFragment sportsFragment = new SportsFragment();
                return sportsFragment;
            case 3:
                FinanceFragment financeFragment = new FinanceFragment();
                return financeFragment;
            case 4:
                HealthFragment healthFragment = new HealthFragment();
                return healthFragment;
            case 5:
                ScienceFragment scienceFragment = new ScienceFragment();
                return scienceFragment;
            case 6:
                BollywoodFragment bollywoodFragment = new BollywoodFragment();
                return bollywoodFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

}
