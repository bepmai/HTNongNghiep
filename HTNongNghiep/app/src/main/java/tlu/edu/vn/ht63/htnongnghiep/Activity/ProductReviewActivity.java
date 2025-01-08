package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
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

import java.util.Date;

import tlu.edu.vn.ht63.htnongnghiep.Model.Expenditure;
import tlu.edu.vn.ht63.htnongnghiep.Model.PlantOfUser;
import tlu.edu.vn.ht63.htnongnghiep.Model.ReviewPlant;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class ProductReviewActivity extends AppCompatActivity {
    TextView product_name;
    ImageView ic_back;
    RatingBar statrating;
    EditText comment;
    Button btn_review;
    String fullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_review);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        product_name = findViewById(R.id.product_name);
        statrating = findViewById(R.id.statrating);
        comment = findViewById(R.id.comment);
        ic_back = findViewById(R.id.ic_back);
        btn_review = findViewById(R.id.btn_review);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("inforUser");
        userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    fullName = dataSnapshot.child("fullName").getValue(String.class);
                } else {
                    Toast.makeText(ProductReviewActivity.this, "Người dùng không tồn tại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ProductReviewActivity.this, "Lỗi khi lấy dữ liệu: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Expenditure expenditure = (Expenditure) getIntent().getSerializableExtra("expenditure");
        if (expenditure!=null){
            product_name.setText(expenditure.getNameProduct());

            btn_review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseReference reviewDetailRef = FirebaseDatabase.getInstance()
                            .getReference("review")
                            .child(expenditure.getIdPlantShop());

                    ReviewPlant reviewPlant = new ReviewPlant(
                            "",
                            expenditure.getIdPlantShop(),
                            userId,
                            "",
                            expenditure.getNameProduct(),
                            new Date(),
                            statrating.getRating(),
                            comment.getText().toString().trim()
                    );

                    reviewPlant.setNameuser(fullName);

                    reviewDetailRef.child(userId).setValue(reviewPlant).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ProductReviewActivity.this, "Đánh giá thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            });
        }

        ic_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}