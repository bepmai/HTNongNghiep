package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import tlu.edu.vn.ht63.htnongnghiep.R;

public class PlantDetailActivity extends AppCompatActivity {
    EditText tree_name,tree_name_duplicate,tree_price,tree_description;
    ImageView img1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);

        tree_name = findViewById(R.id.tree_name);
        tree_name_duplicate = findViewById(R.id.tree_name_duplicate);
        tree_price = findViewById(R.id.tree_price);
        tree_description = findViewById(R.id.tree_description);
        img1 = findViewById(R.id.img1);

        
    }


}