package tlu.edu.vn.ht63.social.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import tlu.edu.vn.ht63.social.R;


public class PostCreateActivity extends AppCompatActivity {

    ImageView back;
    Button bottomsheet, bottomsheet2;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_create);
        back = findViewById(R.id.thoat);
        bottomsheet = findViewById(R.id.bottom_sheet);
        bottomsheet2 = findViewById(R.id.bottom_sheet2);
        bottomsheet2.setOnClickListener(v -> {
            showDialog1();
        });

        bottomsheet.setOnClickListener(v -> {
            showDialog();
        });
        back.setOnClickListener(v -> {
            finish();
        });


    }
    private void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        LinearLayout lifeLayout = dialog.findViewById(R.id.layoutLife);
        LinearLayout experienceLayout = dialog.findViewById(R.id.layoutExperience);
        LinearLayout questionLayout = dialog.findViewById(R.id.layoutQuestion);
        LinearLayout differentLayout = dialog.findViewById(R.id.layoutChoose);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations =  R.style.DialoAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }
    private void showDialog1(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayoutobject);

        LinearLayout lifeLayout = dialog.findViewById(R.id.layoutLife);
        LinearLayout experienceLayout = dialog.findViewById(R.id.layoutExperience);
        LinearLayout questionLayout = dialog.findViewById(R.id.layoutQuestion);
        LinearLayout differentLayout = dialog.findViewById(R.id.layoutChoose);

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations =  R.style.DialoAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

}