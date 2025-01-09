package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import tlu.edu.vn.ht63.htnongnghiep.Container.UI.ExpenditureDetailFragment;
import tlu.edu.vn.ht63.htnongnghiep.Container.UI.ExpenditureFragment;
import tlu.edu.vn.ht63.htnongnghiep.Container.UI.ProductDetailFragment;
import tlu.edu.vn.ht63.htnongnghiep.Container.UI.RevenueFragment;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class RevenueExpenditureActivity extends AppCompatActivity {
    ImageButton backButton;
    Button revenueBtn,expenditureBtn;
    View revenueBottomBolder,expenditureBottomBolder;
    FrameLayout frameLayout;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_revenue_expenditure);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backButton = findViewById(R.id.backButton);
        revenueBtn = findViewById(R.id.revenueBtn);
        expenditureBtn = findViewById(R.id.expenditureBtn);
        revenueBottomBolder = findViewById(R.id.revenueBottomBolder);
        expenditureBottomBolder = findViewById(R.id.expenditureBottomBolder);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        revenueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revenueBtn.setTextColor(getResources().getColor(R.color.green));
                expenditureBtn.setTextColor(getResources().getColor(R.color.white_black));
                revenueBottomBolder.setBackgroundColor(getResources().getColor(R.color.green));
                expenditureBottomBolder.setBackgroundColor(getResources().getColor(R.color.white));

                Fragment revenueFragment = new RevenueFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, revenueFragment);
                transaction.commit();

                floatingActionButton.setVisibility(View.GONE);

//                floatingActionButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Fragment revenueDetailFragment = new RevenueDetailFragment();
//                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//                        transaction.replace(R.id.main, revenueDetailFragment);
//                        transaction.addToBackStack(null);
//                        transaction.commit();
//                    }
//                });
            }
        });

        expenditureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revenueBtn.setTextColor(getResources().getColor(R.color.white_black));
                expenditureBtn.setTextColor(getResources().getColor(R.color.green));
                revenueBottomBolder.setBackgroundColor(getResources().getColor(R.color.white));
                expenditureBottomBolder.setBackgroundColor(getResources().getColor(R.color.green));

                Fragment expenditureFragment = new ExpenditureFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, expenditureFragment);
                transaction.commit();

                floatingActionButton.setVisibility(View.VISIBLE);

                floatingActionButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment productDetailFragment = new ProductDetailFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.main, productDetailFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
//                        showBottomDialog();
                    }
                });
            }
        });

        Fragment revenueFragment = new RevenueFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, revenueFragment);
        transaction.commit();
    }

    private void showBottomDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout_renueve);

        LinearLayout layoutPlant = dialog.findViewById(R.id.layoutPlant);
        LinearLayout layoutProduct = dialog.findViewById(R.id.layoutProduct);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        layoutPlant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Fragment expenditureDetailFragment = new ExpenditureDetailFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main, expenditureDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();
//                Toast.makeText(MainActivity.this,"Upload a Video is clicked",Toast.LENGTH_SHORT).show();
            }
        });

        layoutProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Fragment productDetailFragment = new ProductDetailFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main, productDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();
//                Toast.makeText(MainActivity.this,"Create a short is Clicked",Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}