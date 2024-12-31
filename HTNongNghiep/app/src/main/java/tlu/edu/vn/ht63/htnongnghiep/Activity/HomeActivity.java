package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import tlu.edu.vn.ht63.htnongnghiep.Container.UI.GardenFragment;
import tlu.edu.vn.ht63.htnongnghiep.Container.UI.UserRights;
import tlu.edu.vn.ht63.htnongnghiep.Container.UI.home;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class HomeActivity extends AppCompatActivity {

    private ImageButton homeBar,communityBar,shopBar,analysisBar,farmBar,userBar;
    private int barClick = R.id.home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            // Tạo một Fragment mới
            Fragment homeFragment = new home();

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main, homeFragment);
            transaction.commit();
        }
        homeBar = findViewById(R.id.home);
        communityBar = findViewById(R.id.community);
        shopBar = findViewById(R.id.shop);
        analysisBar = findViewById(R.id.analysis);
        farmBar = findViewById(R.id.farm);
        userBar = findViewById(R.id.user);

        if(barClick == R.id.home ){
            homeBar.setColorFilter(getResources().getColor(R.color.active_bar));
        }


        homeBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thay đổi màu nền của nút khi click vào
                unsetColorBar();
                homeBar.setColorFilter(getResources().getColor(R.color.active_bar)); // Màu bạn muốn
                barClick=R.id.home;

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
                barClick=R.id.community;

                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        shopBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsetColorBar();
                shopBar.setColorFilter(getResources().getColor(R.color.active_bar));
                barClick=R.id.shop;
            }
        });

        analysisBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsetColorBar();
                analysisBar.setColorFilter(getResources().getColor(R.color.active_bar));
                barClick=R.id.analysis;
            }
        });

        farmBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsetColorBar();
                farmBar.setColorFilter(getResources().getColor(R.color.active_bar));
                barClick=R.id.farm;

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
                userBar.setColorFilter(getResources().getColor(R.color.active_bar));
                barClick=R.id.user;

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

    private void unsetColorBar(){
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