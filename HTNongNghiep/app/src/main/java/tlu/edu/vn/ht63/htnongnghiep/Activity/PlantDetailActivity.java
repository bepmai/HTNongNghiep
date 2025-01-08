package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
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
import java.util.HashMap;
import java.util.Map;

import tlu.edu.vn.ht63.htnongnghiep.Component.Subcomponent.ToastFragment;
import tlu.edu.vn.ht63.htnongnghiep.Model.Plant;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class PlantDetailActivity extends AppCompatActivity {
    EditText tree_name,tree_name_duplicate,tree_price,tree_description;
    ImageView img1;
    Button submit_button;
    DatabaseReference plantDetailRef;
    String userId;
    private String imageURL;
    private Uri uri;
    Plant plant;
    ImageView backButton;
    String fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_detail);

        tree_name = findViewById(R.id.tree_name);
        tree_name_duplicate = findViewById(R.id.tree_name_duplicate);
        tree_price = findViewById(R.id.tree_price);
        tree_description = findViewById(R.id.tree_description);
        img1 = findViewById(R.id.img1);
        submit_button = findViewById(R.id.submit_button);
        backButton = findViewById(R.id.backButton);

        ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        uri = data.getData();
                        img1.setImageURI(uri);
                    }
                } else {
                    Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
                }
            });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        plant = (Plant) getIntent().getSerializableExtra("plant");
        if (plant != null){
            tree_name.setText(plant.getName());
            tree_name_duplicate.setText(plant.getAddress());
            tree_price.setText(plant.getPrice().toString());
            tree_description.setText(plant.getDescription());
            Glide.with(img1)
                    .load(plant.getImage())
                    .placeholder(R.drawable.group260) // Ảnh hiển thị khi đang tải
                    .error(R.drawable.group260)       // Ảnh hiển thị khi lỗi
                    .into(img1);
        }

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        userId = sharedPreferences.getString("userId", null);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("inforUser");
        userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    fullName = dataSnapshot.child("fullName").getValue(String.class);
                } else {
                    Toast.makeText(PlantDetailActivity.this, "Người dùng không tồn tại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PlantDetailActivity.this, "Lỗi khi lấy dữ liệu: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        plantDetailRef = FirebaseDatabase.getInstance()
                .getReference("PlantShop")
                .child(userId);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PlantDetailActivity.this);
                builder.setCancelable(false);
                builder.setView(R.layout.progress_layout);
                AlertDialog dialog = builder.create();
                dialog.show();

                String plantDetailId;
                if (plant.getId().isEmpty() || plant.getId() == "" || plant.getId() == null){
                    plantDetailId = plantDetailRef.push().getKey();
                }else {
                    plantDetailId = plant.getId();
                }

                Plant plant_new = new Plant(
                        plantDetailId,
                        "",
                        userId,
                        "Không có",
                        new Date(),
                        tree_name_duplicate.getText().toString().trim(),
                        tree_description.getText().toString().trim(),
                        tree_name.getText().toString().trim(),
                        Float.parseFloat(tree_price.getText().toString().trim()),
                        0f,
                        ""
                );

                plant_new.setNameuser(fullName);

                if (plant.getImage().isEmpty() || plant.getImage() == "" || plant.getImage() == null){

                }else {
                    plant_new.setImage(plant.getImage());
                }

                if (uri != null) {
                    StorageReference storageReference = FirebaseStorage.getInstance()
                            .getReference("images")
                            .child(uri.getLastPathSegment());

                    storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                        taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                imageURL = task.getResult().toString();
                                plant_new.setImage(imageURL);

                                plantDetailRef.child(plantDetailId).setValue(plant_new).addOnCompleteListener(task2 -> {
                                    if (task2.isSuccessful()) {
                                        Toast.makeText(PlantDetailActivity.this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        Intent intent = new Intent(PlantDetailActivity.this, PlantShopDetailActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(PlantDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                });
                            }
                        });
                    }).addOnFailureListener(e -> {
                        ToastFragment toastFragment = new ToastFragment(3, "Cập nhật thông tin thất bại!");
                        toastFragment.showToast(getSupportFragmentManager(),R.id.main);
                        dialog.dismiss();
                    });
                }else {
                    plantDetailRef.child(plantDetailId).setValue(plant_new).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(PlantDetailActivity.this, "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            Intent intent = new Intent(PlantDetailActivity.this, PlantShopDetailActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(PlantDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    });
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlantDetailActivity.this, PlantShopActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}