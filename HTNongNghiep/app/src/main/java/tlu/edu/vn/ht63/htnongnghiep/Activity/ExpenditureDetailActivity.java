package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import tlu.edu.vn.ht63.htnongnghiep.Model.Expenditure;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.ViewModel.ExpenditureViewModel;

public class ExpenditureDetailActivity extends AppCompatActivity {
    EditText date_edt,buyer_edt,plant_edt,adress_edt,total_edt,payment_edt,totalPayment_edt;
    ImageButton backButton;
    Button saveBtn;
    ExpenditureViewModel expenditureViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_expenditure_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        date_edt = findViewById(R.id.date_edt);
        buyer_edt = findViewById(R.id.buyer_edt);
        plant_edt = findViewById(R.id.plant_edt);
        adress_edt = findViewById(R.id.adress_edt);
        total_edt = findViewById(R.id.total_edt);
        payment_edt = findViewById(R.id.payment_edt);
        totalPayment_edt = findViewById(R.id.totalPayment_edt);
        backButton = findViewById(R.id.backButton);
        saveBtn = findViewById(R.id.saveBtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("vi"));

        date_edt.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        date_edt.setText(dateFormat.format(calendar.getTime()));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );
            datePickerDialog.show();
        });

        Expenditure expenditure = (Expenditure) getIntent().getSerializableExtra("expenditure");
        if (expenditure!=null){
            buyer_edt.setText(expenditure.getNameSeller());
            plant_edt.setText(expenditure.getNameProduct());
            adress_edt.setText(expenditure.getAdress());
            date_edt.setText(dateFormat.format(expenditure.getDate()));
            total_edt.setText(expenditure.getTotal());
            payment_edt.setText(expenditure.getProductCost().toString());
            totalPayment_edt.setText(expenditure.getTotalPayment().toString()+" vnđ");
        }

        expenditureViewModel = new ViewModelProvider(
                (ViewModelStoreOwner) getApplication()).get(ExpenditureViewModel.class);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expenditure expenditure_new = new Expenditure(
                        Expenditure.TYPE_BUY,
                        "https://example.com/image1.png",
                        "Seller A",
                        "Hà Nội",
                        new Date(),
                        "Đã thanh toán",
                        "Product A",
                        10,
                        101,
                        5000.0f,
                        50000.0f);
                expenditureViewModel.addExpenditure(expenditure_new);
                onBackPressed();
            }
        });
    }
}