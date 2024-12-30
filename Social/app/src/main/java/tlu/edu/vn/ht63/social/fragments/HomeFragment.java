package tlu.edu.vn.ht63.social.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import tlu.edu.vn.ht63.social.R;
import tlu.edu.vn.ht63.social.adapter.HomeAdapter;
import tlu.edu.vn.ht63.social.adapter.ViewPagerCommunityAdapter;
import tlu.edu.vn.ht63.social.model.HomeModel;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerCommunityAdapter pagerCommunityAdapter;
    HomeAdapter adapter;
    private List<HomeModel> list;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

  @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
       // init(view);
        initHome();
        addTabs();
        /*list = new ArrayList<>();
        list.add(new HomeModel("John Doe", "https://www.w3schools.com/w3images/avatar2.png", "Admin", "2 hours ago", "https://www.w3schools.com/w3images/nature.jpg", "Nature", "Nature is beautiful", "2 hours ago", 10, 5, 2));
        list.add(new HomeModel("Jane Doe", "https://www.w3schools.com/w3images/avatar6.png", "User", "3 hours ago", "https://www.w3schools.com/w3images/nature.jpg", "Nature", "Nature is beautiful", "3 hours ago", 10, 5, 2));
        adapter = new HomeAdapter(list, getContext());
        recyclerView.setAdapter(adapter);*/
    }

    private void initHome(){
        tabLayout = requireView().findViewById(R.id.tabLayout2);
        viewPager = requireView().findViewById(R.id.viewPager2);
    }
    private void addTabs(){
        tabLayout.addTab(tabLayout.newTab().setText("Bài viết"));
        tabLayout.addTab(tabLayout.newTab().setText("Bạn bè"));
        tabLayout.addTab(tabLayout.newTab().setText("Trang cá nhân"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        pagerCommunityAdapter = new ViewPagerCommunityAdapter(requireActivity().getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pagerCommunityAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        Objects.requireNonNull(tabLayout.getTabAt(0)).setText("Bài viết");
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                        tab.setText("Bài viết");
                        break;
                    case 1:
                        tab.setText("Bạn bè");
                        break;
                    case 2:
                        tab.setText("Trang cá nhân");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    /*private void init(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }*/
}