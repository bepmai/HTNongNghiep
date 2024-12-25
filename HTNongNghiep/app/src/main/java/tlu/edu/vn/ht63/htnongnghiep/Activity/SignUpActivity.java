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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import tlu.edu.vn.ht63.htnongnghiep.R;

public class SignUpActivity extends AppCompatActivity {
    ImageButton backButton;
    LinearLayout changeToLogIn;
    EditText signin_username,signin_password,re_signin_password;
    Button signUpButton;
    CheckBox checkBox;

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
                if (signin_username.getText().length()>0
                        & signin_password.getText().length()>0
                        & re_signin_password.getText().length()>0
                        & checkBox.isChecked()) {
                    Intent intent = new Intent(SignUpActivity.this, MessageSignUpSucessActivity.class);
                    startActivity(intent);
                    finish();
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
}