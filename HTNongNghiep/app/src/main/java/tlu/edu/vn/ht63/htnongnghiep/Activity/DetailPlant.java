package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import tlu.edu.vn.ht63.htnongnghiep.R;

public class DetailPlant extends AppCompatActivity {

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
        Spinner spinner_temperature = findViewById(R.id.input_temperature);

        String[] options_temperature = {"Nóng", "Ẩm", "Khô", "Lạnh", "Ôn hòa"};

        ArrayAdapter<String> adapter_temperature = new ArrayAdapter<>(this, R.layout.spinner_detail_plant_item, options_temperature);
        adapter_temperature.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_temperature.setAdapter(adapter_temperature);

        Spinner spinner_environment = findViewById(R.id.input_environment);

        String[] options_environment = {"Cạn", "Nước", "Trôi nổi trên mặt nước", "Nhà kính", "Vườn"};

        ArrayAdapter<String> adapter_environment = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options_environment);
        adapter_environment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_environment.setAdapter(adapter_environment);

        Spinner spinner_type = findViewById(R.id.input_type);

        String[] options_type = {"Cây thân cỏ", "Cây thân gỗ", "Cây thân leo", "Cây thủy sinh", "Cây khí sinh"};

        ArrayAdapter<String> adapter_type = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options_type);
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_type.setAdapter(adapter_type);
    }
}