package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

import tlu.edu.vn.ht63.htnongnghiep.Component.Subcomponent.ToastFragment;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class SignUpActivity extends AppCompatActivity {
    ImageButton backButton;
    LinearLayout changeToLogIn;
    EditText signin_username,signin_password,re_signin_password;
    Button signUpButton;
    CheckBox checkBox;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backButton = findViewById(R.id.backButton);
        signUpButton = findViewById(R.id.signUpButton);
        changeToLogIn = findViewById(R.id.changeToLogIn);
        signin_username = findViewById(R.id.signin_username);
        signin_password = findViewById(R.id.signin_password);
        re_signin_password = findViewById(R.id.re_signin_password);
        checkBox = findViewById(R.id.checkBox);

        auth = FirebaseAuth.getInstance();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        changeToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        funAddTextChangedListener(signin_username,signin_password,re_signin_password);
        funAddTextChangedListener(signin_password,signin_username,re_signin_password);
        funAddTextChangedListener(re_signin_password,signin_username,signin_password);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (signin_username.getText().length()>0
                        & signin_password.getText().length()>0
                        & re_signin_password.getText().length()>0
                        & isChecked){
                    signUpButton.setBackgroundColor(getResources().getColor(R.color.green));
                    signUpButton.setClickable(true);
                }else {
                    signUpButton.setBackgroundColor(getResources().getColor(R.color.white_gray));
                    signUpButton.setClickable(false);
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = signin_username.getText().toString().trim();
                String pass = signin_password.getText().toString().trim();

                if (checkSignUp()){
                    if(checkBox.isChecked()){
                        auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    ToastFragment toastFragment = new ToastFragment(1, "Đăng ký thành công!");
                                    toastFragment.setOnToastDismissListener(() -> {
                                        Intent intent = new Intent(SignUpActivity.this, MessageSignUpSucessActivity.class);
                                        startActivity(intent);
                                        finish();
                                    });
                                    toastFragment.showToast(getSupportFragmentManager(),R.id.main);
                                } else {
                                    ToastFragment toastFragment = new ToastFragment(3, "Đăng ký thất bại: " + getLocalizedErrorMessage(task.getException().getMessage()));
                                    toastFragment.showToast(getSupportFragmentManager(),R.id.main);
                                }
                            }
                        });
                    }else {
                        checkBox.setError("Bạn phải chấp nhận với điều khoản");
                    }
                }
            }
        });
    }

    private void funAddTextChangedListener(EditText editText1,EditText editText2,EditText editText3){
        editText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count>0 & editText2.getText().length()>0 & editText3.getText().length()>0 & checkBox.isChecked()){
                    signUpButton.setBackgroundColor(getResources().getColor(R.color.green));
                    signUpButton.setClickable(true);
                }else {
                    signUpButton.setBackgroundColor(getResources().getColor(R.color.white_gray));
                    signUpButton.setClickable(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private String getLocalizedErrorMessage(String errorMessage) {
        if (errorMessage.contains("email address is badly formatted")) {
            return "Địa chỉ email không đúng định dạng.";
        } else if (errorMessage.contains("The email address is already in use")) {
            return "Email này đã được sử dụng.";
        } else if (errorMessage.contains("Password should be at least 6 characters")) {
            return "Mật khẩu phải có ít nhất 6 ký tự.";
        } else if (errorMessage.contains("network error")) {
            return "Lỗi kết nối mạng. Vui lòng kiểm tra kết nối Internet.";
        } else if (errorMessage.contains("There is no user record corresponding to this identifier")) {
            return "Không tìm thấy tài khoản người dùng.";
        } else if (errorMessage.contains("The password is invalid")) {
            return "Mật khẩu không đúng.";
        } else if (errorMessage.contains("User disabled")) {
            return "Tài khoản đã bị vô hiệu hóa.";
        } else {
            return "Đăng ký thất bại. Vui lòng thử lại.";
        }
    }

    private boolean checkSignUp(){
        String user = signin_username.getText().toString().trim();
        String pass = signin_password.getText().toString().trim();
        String re_pass = re_signin_password.getText().toString().trim();

        boolean check = true;
        if (user.isEmpty()){
            signin_username.setError("Tài khoản không được để trống");
            check = false;
        }
        if (pass.isEmpty()){
            signin_password.setError("Mật khẩu không được để trống");
            check = false;
        }
        if (re_pass.isEmpty()){
            re_signin_password.setError("Mật khẩu nhập lại không được để trống");
            check = false;
        }
        if (!re_pass.equals(pass)){
            re_signin_password.setError("Mật khẩu nhập lại không khớp");
            check = false;
        }
        if (!isValidPhone(user)&&!isValidEmail(user)){
            signin_username.setError("Tài khoản không hợp lệ");
            check = false;
        }
        if(pass.length()<6){
            signin_password.setError("Mật khẩu phải dài hơn 6 ký tự");
            check = false;
        }
        if(re_pass.length()<6){
            re_signin_password.setError("Mật khẩu nhập lại phải dài hơn 6 ký tự");
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