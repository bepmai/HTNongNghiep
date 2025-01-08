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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

import tlu.edu.vn.ht63.htnongnghiep.Model.Plant;
import tlu.edu.vn.ht63.htnongnghiep.Model.PlantOfUser;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class DetailPlant extends AppCompatActivity {
    ImageView image, ic_back;
    EditText nameplant, ageplant, height, weeklyWatering, weeklySunExposure, health, note;
    Spinner temperature, environment, type;
    Button btnDelete, btnUpdate,btn_sell;
    String imageUrl = "";
    private String currentImageUrl;
    Uri uri;
    PlantOfUser plantOfUser;
    String userId,fullName,adress;

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
        btnDelete = findViewById(R.id.btn_delete);
        btnUpdate = findViewById(R.id.btn_update);
        String plantId = getIntent().getStringExtra("plantId");
        if (plantId != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("plantId", plantId);
            editor.apply();
        }

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

        ArrayAdapter<String> adapter_environment = new ArrayAdapter<>(this, R.layout.spinner_detail_plant_item, options_environment);
        adapter_environment.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        environment.setAdapter(adapter_environment);

        type = findViewById(R.id.input_type);

        String[] options_type = {"Cây thân thảo", "Cây thân gỗ", "Cây thân leo", "Cây thủy sinh", "Cây khí sinh"};

        ArrayAdapter<String> adapter_type = new ArrayAdapter<>(this, R.layout.spinner_detail_plant_item, options_type);
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type.setAdapter(adapter_type);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            currentImageUrl = bundle.getString("Image"); // Lưu URL ảnh hiện tại
            Glide.with(this).load(currentImageUrl).into(image);
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
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DetailPlant.this)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa cây này không?")
                        .setPositiveButton("Xóa", (dialog, which) -> deletePlantData())
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });

        btn_sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(DetailPlant.this)
                        .setTitle("Xác nhận bán")
                        .setMessage("Bạn có chắc chắn muốn bán cây này không?")
                        .setPositiveButton("Đồng ý", (dialog, which) -> sellPlantData())
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uri != null) {
                    uploadImageToFirebase(uri); // Tải ảnh mới lên Firebase Storage
                } else {
                    // Không chọn ảnh mới, dùng URL ảnh hiện tại để cập nhật thông tin
                    if (currentImageUrl != null) {
                        updatePlantData(currentImageUrl); // Cập nhật dữ liệu với URL ảnh hiện tại
                    } else {
                        Toast.makeText(DetailPlant.this, "Không có ảnh để cập nhật!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", null);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("inforUser");
        userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    fullName = dataSnapshot.child("fullName").getValue(String.class);
                    adress = dataSnapshot.child("adress").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DetailPlant.this, "Lỗi khi lấy dữ liệu: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadImageToFirebase(Uri imageUri) {
        // Tạo tham chiếu tới Firebase Storage
        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference("PlantImages")
                .child(imageUri.getLastPathSegment());

        // Tải ảnh lên
        storageReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Lấy URL của ảnh đã tải lên
                    storageReference.getDownloadUrl().addOnSuccessListener(downloadUri -> {
                        String imageUrl = downloadUri.toString();
                        updatePlantData(imageUrl); // Cập nhật dữ liệu Firebase Database với URL mới
                    });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(DetailPlant.this, "Tải ảnh thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void updatePlantData(String imageUrl) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        plantOfUser = (PlantOfUser) getIntent().getSerializableExtra("plant");

        if (userId == null || plantOfUser.getId() == null) {
            Toast.makeText(this, "User ID hoặc Plant ID bị thiếu!", Toast.LENGTH_SHORT).show();
            return;
        }

        String finalImageUrl = imageUrl != null ? imageUrl : currentImageUrl;

        PlantOfUser updatedPlant = new PlantOfUser(
                plantOfUser.getId(),
                finalImageUrl, // URL ảnh mới
                nameplant.getText().toString().trim(),
                Integer.parseInt(ageplant.getText().toString().trim()),
                Float.parseFloat(height.getText().toString().trim()),
                Integer.parseInt(weeklyWatering.getText().toString().trim()),
                Integer.parseInt(weeklySunExposure.getText().toString().trim()),
                health.getText().toString().trim(),
                temperature.getSelectedItem().toString(),
                environment.getSelectedItem().toString(),
                type.getSelectedItem().toString(),
                note.getText().toString().trim()
        );

        DatabaseReference plantOfUserRef = FirebaseDatabase.getInstance()
                .getReference("PlantOfUser")
                .child(userId)
                .child(plantOfUser.getId());

        plantOfUserRef.setValue(updatedPlant)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(DetailPlant.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                        finish(); // Đóng Activity
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(DetailPlant.this, "Cập nhật thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void deletePlantData() {

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        plantOfUser = (PlantOfUser) getIntent().getSerializableExtra("plant");

        if (userId == null || plantOfUser.getId() == null) {
            Toast.makeText(this, "User ID hoặc Plant ID bị thiếu!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lấy URL của ảnh từ cây cần xóa
        DatabaseReference plantOfUserRef = FirebaseDatabase.getInstance()
                .getReference("PlantOfUser")
                .child(userId)
                .child(plantOfUser.getId());

        plantOfUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Lấy URL ảnh của cây
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);

                    if (imageUrl != null) {
                        // Xóa ảnh khỏi Firebase Storage
                        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUrl);
                        storageReference.delete()
                                .addOnSuccessListener(aVoid -> {
                                    // Sau khi xóa ảnh thành công, xóa dữ liệu cây
                                    deletePlantDataFromDatabase(plantOfUserRef);
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(DetailPlant.this, "Xóa ảnh thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        // Nếu không có ảnh, xóa dữ liệu cây ngay lập tức
                        deletePlantDataFromDatabase(plantOfUserRef);
                    }
                } else {
                    Toast.makeText(DetailPlant.this, "Cây không tồn tại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DetailPlant.this, "Lỗi khi lấy dữ liệu: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Hàm xóa dữ liệu cây khỏi Firebase Realtime Database
    private void deletePlantDataFromDatabase(DatabaseReference plantOfUserRef) {
        plantOfUserRef.removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(DetailPlant.this, "Xóa cây thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(DetailPlant.this, "Xóa cây thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void sellPlantData() {
        Plant plant = new Plant(
                "",
                "",
                "",
                "Không có",
                new Date(),
                "",
                "",
                "",
                0f,
                0f,
                ""
        );
        plantOfUser = (PlantOfUser) getIntent().getSerializableExtra("plant");
        if (plantOfUser!=null){
            plant.setIdplant(plantOfUser.getId());
            plant.setDescription(plantOfUser.getNote());
            plant.setName(plantOfUser.getNameplant());
            plant.setImage(plantOfUser.getImage());
        }

        plant.setNameuser(fullName);
        plant.setAddress(adress);

        Intent intent = new Intent(this, PlantDetailActivity.class);
        intent.putExtra("plant",plant);

        startActivity(intent);
    }
}