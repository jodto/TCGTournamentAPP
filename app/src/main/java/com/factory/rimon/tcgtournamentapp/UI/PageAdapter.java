package com.factory.rimon.tcgtournamentapp.UI;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.factory.rimon.tcgtournamentapp.UI.Fragments.DetailsFragment;
import com.factory.rimon.tcgtournamentapp.UI.Fragments.PlayersFragment;

/**
 * Created by rimon on 5/6/2016.
 */
public class PageAdapter extends FragmentPagerAdapter {

    Intent intent;

    public PageAdapter(FragmentManager fm, Intent intent) {
        super(fm);
        this.intent = intent;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position)
        {
            case 0:
                return DetailsFragment.newInstance(intent);
            case 1:
                return PlayersFragment.newInstance(intent);
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position)
        {
            case 0:
                return "Details";
            case 1:
                return "Players";
            default:
                return null;
        }    }

    @Override
    public int getCount() {
        return 2;
    }


}
