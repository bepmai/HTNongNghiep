package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import tlu.edu.vn.ht63.htnongnghiep.R;

public class DetailPlant extends AppCompatActivity {
    ImageView image, ic_back;
    EditText nameplant, ageplant, height, weeklyWatering, weeklySunExposure, health, note;
    Spinner temperature, environment, type;
    Button btnDelate, btnUpdate;
    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_plant);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        image = findViewById(R.id.image);
        nameplant = findViewById(R.id.input_nameplant);
        ageplant = findViewById(R.id.input_ageplant);
        height = findViewById(R.id.input_height);
        weeklyWatering = findViewById(R.id.input_weeklyWatering);
        weeklySunExposure = findViewById(R.id.input_weeklySunExposure);
        health = findViewById(R.id.input_health);
        note = findViewById(R.id.input_Note);
        btnDelate = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);

        ic_back = findViewById(R.id.ic_back);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở MainActivity (chứa GardenFragment)
                Intent intent = new Intent(DetailPlant.this, HomeActivity.class);
                intent.putExtra("open_fragment", "garden");
                startActivity(intent);
                finish();
            }
        });

        temperature = findViewById(R.id.input_temperature);

        String[] options_temperature = {"Nóng", "Ẩm", "Khô", "Lạnh", "Ôn hòa"};

        ArrayAdapter<String> adapter_temperature = new ArrayAdapter<>(this, R.layout.spinner_detail_plant_item, options_temperature);
        adapter_temperature.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        temperature.setAdapter(adapter_temperature);

        environment = findViewById(R.id.input_environment);

        String[] options_environment = {"Cạn", "Nước", "Trôi nổi trên mặt nước", "Nhà kính", "Vườn"};

        ArrayAdapter<String> adapter_environment = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options_environment);
        adapter_environment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        environment.setAdapter(adapter_environment);

        type = findViewById(R.id.input_type);

        String[] options_type = {"Cây thân thảo", "Cây thân gỗ", "Cây thân leo", "Cây thủy sinh", "Cây khí sinh"};

        ArrayAdapter<String> adapter_type = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options_type);
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter_type);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            nameplant.setText(bundle.getString("Name"));
            ageplant.setText(bundle.getString("Age"));
            height.setText(bundle.getString("Height"));
            weeklyWatering.setText(bundle.getString("WeeklyWatering"));
            weeklySunExposure.setText(bundle.getString("WeeklySunExposure"));
            health.setText(bundle.getString("Health"));
            note.setText(bundle.getString("Note"));
//            key = bundle.getString("Key");
            imageUrl = bundle.getString("Image");
            Glide.with(this).load(bundle.getString("Image")).into(image);
        }
    }
}