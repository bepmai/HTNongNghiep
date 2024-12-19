package tlu.edu.vn.ht63.htnongnghiep.Container.SlpashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import tlu.edu.vn.ht63.htnongnghiep.Container.OnboardingWalkthroughScreen.NavigationActivity;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.Container.LoginSignupActivity;

public class SlpashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_slpash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        new  Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SlpashActivity.this, NavigationActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}