package tlu.edu.vn.ht63.social.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import tlu.edu.vn.ht63.social.fragments.SearchFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int noOfTabs;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTabs = noOfTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new tlu.edu.vn.ht63.social.fragments.HomeFragment();
            case 1:
                return new tlu.edu.vn.ht63.social.fragments.AnalysisFragment();
            case 2:
                return new tlu.edu.vn.ht63.social.fragments.GardenFragment();
            case 3:
                return new SearchFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
