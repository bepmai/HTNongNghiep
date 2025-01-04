package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.PlantShopAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.Plant;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class PlantShopActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PlantShopAdapter plantShopAdapter;
    private ArrayList<Plant> plantList; // Khai báo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantshop);

        recyclerView = findViewById(R.id.recyclerView);
        plantList = new ArrayList<>(); // Khởi tạo

        // Thêm sản phẩm vào danh sách
        plantList.add(new Plant("1", "Cây Xương Rồng", "$20", "Mô tả cây xương rồng", R.drawable.img_1));

        plantShopAdapter = new PlantShopAdapter(plantList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(plantShopAdapter);
    }
    public void onButton3Click(View view) {
        Intent intent = new Intent(PlantShopActivity.this, PlantDetailActivity.class);
        startActivity(intent);
    }
}