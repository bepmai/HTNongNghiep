package tlu.edu.vn.ht63.htnongnghiep.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.ViewPagerAdapter1;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter1 pagerAdapter;
    private ImageView chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        abbTabs();
    }

    private void init(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        chat = findViewById(R.id.chat);
        chat.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
        });

    }
    private void abbTabs(){
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_dashboard));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_community));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_analysis));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_garden));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_menu));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        pagerAdapter = new ViewPagerAdapter1(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_dashboard);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                switch (tab.getPosition()){
                    case 0:
                        tab.setIcon(R.drawable.ic_dashboard);
                        break;
                    case 1:
                        tab.setIcon(R.drawable.ic_community);
                        break;
                    case 2:
                        tab.setIcon(R.drawable.ic_analysis);
                        break;
                    case 3:
                        tab.setIcon(R.drawable.ic_garden);
                        break;
                    case 4:
                        tab.setIcon(R.drawable.ic_menu);
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
}