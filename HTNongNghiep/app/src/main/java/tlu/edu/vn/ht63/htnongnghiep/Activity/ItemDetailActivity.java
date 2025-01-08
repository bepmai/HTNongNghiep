package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import tlu.edu.vn.ht63.htnongnghiep.Model.Expenditure;
import tlu.edu.vn.ht63.htnongnghiep.Model.Plant;
import tlu.edu.vn.ht63.htnongnghiep.Model.PlantOfUser;
import tlu.edu.vn.ht63.htnongnghiep.Model.Revenue;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class ItemDetailActivity extends AppCompatActivity {
    Button button1;
    ImageView imageView;
    DatabaseReference expenditureDetailRef,revenueDetailRef;
    Plant plant;
    String userId,fullName,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.info_product);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imageView = findViewById(R.id.img1);
        button1 = findViewById(R.id.button1);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", null);

        plant = (Plant) getIntent().getSerializableExtra("plant");

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
                    Toast.makeText(ItemDetailActivity.this, "Người dùng không tồn tại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ItemDetailActivity.this, "Lỗi khi lấy dữ liệu: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buyPlanteData();
            }
        });

//        Intent intent = getIntent();
//
//        int plantImage = intent.getIntExtra("plant_image", R.drawable.tree);
//        imageView.setImageResource(plantImage);
    }

    private void buyPlanteData(){
        String expenditureDetailId = expenditureDetailRef.push().getKey();
        String revenueDetailId = revenueDetailRef.push().getKey();

        Revenue revenue = new Revenue(
                expenditureDetailId,
                plant.getId(),
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
                Toast.makeText(ItemDetailActivity.this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
            }
        })
        .addOnFailureListener(e -> {
            Toast.makeText(ItemDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });

        Expenditure expenditure = new Expenditure(
                expenditureDetailId,
                plant.getId(),
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
                Toast.makeText(ItemDetailActivity.this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        })
        .addOnFailureListener(e -> {
            Toast.makeText(ItemDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }
}