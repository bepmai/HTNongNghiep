package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import tlu.edu.vn.ht63.htnongnghiep.R;

public class SlpashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_slpash);

        // Kiểm tra trạng thái người dùng đã vào lần đầu chưa
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        boolean isFirstTime = prefs.getBoolean("isFirstTime", true); // Mặc định là true nếu lần đầu

        // Lắng nghe thay đổi hệ thống
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;
                if (isFirstTime) {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                    String userId = sharedPreferences.getString("userId", null);
                    if (userId == null){
                        intent = new Intent(SlpashActivity.this, LoginSignupActivity.class);
                    }else {
                        intent = new Intent(SlpashActivity.this, HomeActivity.class);
                    }
                } else {
                    intent = new Intent(SlpashActivity.this, NavigationActivity.class);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("isFirstTime", true);
                    editor.apply();
                }
                startActivity(intent);
                finish();
            }
        }, 3000); // Đợi 3 giây trước khi chuyển Activity
    }
}