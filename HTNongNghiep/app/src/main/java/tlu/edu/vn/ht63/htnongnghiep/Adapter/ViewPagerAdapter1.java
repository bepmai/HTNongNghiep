package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter1 extends FragmentStatePagerAdapter {
    public int noOfTabs;

    public ViewPagerAdapter1(@NonNull FragmentManager fm) {
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new tlu.edu.vn.ht63.htnongnghiep.Container.UI.HomeFragment();
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
