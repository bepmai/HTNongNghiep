package tlu.edu.vn.ht63.htnongnghiep.Container.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.Container.signup.signUpActivity;

public class LogInActivity extends AppCompatActivity {
    ImageButton backButton;
    LinearLayout changeToSignUp;
    Button logInButton;
    EditText signin_username,signin_password;

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

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        changeToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, signUpActivity.class);
                startActivity(intent);
                finish();
            }
        });

        funAddTextChangedListener(signin_username,signin_password);
        funAddTextChangedListener(signin_password,signin_username);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signin_username.getText().length()>0 & signin_password.getText().length()>0){
                    Intent intent = new Intent(LogInActivity.this, AddInfoUserActivity.class);
                    startActivity(intent);
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
}