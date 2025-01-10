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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import tlu.edu.vn.ht63.htnongnghiep.Model.HomeModel;
import tlu.edu.vn.ht63.htnongnghiep.R;


public class PostCreateActivity extends AppCompatActivity {

    TextView postContet;
    String postTime;
    ImageView back,pic, postImage;
    Button bottomsheet, bottomsheet2, postButton;
    ActivityResultLauncher<Intent> resultLauncher;
    Uri selectImageUri;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

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

        postImage = findViewById(R.id.postImage);
        postContet = findViewById(R.id.content);
        postButton = findViewById(R.id.postButton);
        databaseReference = FirebaseDatabase.getInstance().getReference("Post");
        storageReference = FirebaseStorage.getInstance().getReference("PostImages");

        postButton.setOnClickListener(v -> {
            savePostToFirebase();
        });
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
    private void savePostToFirebase(){
        String content = postContet.getText().toString().trim();
        if(content.isEmpty() && selectImageUri != null){
            Toast.makeText(this, "Nội dung hoặc hình ảnh không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        String postID = UUID.randomUUID().toString();
        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
        String userName = "";
        String userImage = "https://www.w3schools.com/w3images/avatar2.png";
        postTime = calculatePostTime(System.currentTimeMillis());
        if(selectImageUri != null){
            StorageReference imageRef  = storageReference.child(postID + ".jpg");
            imageRef.putFile(selectImageUri).addOnSuccessListener(taskSnapshot -> {
                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUri = uri.toString();
                    savePostToDatabase(postID,userId,userName,userImage,content,imageUri,postTime);
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Không thể lấy URL ảnh", Toast.LENGTH_SHORT).show();
                });
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "Tải ảnh lên thất bại", Toast.LENGTH_SHORT).show();
            });
        }else {
            savePostToDatabase(postID, userId, userName, userImage, content, null, postTime);
        }
    }
    private void savePostToDatabase(String postId, String userId, String userName,String userImage, String content, String imageUrl, String postTime){
        userName = "Trees Of Life Shop";
        HomeModel post = new HomeModel(userName , userImage, "","",imageUrl, content, "", postTime, 0, 0, 0);
        databaseReference.child(userId).child(postId).setValue(post).addOnSuccessListener(aVoid ->{
            Toast.makeText(this, "Đăng bài thành công", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(this, "Đăng bài thất bại", Toast.LENGTH_SHORT).show();
        });
    }
    private String calculatePostTime(long currentTimeMillis) {
        long difference = (System.currentTimeMillis() - currentTimeMillis) / 1000; // Tính khoảng thời gian
        if (difference < 60) {
            return difference + " giây trước";
        } else if (difference < 3600) {
            return difference / 60 + " phút trước";
        } else if (difference < 86400) {
            return difference / 3600 + " giờ trước";
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            return sdf.format(new Date(currentTimeMillis));
        }
    }
    @RequiresExtension(extension = Build.VERSION_CODES.R, version = 2)
    private void pickImage() {
        if (postImage != null) {
            postImage.setImageResource(R.drawable.ic_image_trees); // Hình ảnh mặc định
        } else {
            Log.e("PostCreateActivity", "postImage is null. Check your XML layout or ID mapping.");
        }

//        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
//        resultLauncher.launch(intent);
    }

    private void registerResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            try {
                                // Lấy URI của hình ảnh được chọn và set vào ImageView
                                Uri imageUri = result.getData().getData();
                                postImage.setImageURI(imageUri);
                            } catch (Exception e) {
                                Toast.makeText(PostCreateActivity.this, "Không thể hiển thị hình ảnh", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Nếu không chọn ảnh, tải ảnh từ URL và set vào ImageView
                            String imageUrl = "https://firebasestorage.googleapis.com/v0/b/e-learning-app-9e5d6.appspot.com/o/PlantImages%2F54?alt=media&token=c7686aa5-d242-49a1-aa9b-b414319469ea"; // Thay URL này bằng URL hình ảnh của bạn
                            loadImageFromUrl(imageUrl);
                        }
                    }
                }
        );
    }

    // Hàm tải hình ảnh từ URL
    private void loadImageFromUrl(String url) {
        Glide.with(this) // Sử dụng thư viện Glide để tải ảnh
                .load(url)
                .placeholder(R.drawable.ic_image_trees) // Hình ảnh loading (tuỳ chọn)
                .into(postImage);
    }
    private void showDialog(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);

        LinearLayout lifeLayout = dialog.findViewById(R.id.layoutLife);
        LinearLayout experienceLayout = dialog.findViewById(R.id.layoutExperience);
        LinearLayout questionLayout = dialog.findViewById(R.id.layoutQuestion);
        LinearLayout differentLayout = dialog.findViewById(R.id.layoutChoose);
        // Thiết lập sự kiện click cho từng layout
        lifeLayout.setOnClickListener(v -> {
            bottomsheet.setText("Life Object"); // Cập nhật text cho nút bottom_sheet2
            dialog.dismiss(); // Đóng dialog
        });

        experienceLayout.setOnClickListener(v -> {
            bottomsheet.setText("Experience Object");
            dialog.dismiss();
        });

        questionLayout.setOnClickListener(v -> {
            bottomsheet.setText("Question Object");
            dialog.dismiss();
        });

        differentLayout.setOnClickListener(v -> {
            bottomsheet.setText("Different Object");
            dialog.dismiss();
        });
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

        RadioButton everyone = dialog.findViewById(R.id.radioEveryone);
        RadioButton friend = dialog.findViewById(R.id.radioFriends);
        RadioButton friendOnly = dialog.findViewById(R.id.radioSpecificFriends);
        RadioButton OnlyMe = dialog.findViewById(R.id.radioOnlyMe);

        everyone.setOnClickListener(v -> {
            bottomsheet2.setText("Mọi Người");
            dialog.dismiss();
        });

        friend.setOnClickListener(v -> {
            bottomsheet2.setText("Ban bè");
            dialog.dismiss();
        });

        friendOnly.setOnClickListener(v -> {
            bottomsheet2.setText("Bạn bè cụ thể");
            dialog.dismiss();
        });
        OnlyMe.setOnClickListener(v -> {
            bottomsheet2.setText("Chỉ mình tôi");
            dialog.dismiss();
        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations =  R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

}