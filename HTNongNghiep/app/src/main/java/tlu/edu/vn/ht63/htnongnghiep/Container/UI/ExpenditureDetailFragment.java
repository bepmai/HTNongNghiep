package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import static android.content.Intent.getIntent;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import tlu.edu.vn.ht63.htnongnghiep.Model.Expenditure;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.ViewModel.ExpenditureViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpenditureDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenditureDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Expenditure expenditure;

    public ExpenditureDetailFragment(Expenditure expenditure) {
        // Required empty public constructor
        this.expenditure = expenditure;
    }

    public ExpenditureDetailFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ExpenditureDetailFragment newInstance(Expenditure expenditure) {
        ExpenditureDetailFragment fragment = new ExpenditureDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("expenditure", expenditure);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        if (getArguments() != null) {
            expenditure = (Expenditure) getArguments().getSerializable("expenditure");
        }
    }

    EditText date_edt,buyer_edt,plant_edt,adress_edt,total_edt,payment_edt,totalPayment_edt;
    ImageButton backButton;
    Button saveBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenditure_detail, container, false);

        date_edt = view.findViewById(R.id.date_edt);
        buyer_edt = view.findViewById(R.id.buyer_edt);
        plant_edt = view.findViewById(R.id.plant_edt);
        adress_edt = view.findViewById(R.id.adress_edt);
        total_edt = view.findViewById(R.id.total_edt);
        payment_edt = view.findViewById(R.id.payment_edt);
        totalPayment_edt = view.findViewById(R.id.totalPayment_edt);
        backButton = view.findViewById(R.id.backButton);
        saveBtn = view.findViewById(R.id.saveBtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("vi"));

        date_edt.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (view1, year, month, dayOfMonth) -> {
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

        if (expenditure!=null){
            buyer_edt.setText(expenditure.getNameSeller());
            plant_edt.setText(expenditure.getNameProduct());
            adress_edt.setText(expenditure.getAdress());
            date_edt.setText(dateFormat.format(expenditure.getDate()));
            total_edt.setText(String.valueOf(expenditure.getTotal()));
            payment_edt.setText(expenditure.getProductCost().toString());
            totalPayment_edt.setText(expenditure.getTotalPayment().toString()+" vnđ");
        }

        ExpenditureViewModel expenditureViewModel =
                new ViewModelProvider(requireActivity()).get(ExpenditureViewModel.class);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expenditure expenditure_new = new Expenditure(3,
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
                if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    requireActivity().getSupportFragmentManager().popBackStack();
                } else {
                    // Nếu không có fragment trước đó, có thể kết thúc activity
                    requireActivity().finish();
                }
            }
        });
        return view;
    }
}