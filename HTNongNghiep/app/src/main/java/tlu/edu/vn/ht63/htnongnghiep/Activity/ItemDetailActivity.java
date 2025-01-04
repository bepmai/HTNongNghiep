package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import tlu.edu.vn.ht63.htnongnghiep.R;

public class ItemDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_product);

        TextView nameTextView = findViewById(R.id.detailButton);
        TextView ratingTextView = findViewById(R.id.rating_text);
        ImageView imageView = findViewById(R.id.img1);
        ImageView image1 = findViewById(R.id.img2);
        ImageView image2 = findViewById(R.id.img3);
        ImageView image3 = findViewById(R.id.img4);

        Intent intent = getIntent();
        nameTextView.setText(intent.getStringExtra("plant_name"));
        ratingTextView.setText("Rating: " + intent.getStringExtra("plant_rating"));
        int plantImage = intent.getIntExtra("plant_image", R.drawable.tree);
        imageView.setImageResource(plantImage);
        image1.setImageResource(plantImage);
        image2.setImageResource(plantImage);
        image3.setImageResource(plantImage);
    }
}