package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import tlu.edu.vn.ht63.htnongnghiep.Adapter.ViewPagerAdapter1;
import tlu.edu.vn.ht63.htnongnghiep.Container.UI.GardenFragment;
import tlu.edu.vn.ht63.htnongnghiep.Container.UI.HomeFragment;
import tlu.edu.vn.ht63.htnongnghiep.Container.UI.ShopFragment;
import tlu.edu.vn.ht63.htnongnghiep.Container.UI.UserRights;
import tlu.edu.vn.ht63.htnongnghiep.Container.UI.home;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class HomeActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ViewPagerAdapter1 pagerAdapter;
    private ImageView chat;
    private EditText searchView;
    private ImageButton homeBar, communityBar, shopBar, analysisBar, farmBar, userBar;
    private int barClick = R.id.home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
//            // Tạo một Fragment mới
//            Fragment homeFragment = new home();
//
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.replace(R.id.main, homeFragment);
//            transaction.commit();
            viewPager = findViewById(R.id.viewPager);
            chat = findViewById(R.id.chat);
            chat.setOnClickListener(v->{
                Intent intent = new Intent(HomeActivity.this, ChatActivity.class);
                startActivity(intent);
            });
            pagerAdapter = new ViewPagerAdapter1(getSupportFragmentManager());
            pagerAdapter.noOfTabs = 1;


            String openFragment = getIntent().getStringExtra("open_fragment");
            if ("garden".equals(openFragment)) {
                Fragment gardenFragment = new GardenFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, gardenFragment)
                        .commit();
            } else if ("userrights".equals(openFragment)) {
                Fragment userrightsFragment = new UserRights();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, userrightsFragment)
                        .commit();
            } else {
                Fragment homeFragment = new home();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main, homeFragment)
                        .commit();
            }
        }
        homeBar = findViewById(R.id.home);
        communityBar = findViewById(R.id.community);
        shopBar = findViewById(R.id.shop);
        analysisBar = findViewById(R.id.analysis);
        farmBar = findViewById(R.id.farm);
        userBar = findViewById(R.id.user);

        if (barClick == R.id.home) {
            homeBar.setColorFilter(getResources().getColor(R.color.active_bar));
        }


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
                FrameLayout frame = findViewById(R.id.main);
                frame.removeAllViews();
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
                analysisBar.setColorFilter(getResources().getColor(R.color.active_bar));
                Intent analysisIntent = new Intent(HomeActivity.this, AnalysisActivity.class);
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

    @Override
    public void onBackPressed() {
        // Quản lý back stack: nếu có Fragment trong back stack, pop nó ra
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();  // Nếu không có Fragment, thực hiện hành động mặc định
        }
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
    private void abbTabs(){
        pagerAdapter = new ViewPagerAdapter1(getSupportFragmentManager());
        pagerAdapter.noOfTabs = 1;

        viewPager.setAdapter(pagerAdapter);

    }
    private void unsetAdapter(){
        viewPager.setAdapter(null);
    }
}