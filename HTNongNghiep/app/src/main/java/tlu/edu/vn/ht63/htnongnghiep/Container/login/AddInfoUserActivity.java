package tlu.edu.vn.ht63.htnongnghiep.Container.login;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import tlu.edu.vn.ht63.htnongnghiep.Container.login.Adapter.CustomAdapter;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class AddInfoUserActivity extends AppCompatActivity {
    Spinner genderSpinner,plantsSpinner;
    EditText datePickerEditText;

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
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (!selectedItem.equals("Giới tính")){
                    ((TextView) view).setTextColor(getResources().getColor(R.color.black)); // Đổi thành màu bạn muốn
                }else {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.super_white_black));
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
                String selectedItem = parent.getItemAtPosition(position).toString();
                if (!selectedItem.equals("Chọn loại cây")){
                    ((TextView) view).setTextColor(getResources().getColor(R.color.black)); // Đổi thành màu bạn muốn
                }else {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.super_white_black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}