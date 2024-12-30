package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import tlu.edu.vn.ht63.htnongnghiep.Container.UI.SearchFragment;

public class ViewPagerAdapter1 extends FragmentStatePagerAdapter {
    int noOfTabs;

    public ViewPagerAdapter1(@NonNull FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTabs = noOfTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new tlu.edu.vn.ht63.htnongnghiep.Container.UI.HomeFragment();
            case 1:
                return new tlu.edu.vn.ht63.htnongnghiep.Container.UI.AnalysisFragment();
            case 2:
                return new tlu.edu.vn.ht63.htnongnghiep.Container.UI.GardenFragment();
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
