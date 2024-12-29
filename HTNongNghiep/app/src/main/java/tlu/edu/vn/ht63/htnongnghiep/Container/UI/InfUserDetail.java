package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.app.DatePickerDialog;
import java.util.Calendar;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import tlu.edu.vn.ht63.htnongnghiep.R;

public class InfUserDetail extends AppCompatActivity {
    EditText input_birthday;
    Spinner input_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inf_user_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
            });

        input_birthday = findViewById(R.id.input_birthday);
        input_gender = findViewById(R.id.input_gender);

        input_birthday.setOnClickListener(view -> {
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view1, year, month, dayOfMonth) -> {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("vi"));
                        input_birthday.setText(dateFormat.format(calendar.getTime()));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });
        String[] options_temperature = {"Nam", "Nu"};

        ArrayAdapter<String> adapter_temperature = new ArrayAdapter<>(this, R.layout.spinner_detail_plant_item, options_temperature);
        adapter_temperature.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        input_gender.setAdapter(adapter_temperature);
    }
}