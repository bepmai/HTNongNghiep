package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import tlu.edu.vn.ht63.htnongnghiep.Container.UI.analysis_ImageAnalysis_view;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class AnalysisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.blank_home);
        if (savedInstanceState == null) {
            // Tạo một Fragment mới
            Fragment imageAnalysisFragment = new analysis_ImageAnalysis_view();

            // Thêm Fragment vào Activity
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.main, imageAnalysisFragment);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            transaction.commit();
        }
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
}