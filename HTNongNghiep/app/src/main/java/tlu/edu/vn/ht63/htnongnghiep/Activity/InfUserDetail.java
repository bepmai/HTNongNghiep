package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.app.DatePickerDialog;
import java.util.Calendar;
import java.text.ParseException;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import tlu.edu.vn.ht63.htnongnghiep.Model.InforUser;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class InfUserDetail extends AppCompatActivity {
    EditText input_name, input_nameplant, input_birthday;
    Spinner input_gender;
    Button btn_update;
    ImageView ic_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inf_user_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        input_name = findViewById(R.id.input_name);
        input_nameplant = findViewById(R.id.input_nameplant);
        input_birthday = findViewById(R.id.input_birthday);
        input_gender = findViewById(R.id.input_gender);
        btn_update = findViewById(R.id.btn_update);
        ic_back =  findViewById(R.id.ic_back);

        ic_back.setOnClickListener(view -> {
            Intent intent = new Intent(InfUserDetail.this, HomeActivity.class);
            startActivity(intent);

            finish();
        });


        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);
        Log.d("Token",userId);

        if (userId != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("inforUser");
            userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String dateOfBirth = dataSnapshot.child("dateOfBirth").getValue(String.class);

                        if (dateOfBirth != null) {
                            try {
                                SimpleDateFormat inputFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("vi"));

                                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi"));

                                Date date = inputFormat.parse(dateOfBirth);
                                String formattedDate = outputFormat.format(date);

                                input_birthday.setText(formattedDate);
                            } catch (ParseException e) {
                                input_birthday.setText(dateOfBirth);
                            }
                        }

                        String fullName = dataSnapshot.child("fullName").getValue(String.class);
                        String address = dataSnapshot.child("adress").getValue(String.class);
                        String nameplant = dataSnapshot.child("plant").getValue(String.class);
                        String gender = dataSnapshot.child("gender").getValue(String.class);

                        input_name.setText(fullName);
                        input_nameplant.setText(address);
                        input_nameplant.setText(nameplant);

                        if (gender != null && gender.equalsIgnoreCase("Nữ")) {
                            input_gender.setSelection(1);
                        } else {
                            input_gender.setSelection(0);
                        }
                    } else {
                        Toast.makeText(InfUserDetail.this, "Người dùng không tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(InfUserDetail.this, "Lỗi khi lấy dữ liệu: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Không thể lấy userId từ SharedPreferences!", Toast.LENGTH_SHORT).show();
        }

        btn_update.setOnClickListener(view -> {
            String fullName = input_name.getText().toString().trim();
            String address = input_nameplant.getText().toString().trim();
            String dateOfBirth = input_birthday.getText().toString().trim();
            String gender = input_gender.getSelectedItem().toString();

            if (fullName.isEmpty() || address.isEmpty() || dateOfBirth.isEmpty()) {
                Toast.makeText(InfUserDetail.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (userId != null) {
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("inforUser").child(userId);

                Map<String, Object> updates = new HashMap<>();
                updates.put("fullName", fullName);
                updates.put("adress", address);
                updates.put("dateOfBirth", dateOfBirth);
                updates.put("gender", gender);

                input_name.clearFocus();
                input_nameplant.clearFocus();
                input_birthday.clearFocus();

                userRef.updateChildren(updates).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(InfUserDetail.this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(InfUserDetail.this, "Cập nhật thông tin thất bại!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(InfUserDetail.this, "Không thể lấy userId để cập nhật!", Toast.LENGTH_SHORT).show();
            }
        });

        input_birthday.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    InfUserDetail.this,
                    (view1, year, month, dayOfMonth) -> {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi"));
                        input_birthday.setText(dateFormat.format(calendar.getTime()));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        String[] options_gender = {"Nam", "Nữ"};
        ArrayAdapter<String> adapter_gender = new ArrayAdapter<>(this, R.layout.spinner_detail_plant_item, options_gender);
        adapter_gender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        input_gender.setAdapter(adapter_gender);
    }
}
