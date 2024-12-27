package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import tlu.edu.vn.ht63.htnongnghiep.Container.UI.analysis_ImageAnalysis_view;
import tlu.edu.vn.ht63.htnongnghiep.Container.UI.shop_Farm_view;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class ShopActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.blank_home);
        if (savedInstanceState == null) {
            Fragment shopFragment = new shop_Farm_view();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main, shopFragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();
        }
    }
}
