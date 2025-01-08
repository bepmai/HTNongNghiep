package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import tlu.edu.vn.ht63.htnongnghiep.Model.Expenditure;
import tlu.edu.vn.ht63.htnongnghiep.Model.Plant;
import tlu.edu.vn.ht63.htnongnghiep.Model.Revenue;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class PlantShopDetailActivity extends AppCompatActivity {
    Button button1;
    ImageView imageView;
    DatabaseReference expenditureDetailRef,revenueDetailRef;
    Plant plant;
    String userId,fullName,address;
    ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_plant_shop_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.img1);
        button1 = findViewById(R.id.button1);
        backButton = findViewById(R.id.backButton);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", null);

        plant = (Plant) getIntent().getSerializableExtra("plant");
        if (plant != null){
            expenditureDetailRef = FirebaseDatabase.getInstance()
                    .getReference("expenditure")
                    .child(userId);

            revenueDetailRef = FirebaseDatabase.getInstance()
                    .getReference("revenue")
                    .child(plant.getUserid());

            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("inforUser");
            userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        fullName = dataSnapshot.child("fullName").getValue(String.class);
                        address = dataSnapshot.child("adress").getValue(String.class);
                    } else {
                        Toast.makeText(PlantShopDetailActivity.this, "Người dùng không tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(PlantShopDetailActivity.this, "Lỗi khi lấy dữ liệu: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buyPlanteData();
                }
            });
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void buyPlanteData(){
        String expenditureDetailId = expenditureDetailRef.push().getKey();
        String revenueDetailId = revenueDetailRef.push().getKey();

        Revenue revenue = new Revenue(
                expenditureDetailId,
                userId,
                plant.getIdplant(),
                plant.getImage(),
                "Không có",
                "Không có",
                new Date(),
                0,
                plant.getName(),
                1,
                plant.getPrice(),
                plant.getPrice()
        );

        revenue.setNameBuyer(fullName);
        revenue.setAdress(address);

        revenueDetailRef.child(expenditureDetailId).setValue(revenue).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(PlantShopDetailActivity.this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
            }
        })
        .addOnFailureListener(e -> {
            Toast.makeText(PlantShopDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });

        Expenditure expenditure = new Expenditure(
                expenditureDetailId,
                0,
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
                Toast.makeText(PlantShopDetailActivity.this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RevenueExpenditureActivity.class);
                startActivity(intent);
                finish();
            }
        })
        .addOnFailureListener(e -> {
            Toast.makeText(PlantShopDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}