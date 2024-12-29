package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import tlu.edu.vn.ht63.htnongnghiep.Component.Subcomponent.ToastFragment;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class AddInfoUserActivity extends AppCompatActivity {
    Spinner genderSpinner,plantsSpinner;
    EditText datePickerEditText,nameEditText,adressEditText;
    TextView genderSpinnerError,plantSpinnerError;
    Button addInfoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_info_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        genderSpinner = findViewById(R.id.genderSpinner);
        plantsSpinner = findViewById(R.id.plantsSpinner);
        datePickerEditText = findViewById(R.id.datePickerEditText);
        addInfoBtn = findViewById(R.id.addInfoBtn);
        nameEditText = findViewById(R.id.nameEditText);
        adressEditText = findViewById(R.id.adressEditText);
        genderSpinnerError = findViewById(R.id.genderSpinnerError);
        plantSpinnerError = findViewById(R.id.plantSpinnerError);

        datePickerEditText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("vi"));
                        datePickerEditText.setText(dateFormat.format(calendar.getTime()));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.gender_array,
                R.layout.spinner_item
        );

        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    ((TextView) view).setTextColor(getResources().getColor(R.color.black));
                    genderSpinnerError.setText("Bạn phải chọn một giá trị");
                    genderSpinnerError.setVisibility(View.VISIBLE);
                }else {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.super_white_black));
                    genderSpinnerError.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> planteAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.plante_array,
                R.layout.spinner_item
        );

        planteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plantsSpinner.setAdapter(planteAdapter);

        plantsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    ((TextView) view).setTextColor(getResources().getColor(R.color.black));
                    plantSpinnerError.setText("Bạn phải chọn một giá trị");
                    plantSpinnerError.setVisibility(View.VISIBLE);
                }else {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.super_white_black));
                    plantSpinnerError.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        addInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSignUp()){
                    ToastFragment toastFragment = new ToastFragment(1, "Thêm thông tin thành công!");
                    toastFragment.setOnToastDismissListener(() -> {
                        Intent intent = new Intent(AddInfoUserActivity.this, MessageSignUpSucessActivity.class);
                        startActivity(intent);
                        finish();
                    });
                    toastFragment.showToast(getSupportFragmentManager(),R.id.main);
                }
            }
        });
    }

    private boolean checkSignUp(){
        String name = nameEditText.getText().toString().trim();
        String date = datePickerEditText.getText().toString().trim();
        String adress = adressEditText.getText().toString().trim();

        boolean check = true;
        if (name.isEmpty()){
            nameEditText.setError("Tên đăng nhập không được để trống");
            check = false;
        }

        if (date.isEmpty()){
            datePickerEditText.setError("Mật khẩu không được để trống");
            check = false;
        }

        if (adress.isEmpty()){
            adressEditText.setError("Mật khẩu nhập lại không được để trống");
            check = false;
        }

        if (genderSpinner.getSelectedItemPosition() == 0){
            genderSpinnerError.setText("Bạn phải chọn một giá trị");
            genderSpinnerError.setVisibility(View.VISIBLE);
            check = false;
        } else {
            genderSpinnerError.setVisibility(View.GONE);
        }

        if(plantsSpinner.getSelectedItemPosition() == 0){
            plantSpinnerError.setText("Bạn phải chọn một giá trị");
            plantSpinnerError.setVisibility(View.VISIBLE);
            check = false;
        }else {
            plantSpinnerError.setVisibility(View.GONE);
        }

        return check;
    }
}