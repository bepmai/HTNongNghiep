package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

import tlu.edu.vn.ht63.htnongnghiep.Component.Subcomponent.ToastFragment;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class LogInActivity extends AppCompatActivity {
    ImageButton backButton;
    LinearLayout changeToSignUp;
    Button logInButton;
    EditText signin_username,signin_password;
    private FirebaseAuth auth;
    boolean checkUserInDB = false;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backButton = findViewById(R.id.backButton);
        changeToSignUp = findViewById(R.id.changeToSignUp);
        logInButton = findViewById(R.id.logInButton);
        signin_username = findViewById(R.id.signin_username);
        signin_password = findViewById(R.id.signin_password);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("inforUser");


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        changeToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        funAddTextChangedListener(signin_username,signin_password);
        funAddTextChangedListener(signin_password,signin_username);


        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signin_username.getText().toString().trim();
                String pass = signin_password.getText().toString().trim();

                if(checkLogIn()){
                    auth.signInWithEmailAndPassword(email, pass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    String userId = auth.getCurrentUser().getUid();

                                    SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("userId", userId);
                                    editor.apply();

                                    reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists()) {
                                                Log.d("User Check", "User exists in the database.");
                                                checkUserInDB = true;
                                            } else {
                                                Log.d("User Check", "User does not exist in the database.");
                                            }
                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            Log.e("Database Error", databaseError.getMessage());
                                            Toast.makeText(LogInActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    ToastFragment toastFragment = new ToastFragment(1, "Đăng nhập thành công!");
                                    toastFragment.setOnToastDismissListener(() -> {
                                        if(checkUserInDB){
                                            Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            Intent intent = new Intent(LogInActivity.this, AddInfoUserActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                    toastFragment.showToast(getSupportFragmentManager(),R.id.main);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    ToastFragment toastFragment = new ToastFragment(3, "Đăng nhập thất bại!");
                                    toastFragment.showToast(getSupportFragmentManager(),R.id.main);
                                }
                            });
                }
            }
        });
    }

    private void funAddTextChangedListener(EditText editText1, EditText editText2){
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count>0 & editText2.getText().length()>0){
                    logInButton.setBackgroundColor(getResources().getColor(R.color.green));
                    logInButton.setClickable(true);
                }else {
                    logInButton.setBackgroundColor(getResources().getColor(R.color.white_gray));
                    logInButton.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private boolean checkLogIn(){
        String user = signin_username.getText().toString().trim();
        String pass = signin_password.getText().toString().trim();

        boolean check = true;
        if (user.isEmpty()){
            signin_username.setError("Tài khoản không được để trống");
            check = false;
        }
        if (pass.isEmpty()){
            signin_password.setError("Mật khẩu không được để trống");
            check = false;
        }
        if (!isValidPhone(user)&&!isValidEmail(user)){
            signin_username.setError("Tài khoản không hợp lệ");
            check = false;
        }
        if(pass.length()<6){
            signin_password.setError("Mật khẩu không hợp lệ");
            check = false;
        }
        return check;
    }


    public boolean isValidPhone(String phone) {
        String phonePattern = "^(0[3|5|7|8|9])+([0-9]{8})$";
        return Pattern.matches(phonePattern, phone);
    }

    public boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailPattern, email);
    }

    private void navigateToHomeActivity() {
        Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToAddInfoUserActivity() {
        Intent intent = new Intent(LogInActivity.this, AddInfoUserActivity.class);
        startActivity(intent);
        finish();
    }
}