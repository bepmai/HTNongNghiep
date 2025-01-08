package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import tlu.edu.vn.ht63.htnongnghiep.Adapter.PlantAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.PlantOfMyShopAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.PlantOfUserAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.Plant;
import tlu.edu.vn.ht63.htnongnghiep.Model.PlantOfUser;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class PlantShopActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Plant> plantList; // Khai báo
    private Button cartButton;
    PlantOfMyShopAdapter adapter;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantshop);

        recyclerView = findViewById(R.id.recyclerView);
        plantList = new ArrayList<>(); // Khởi tạo
        cartButton = findViewById(R.id.cartButton);

        // Thêm sản phẩm vào danh sách
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        plantList = new ArrayList<>();

        // Set adapter
        adapter = new PlantOfMyShopAdapter(this, plantList);
        recyclerView.setAdapter(adapter);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        if (userId == null) {
            Toast.makeText(PlantShopActivity.this, "User ID is missing", Toast.LENGTH_SHORT).show();
        }
        else {
            databaseReference = FirebaseDatabase.getInstance().getReference("PlantShop").child(userId);
            eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    plantList.clear();
                    for (DataSnapshot idPlantSnapshot : snapshot.getChildren()) {
                        Plant plant = idPlantSnapshot.getValue(Plant.class);
                        if (plant != null) {
                            plantList.add(plant);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("FirebaseError", "Error: " + error.getMessage());
                }
            });
        }

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlantShopActivity.this, PlantDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}