package tlu.edu.vn.ht63.htnongnghiep.Component.Subcomponent;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import tlu.edu.vn.ht63.htnongnghiep.Container.UI.home;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class MenuFragment{
    private static ImageButton homeBar;
    private static ImageButton communityBar;
    private static ImageButton shopBar;
    private static ImageButton analysisBar;
    private static ImageButton farmBar;
    private static ImageButton userBar;
    private static int barClick = R.id.home;

    public MenuFragment(){

    }
    public static void setMenu(AppCompatActivity activity, View view){
        homeBar = view.findViewById(R.id.home);
        communityBar = view.findViewById(R.id.community);
        shopBar = view.findViewById(R.id.shop);
        analysisBar = view.findViewById(R.id.analysis);
        farmBar = view.findViewById(R.id.farm);
        userBar = view.findViewById(R.id.user);

        if(barClick == R.id.home ){
            homeBar.setColorFilter(activity.getResources().getColor(R.color.active_bar));
        }


        homeBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thay đổi màu nền của nút khi click vào
                unsetColorBar();
                homeBar.setColorFilter(activity.getResources().getColor(R.color.active_bar)); // Màu bạn muốn
                barClick=R.id.home;

                Fragment homeFragment = new home();

                FragmentTransaction transaction =activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main, homeFragment);
                transaction.commit();
            }
        });

        communityBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsetColorBar();
                communityBar.setColorFilter(activity.getResources().getColor(R.color.active_bar));
                barClick=R.id.community;
            }
        });

        shopBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsetColorBar();
                shopBar.setColorFilter(activity.getResources().getColor(R.color.active_bar));
                barClick=R.id.shop;
            }
        });

        analysisBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsetColorBar();
                analysisBar.setColorFilter(activity.getResources().getColor(R.color.active_bar));
                barClick=R.id.analysis;
            }
        });

        farmBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsetColorBar();
                farmBar.setColorFilter(activity.getResources().getColor(R.color.active_bar));
                barClick=R.id.farm;
            }
        });

        userBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unsetColorBar();
                userBar.setColorFilter(activity.getResources().getColor(R.color.active_bar));
                barClick=R.id.user;
            }
        });
    }
    private static void unsetColorBar(){
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