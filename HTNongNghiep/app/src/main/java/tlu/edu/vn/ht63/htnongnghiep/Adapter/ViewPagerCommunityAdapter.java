package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerCommunityAdapter extends FragmentStatePagerAdapter {
    int noOfTabs;
    public ViewPagerCommunityAdapter(@NonNull FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTabs = noOfTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new tlu.edu.vn.ht63.htnongnghiep.Container.UI.PostFragment();
            case 1:
                return new tlu.edu.vn.ht63.htnongnghiep.Container.UI.FriendFragment();
            case 2:
                return new tlu.edu.vn.ht63.htnongnghiep.Container.UI.InfoFragment();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return noOfTabs;
    }
}
