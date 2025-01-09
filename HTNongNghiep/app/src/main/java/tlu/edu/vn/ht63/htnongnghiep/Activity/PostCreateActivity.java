package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresExtension;
import androidx.appcompat.app.AppCompatActivity;

import android.os.ext.SdkExtensions;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import tlu.edu.vn.ht63.htnongnghiep.R;


public class PostCreateActivity extends AppCompatActivity {


    ImageView back,pic, postImage;
    Button bottomsheet, bottomsheet2;
    ActivityResultLauncher<Intent> resultLauncher;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_create);
        back = findViewById(R.id.thoat);
        bottomsheet = findViewById(R.id.bottom_sheet);
        bottomsheet2 = findViewById(R.id.bottom_sheet2);
        pic = findViewById(R.id.btnPickImage);
        registerResult();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && SdkExtensions.getExtensionVersion(Build.VERSION_CODES.R) >= 2) {
            pic.setOnClickListener(view -> pickImage());
        }

        postImage = findViewById(R.id.postContent);
        bottomsheet2.setOnClickListener(v -> {
            showDialog1();
        });

        bottomsheet.setOnClickListener(v -> {
            showDialog();
        });
        back.setOnClickListener(v -> {
            finish();
        });
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            String userId = firebaseAuth.getCurrentUser().getUid();
            String userEmail = firebaseAuth.getCurrentUser().getEmail();
            String userName = firebaseAuth.getCurrentUser().getDisplayName();


                Toast.makeText(this, "Email: " + userEmail, Toast.LENGTH_SHORT).show();


                Toast.makeText(this, "Name: " + userName, Toast.LENGTH_SHORT).show();

            Toast.makeText(this,"User ID: " + userId, Toast.LENGTH_SHORT).show();
            Log.d("PostCreateActivity","UserId" + userId);
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
    private void pickImage() {
        Intent intent  = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(intent);
    }

    private void registerResult(){
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try{
                            Uri imageUri = result.getData().getData();
                            postImage.setImageURI(imageUri);
                        }catch (Exception e){
                            Toast.makeText(PostCreateActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
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
        dialog.getWindow().getAttributes().windowAnimations =  R.style.DialogAnimation;
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
        dialog.getWindow().getAttributes().windowAnimations =  R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

}