package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import tlu.edu.vn.ht63.htnongnghiep.Activity.AddPlant;
import tlu.edu.vn.ht63.htnongnghiep.Model.Expenditure;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.ViewModel.ExpenditureViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Expenditure expenditure;

    public ProductDetailFragment() {
        // Required empty public constructor
    }

    public ProductDetailFragment(Expenditure expenditure) {
        // Required empty public constructor
        this.expenditure = expenditure;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductDetailFragment newInstance(String param1, String param2) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
    }

    EditText date_edt,plant_edt,adress_edt,total_edt,payment_edt,totalPayment_edt;
    ImageButton backButton;
    Button saveBtn,deleteBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        date_edt = view.findViewById(R.id.date_edt);
        plant_edt = view.findViewById(R.id.plant_edt);
        adress_edt = view.findViewById(R.id.adress_edt);
        total_edt = view.findViewById(R.id.total_edt);
        payment_edt = view.findViewById(R.id.payment_edt);
        totalPayment_edt = view.findViewById(R.id.totalPayment_edt);
        backButton = view.findViewById(R.id.backButton);
        saveBtn = view.findViewById(R.id.saveBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("vi"));

        date_edt.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    getContext(),
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
            plant_edt.setText(expenditure.getNameProduct());
            adress_edt.setText(expenditure.getAdress());
            date_edt.setText(dateFormat.format(expenditure.getDate()));
            total_edt.setText(String.valueOf(expenditure.getTotal()));
            payment_edt.setText(expenditure.getProductCost().toString());
            totalPayment_edt.setText(expenditure.getTotalPayment().toString()+" vnđ");
        }else {
            deleteBtn.setVisibility(View.GONE);
        }

        ExpenditureViewModel expenditureViewModel =
                new ViewModelProvider(requireActivity()).get(ExpenditureViewModel.class);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        if (userId == null) {
            Toast.makeText(getContext(), "User ID is missing", Toast.LENGTH_SHORT).show();
            return view;
        }

        DatabaseReference productDetailRef = FirebaseDatabase.getInstance()
                .getReference("expenditure")
                .child(userId);

        String productDetailId = productDetailRef.push().getKey();

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                try {
                    // Chuyển đổi chuỗi thành đối tượng Date
                    date = dateFormat.parse(date_edt.getText().toString());
                } catch (ParseException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                String plantText = plant_edt.getText().toString().trim();
                if (plantText.isEmpty() || plantText ==null){
                    plantText = "Không có";
                }
                Expenditure expenditure_new = new Expenditure(
                        productDetailId,
                        1,
                        "",
                        "",
                        "",
                        "",
                        adress_edt.getText().toString().trim(),
                        date,
                        0,
                        plantText,
                        Integer.parseInt(total_edt.getText().toString()),
                        Float.parseFloat(payment_edt.getText().toString().trim()),
                        Float.parseFloat(totalPayment_edt.getText().toString().trim()));
                expenditureViewModel.addExpenditure(expenditure_new);

                productDetailRef.child(productDetailId).setValue(expenditure_new)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), "Lưu thông tin thành công", Toast.LENGTH_SHORT).show();
                                if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                                    requireActivity().getSupportFragmentManager().popBackStack();
                                } else {
                                    requireActivity().finish();
                                }
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productDetailRef.child(productDetailId).removeValue().addOnSuccessListener(aVoid -> {
                            Toast.makeText(getContext(), "Xoá hoá đơn thành công", Toast.LENGTH_SHORT).show();
                            if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                                requireActivity().getSupportFragmentManager().popBackStack();
                            } else {
                                requireActivity().finish();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        });;
            }
        });

        return view;
    }
}