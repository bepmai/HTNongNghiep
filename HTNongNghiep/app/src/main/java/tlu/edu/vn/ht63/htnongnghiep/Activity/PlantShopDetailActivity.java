package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import tlu.edu.vn.ht63.htnongnghiep.Model.Expenditure;
import tlu.edu.vn.ht63.htnongnghiep.Model.Plant;
import tlu.edu.vn.ht63.htnongnghiep.Model.Revenue;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class PlantShopDetailActivity extends AppCompatActivity {
    Button button1;
    ImageView img1;
    DatabaseReference expenditureDetailRef,revenueDetailRef,plantShopDetailRef;
    Plant plant;
    String userId,fullName,address;
    ImageButton backButton;
    TextView namePlantTxt,dateSellTxt,nameSellTxt,adressTxt,priceTxt,descreptionTxt,ratingTxt,ratingTotalTxt;

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

        namePlantTxt = findViewById(R.id.namePlantTxt);
        dateSellTxt = findViewById(R.id.dateSellTxt);
        nameSellTxt = findViewById(R.id.nameSellTxt);
        adressTxt = findViewById(R.id.adressTxt);
        priceTxt = findViewById(R.id.priceTxt);
        descreptionTxt = findViewById(R.id.descreptionTxt);
        ratingTxt = findViewById(R.id.ratingTxt);
        ratingTotalTxt = findViewById(R.id.ratingTotalTxt);
        button1 = findViewById(R.id.button1);
        img1 = findViewById(R.id.img1);
        backButton = findViewById(R.id.backButton);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", null);

        plant = (Plant) getIntent().getSerializableExtra("plant");
        if (plant != null){
            namePlantTxt.setText(plant.getName());
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateSellTxt.setText(dateTimeFormat.format(plant.getDatesell()));
            nameSellTxt.setText("Người bán: "+plant.getNameuser());
            adressTxt.setText("Địa chỉ: "+plant.getAddress());
            priceTxt.setText("Giá bán: "+plant.getPrice().toString()+" vnđ");
            descreptionTxt.setText("Mô tả"+plant.getDescription());
//            ratingTxt.setText(plant.getName());
//            ratingTotalTxt.setText(plant.getName());
            Glide.with(img1)
                    .load(plant.getImage())
                    .placeholder(R.drawable.group260) // Ảnh hiển thị khi đang tải
                    .error(R.drawable.group260)       // Ảnh hiển thị khi lỗi
                    .into(img1);

            expenditureDetailRef = FirebaseDatabase.getInstance()
                    .getReference("expenditure")
                    .child(userId);

            revenueDetailRef = FirebaseDatabase.getInstance()
                    .getReference("revenue")
                    .child(plant.getUserid());

            plantShopDetailRef = FirebaseDatabase.getInstance()
                    .getReference("PlantShop")
                    .child(plant.getUserid())
                    .child(plant.getId());

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

        sendDataToGoogleSheet(expenditure);

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

//        plantShopDetailRef.removeValue().addOnSuccessListener(aVoid -> {
//            Toast.makeText(PlantShopDetailActivity.this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, RevenueExpenditureActivity.class);
//            startActivity(intent);
//            finish();
//        })
//        .addOnFailureListener(e -> {
//            Toast.makeText(PlantShopDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//        });
    }

    private void sendDataToGoogleSheet(Expenditure expenditure) {
        String url = "https://script.google.com/macros/s/AKfycbw8oYb3wwHLDXnkVFolqypN1gSu3FFdJs1qmMCsTa4bARQOelUYcxdOBWt8Eug6zwIJ/exec"; // Thay bằng URL của Apps Script
        JSONObject jsonObject = new JSONObject();

        try {
            // Chuyển dữ liệu sang JSON
            jsonObject.put("id", expenditure.getId());
            jsonObject.put("userId", expenditure.getIdSeller());
            jsonObject.put("name", expenditure.getNameSeller());
            jsonObject.put("total", 1);
            jsonObject.put("address", expenditure.getAdress());
            jsonObject.put("dateSell", expenditure.getDate().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Tạo request POST
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    try {
                        JSONObject res = new JSONObject(response.toString());
                        String status = res.getString("status");
                        if (status.equals("success")) {
                            Toast.makeText(this, "Gửi dữ liệu thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Lỗi: " + res.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Lỗi: " + error.getMessage(), Toast.LENGTH_SHORT).show());

        // Gửi request
        Volley.newRequestQueue(this).add(request);
    }
}