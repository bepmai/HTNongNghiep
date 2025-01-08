package tlu.edu.vn.ht63.htnongnghiep.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import tlu.edu.vn.ht63.htnongnghiep.Component.Subcomponent.MenuFragment;
import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Container.UI.GardenFragment;
import tlu.edu.vn.ht63.htnongnghiep.Container.UI.ShopFragment;
import tlu.edu.vn.ht63.htnongnghiep.Container.UI.UserRights;
import tlu.edu.vn.ht63.htnongnghiep.Container.UI.home;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.ViewPagerAdapter1;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter1 pagerAdapter;
    private ImageView chat;
    private EditText searchView;
    private int barClick;
    private ImageButton homeBar, communityBar, shopBar, analysisBar, farmBar, userBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        abbTabs();
        searchView = findViewById(R.id.searchBar);
        searchView.clearFocus();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filterList(newText);
//                return true;
//            }
//        });
        barClick= R.id.community;
        homeBar = findViewById(R.id.home);
        communityBar = findViewById(R.id.community);
        shopBar = findViewById(R.id.shop);
        analysisBar = findViewById(R.id.analysis);
        farmBar = findViewById(R.id.farm);
        userBar = findViewById(R.id.user);
        communityBar.setColorFilter(getResources().getColor(R.color.active_bar));
        homeBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thay đổi màu nền của nút khi click vào
                unsetColorBar();
                unsetAdapter();
                homeBar.setColorFilter(getResources().getColor(R.color.active_bar)); // Màu bạn muốn
                barClick = R.id.home;
                Fragment homeFragment = new home();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main, homeFragment);
                transaction.commit();
            }
        });

        communityBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsetColorBar();
                communityBar.setColorFilter(getResources().getColor(R.color.active_bar));
                barClick = R.id.community;
                abbTabs();
                FrameLayout frameLayout = findViewById(R.id.main);
                frameLayout.removeAllViews();
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent);
//                Fragment homeFragment = new HomeFragment();
//                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                transaction.replace(R.id.main, homeFragment);
//                transaction.commit();
            }
        });

        shopBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsetColorBar();
                unsetAdapter();
                shopBar.setColorFilter(getResources().getColor(R.color.active_bar));
                barClick = R.id.shop;

                Fragment shopFragment = new ShopFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main, shopFragment);
                transaction.commit();
            }
        });


        analysisBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsetColorBar();
                unsetAdapter();
                analysisBar.setColorFilter(getResources().getColor(R.color.active_bar));
                Intent analysisIntent = new Intent(MainActivity.this, AnalysisActivity.class);
                startActivity(analysisIntent);
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        unsetColorBar();
                        if (barClick == R.id.home) {
                            homeBar.setColorFilter(getResources().getColor(R.color.active_bar));
                        } else if (barClick == R.id.user) {
                            userBar.setColorFilter(getResources().getColor(R.color.active_bar));
                        } else if (barClick == R.id.farm) {
                            farmBar.setColorFilter(getResources().getColor(R.color.active_bar));
                        } else if (barClick == R.id.shop) {
                            shopBar.setColorFilter(getResources().getColor(R.color.active_bar));
                        } else if (barClick == R.id.community){
                            communityBar.setColorFilter(getResources().getColor(R.color.active_bar));
                        }
                    }
                },2000);

//                barClick = R.id.analysis;
//                finish();
            }
        });

        farmBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsetColorBar();
                unsetAdapter();
                farmBar.setColorFilter(getResources().getColor(R.color.active_bar));
                barClick = R.id.farm;

                Fragment garden = new GardenFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main, garden);
                transaction.commit();
            }
        });

        userBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsetColorBar();
                unsetAdapter();
                userBar.setColorFilter(getResources().getColor(R.color.active_bar));
                barClick = R.id.user;

                Fragment userRights = new UserRights();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main, userRights);
                transaction.commit();
            }
        });
    }

    private void filterList(String newText) {


    }

    private void init(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.viewPager);
        chat = findViewById(R.id.chat);
        chat.setOnClickListener(v->{
            Intent intent = new Intent(MainActivity.this, ChatActivity.class);
            startActivity(intent);
        });
    }
    private void abbTabs(){
        pagerAdapter = new ViewPagerAdapter1(getSupportFragmentManager());
        pagerAdapter.noOfTabs = 1;

        viewPager.setAdapter(pagerAdapter);

    }
    private void unsetAdapter(){
        viewPager.setAdapter(null);
    }
    private void unsetColorBar() {
        String color = "#777B84";
        int colorInt = Color.parseColor(color);
        homeBar.setColorFilter(colorInt);
        analysisBar.setColorFilter(colorInt);
        shopBar.setColorFilter(colorInt);
        communityBar.setColorFilter(colorInt);
        userBar.setColorFilter(colorInt);
        farmBar.setColorFilter(colorInt);
    }
}