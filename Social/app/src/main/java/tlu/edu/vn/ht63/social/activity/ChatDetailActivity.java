package tlu.edu.vn.ht63.social.activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import tlu.edu.vn.ht63.social.R;


public class ChatDetailActivity extends AppCompatActivity {
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);
        back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            finish();
        });
    }

}