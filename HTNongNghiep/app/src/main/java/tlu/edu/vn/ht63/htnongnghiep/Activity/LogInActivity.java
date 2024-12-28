package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

import tlu.edu.vn.ht63.htnongnghiep.Component.Subcomponent.ToastFragment;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class LogInActivity extends AppCompatActivity {
    ImageButton backButton;
    LinearLayout changeToSignUp;
    Button logInButton;
    EditText signin_username,signin_password;
    private FirebaseAuth auth;

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
                String email = signin_username.getText().toString();
                String pass = signin_password.getText().toString();

                if(checkLogIn()){
                    auth.signInWithEmailAndPassword(email, pass)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    ToastFragment toastFragment = new ToastFragment(1, "Đăng nhập thành công!");
                                    toastFragment.setOnToastDismissListener(() -> {
                                        Intent intent = new Intent(LogInActivity.this, AddInfoUserActivity.class);
                                        startActivity(intent);
                                        finish();
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
}