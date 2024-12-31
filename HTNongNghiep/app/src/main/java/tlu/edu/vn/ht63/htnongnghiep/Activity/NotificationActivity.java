package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import tlu.edu.vn.ht63.htnongnghiep.Component.Subcomponent.MenuFragment;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class NotificationActivity extends AppCompatActivity {
    private Button document, farm, friend;
    private View docView, farmView, friendView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.document = findViewById(R.id.document);
        this.farm = findViewById(R.id.farm);
        this.friend = findViewById(R.id.friend);
        this.docView = findViewById(R.id.documentView);
        this.farmView = findViewById(R.id.farmView);
        this.friendView = findViewById(R.id.friendView);

        this.document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMenuNotifcation(R.id.document);
            }
        });

        this.farm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMenuNotifcation(R.id.farm);
            }
        });
        this.friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickMenuNotifcation(R.id.friend);
            }
        });

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        View menu = getLayoutInflater().inflate(R.layout.component_menu,null);
        View menu = findViewById(R.id.menu);
        MenuFragment.setMenu(this, menu);
    }

    public void clickMenuNotifcation(int id) {
        if (R.id.document == id) {
            document.setTextColor(getColor(R.color.active_bar));
            docView.setBackgroundColor(getColor(R.color.active_bar));
            farm.setTextColor(getColor(R.color.gray));
            farmView.setBackgroundColor(getColor(R.color.white));
            friend.setTextColor(getColor(R.color.gray));
            friendView.setBackgroundColor(getColor(R.color.white));
        } else if (R.id.farm == id) {
            farm.setTextColor(getColor(R.color.active_bar));
            farmView.setBackgroundColor(getColor(R.color.active_bar));
            document.setTextColor(getColor(R.color.gray));
            docView.setBackgroundColor(getColor(R.color.white));
            friend.setTextColor(getColor(R.color.gray));
            friendView.setBackgroundColor(getColor(R.color.white));
        } else if (R.id.friend == id) {
            friend.setTextColor(getColor(R.color.active_bar));
            friendView.setBackgroundColor(getColor(R.color.active_bar));
            farm.setTextColor(getColor(R.color.gray));
            farmView.setBackgroundColor(getColor(R.color.white));
            document.setTextColor(getColor(R.color.gray));
            docView.setBackgroundColor(getColor(R.color.white));
        }
    }
}