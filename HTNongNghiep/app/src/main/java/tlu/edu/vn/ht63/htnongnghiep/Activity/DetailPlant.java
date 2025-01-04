package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import tlu.edu.vn.ht63.htnongnghiep.Model.PlantOfUser;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class DetailPlant extends AppCompatActivity {
    ImageView image, ic_back;
    EditText nameplant, ageplant, height, weeklyWatering, weeklySunExposure, health, note;
    Spinner temperature, environment, type;
    Button btnDelate, btnUpdate;
    String imageUrl = "";
    Uri uri;
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
            Glide.with(this).load(bundle.getString("Image")).into(image);
            nameplant.setText(bundle.getString("Name"));
            health.setText(bundle.getString("Health"));
            note.setText(bundle.getString("Note"));
            imageUrl = bundle.getString("Image");

            int age = bundle.getInt("Age", 0);
            float heightValue = bundle.getFloat("Height", 0.0f);
            int watering = bundle.getInt("WeeklyWatering", 0);
            int sunExposure = bundle.getInt("WeeklySunExposure", 0);
            ageplant.setText(String.valueOf(age));
            height.setText(String.format("%.2f", heightValue));
            weeklyWatering.setText(String.valueOf(watering));
            weeklySunExposure.setText(String.valueOf(sunExposure));

            String temperatureValue = bundle.getString("Temperature");
            if (temperatureValue != null) {
                int tempPosition = adapter_temperature.getPosition(temperatureValue);
                temperature.setSelection(tempPosition);
            }

            String environmentValue = bundle.getString("Environment");
            if (environmentValue != null) {
                int envPosition = adapter_environment.getPosition(environmentValue);
                environment.setSelection(envPosition);
            }

            String typeValue = bundle.getString("Type");
            if (typeValue != null) {
                int typePosition = adapter_type.getPosition(typeValue);
                type.setSelection(typePosition);
            }
        }
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();
                            image.setImageURI(uri);
                        } else {
                            Toast.makeText(DetailPlant.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }
    public void saveData(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("PlantImages")
                .child(uri.getLastPathSegment());
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageUrl = urlImage.toString();
                uploadData();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
    }
    public void uploadData() {
        String namePlant = nameplant.getText().toString().trim();
        int agePlant = Integer.parseInt(ageplant.getText().toString().trim());
        float heightPlant = Float.parseFloat(height.getText().toString().trim());
        int weeklywatering = Integer.parseInt(weeklyWatering.getText().toString().trim());
        int weeklysunExposure = Integer.parseInt(weeklySunExposure.getText().toString().trim());
        String healthStatus = health.getText().toString().trim();
        String notePlant = note.getText().toString().trim();
        String temperatureValue = temperature.getSelectedItem().toString();
        String environmentValue = environment.getSelectedItem().toString();
        String typeValue = type.getSelectedItem().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);
        String plantId = sharedPreferences.getString("plantId", null);
        Log.d("DetailPlant", "Plant ID: " + plantId); // Kiểm tra ID
        Log.d("DetailPlant", "User ID: " + userId); // Kiểm tra ID

        if (userId == null || plantId == null) {
            Toast.makeText(this, "User ID or Plant ID is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        if (uri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("PlantImages")
                    .child(uri.getLastPathSegment());
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete());
                    Uri urlImage = uriTask.getResult();
                    imageUrl = urlImage.toString();
                    updatePlantData(userId, plantId, namePlant, agePlant, heightPlant, weeklywatering, weeklysunExposure,
                            healthStatus, temperatureValue, environmentValue, typeValue, notePlant);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Xử lý lỗi nếu cần
                }
            });
        } else {
            updatePlantData(userId, plantId, namePlant, agePlant, heightPlant, weeklywatering, weeklysunExposure,
                    healthStatus, temperatureValue, environmentValue, typeValue, notePlant);
        }
    }

    private void updatePlantData(String userId, String plantId, String namePlant, int agePlant, float heightPlant,
                                 int weeklywatering, int weeklysunExposure, String healthStatus, String temperatureValue,
                                 String environmentValue, String typeValue, String notePlant) {

        DatabaseReference plantOfUserRef = FirebaseDatabase.getInstance()
                .getReference("PlantOfUser")
                .child(userId)
                .child(plantId);

        PlantOfUser plantOfUser = new PlantOfUser(imageUrl, namePlant, agePlant, heightPlant, weeklywatering,
                weeklysunExposure, healthStatus, temperatureValue, environmentValue, typeValue, notePlant);

        plantOfUserRef.setValue(plantOfUser)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(DetailPlant.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(DetailPlant.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                });
    }

}