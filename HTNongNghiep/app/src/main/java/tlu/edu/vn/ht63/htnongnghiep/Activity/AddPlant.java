package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.app.AlertDialog;
import androidx.annotation.NonNull;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

import tlu.edu.vn.ht63.htnongnghiep.Container.UI.GardenFragment;
import tlu.edu.vn.ht63.htnongnghiep.Model.PlantOfUser;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class AddPlant extends AppCompatActivity {
    ImageView image, ic_back;
    EditText nameplant, ageplant, height, weeklyWatering, weeklySunExposure, health, note;
    Spinner temperature, environment, type;
    Button btnAdd;
    Uri uri;
    String imageURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_plant);
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
        btnAdd = findViewById(R.id.btn_add);
        ic_back = findViewById(R.id.left_admin);

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở MainActivity (chứa GardenFragment)
                Intent intent = new Intent(AddPlant.this, HomeActivity.class);
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
                            Toast.makeText(AddPlant.this, "No Image Selected", Toast.LENGTH_SHORT).show();
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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }
    public void saveData(){
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("PlantImages")
                    .child(uri.getLastPathSegment());
            AlertDialog.Builder builder = new AlertDialog.Builder(AddPlant.this);
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete());
                    Uri urlImage = uriTask.getResult();
                    imageURL = urlImage.toString();
                    uploadData();
                    dialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.dismiss();
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

        if (userId == null) {
            Toast.makeText(this, "User ID is missing", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference plantOfUserRef = FirebaseDatabase.getInstance()
                .getReference("PlantOfUser")
                .child(userId);

        String plantId = plantOfUserRef.push().getKey();

        if (plantId == null) {
            Toast.makeText(this, "Failed to generate plant ID", Toast.LENGTH_SHORT).show();
            return;
        }

        PlantOfUser plantOfUser = new PlantOfUser(imageURL, namePlant, agePlant, heightPlant, weeklywatering, weeklysunExposure,
                healthStatus, temperatureValue, environmentValue, typeValue, notePlant);

        plantOfUserRef.child(plantId).setValue(plantOfUser)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("plantId", plantId);
                        editor.apply();

                        Toast.makeText(AddPlant.this, "Saved information", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AddPlant.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}