package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import tlu.edu.vn.ht63.htnongnghiep.Component.Subcomponent.MenuFragment;
import tlu.edu.vn.ht63.htnongnghiep.Container.UI.Library_informationTree_view;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class LibTreeAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lib_tree_acitivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if(savedInstanceState == null){
            Library_informationTree_view treeLibView = new Library_informationTree_view();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main,treeLibView).commit();
//            transaction.addToBackStack("TreeLib");
        }
    }
}