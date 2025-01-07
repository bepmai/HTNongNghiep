package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tlu.edu.vn.ht63.htnongnghiep.Model.Expenditure;
import tlu.edu.vn.ht63.htnongnghiep.Model.Plant;
import tlu.edu.vn.ht63.htnongnghiep.Model.PlantOfUser;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class ItemDetailActivity extends AppCompatActivity {
    Button button1;
    ImageView imageView;
    DatabaseReference expenditureDetailRef,revenueDetailRef;
    Plant plant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_product);

        imageView = findViewById(R.id.img1);
        button1 = findViewById(R.id.button1);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        plant = (Plant) getIntent().getSerializableExtra("plant");

        expenditureDetailRef = FirebaseDatabase.getInstance()
                .getReference("expenditure")
                .child(userId);

        revenueDetailRef = FirebaseDatabase.getInstance()
                .getReference("revenue")
                .child(plant.getUserid());

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ItemDetailActivity.this)
                        .setTitle("Xác nhận Mua")
                        .setMessage("Bạn có chắc chắn muốn mua cây này không?")
                        .setPositiveButton("Đồng ý", (dialog, which) -> buyPlanteData())
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });

        Intent intent = getIntent();

        int plantImage = intent.getIntExtra("plant_image", R.drawable.tree);
        imageView.setImageResource(plantImage);


    }

    private void buyPlanteData(){
        String expenditureDetailId = expenditureDetailRef.push().getKey();

        Expenditure expenditure = new Expenditure(
                expenditureDetailId,
                1,
                plant.getUserid(),
                plant.getIdplant(),
                plant.getImage(),
                plant.getNameuser(),
                plant.getAddress(),
                plant.getDatesell(),
                0,
                plant.getName(),
                1,
                plant.getPrice(),
                plant.getPrice()
        );

        expenditureDetailRef.child(expenditureDetailId).setValue(expenditure).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(ItemDetailActivity.this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        })
        .addOnFailureListener(e -> {
            Toast.makeText(ItemDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}