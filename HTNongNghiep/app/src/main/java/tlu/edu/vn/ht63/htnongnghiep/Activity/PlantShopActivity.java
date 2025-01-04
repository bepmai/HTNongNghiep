package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import tlu.edu.vn.ht63.htnongnghiep.Adapter.PlantAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.Plant;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class PlantShopActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Plant> plantList; // Khai báo
    private Button cartButton;
    PlantAdapter adapter;

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
        plantList.add(new Plant("Cây Ổi", "500.000đ", "-20%", "4.8 (100 đánh giá)", R.drawable.img));
        plantList.add(new Plant("Cây Xoài", "750.000đ", "-10%", "4.5 (80 đánh giá)", R.drawable.img_1));
        plantList.add(new Plant("Cây Cam", "600.000đ", "-15%", "4.7 (90 đánh giá)", R.drawable.img_2));
        plantList.add(new Plant("Cây Bưởi", "900.000đ", "-5%", "4.9 (120 đánh giá)", R.drawable.img_3));
        plantList.add(new Plant("Cây Chanh", "300.000đ", "-25%", "4.6 (110 đánh giá)", R.drawable.img));

        // Set adapter
        adapter = new PlantAdapter(plantList, new PlantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Plant plant) {
                // Handle item click to navigate to another activity
                Log.d("PlantListActivity", "Navigating to ItemDetailActivity");
                Intent intent = new Intent(PlantShopActivity.this, ItemDetailActivity.class);
                intent.putExtra("plant_name", plant.getName());
                intent.putExtra("plant_price", plant.getPrice());
                intent.putExtra("plant_discount", plant.getDiscount());
                intent.putExtra("plant_rating", plant.getRating());
                intent.putExtra("plant_image", plant.getImageResId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlantShopActivity.this, PlantDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}