package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Intent.getIntent;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import tlu.edu.vn.ht63.htnongnghiep.Model.Expenditure;
import tlu.edu.vn.ht63.htnongnghiep.Model.Revenue;
import tlu.edu.vn.ht63.htnongnghiep.Model.RevenueExpenditure;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.ViewModel.ExpenditureViewModel;
import tlu.edu.vn.ht63.htnongnghiep.ViewModel.RevenueViewModel;

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

    EditText date_edt,buyer_edt,plant_edt,adress_edt,total_edt,payment_edt,totalPayment_edt,status_edt;
    ImageButton backButton;
    Button saveBtn,deleteBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenditure_detail, container, false);

        date_edt = view.findViewById(R.id.date_edt);
        buyer_edt = view.findViewById(R.id.buyer_edt);
        plant_edt = view.findViewById(R.id.plant_edt);
        status_edt = view.findViewById(R.id.status_edt);
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

//        date_edt.setOnClickListener(v -> {
//            Calendar calendar = Calendar.getInstance();
//
//            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
//                    (view1, year, month, dayOfMonth) -> {
//                        calendar.set(Calendar.YEAR, year);
//                        calendar.set(Calendar.MONTH, month);
//                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//
//                        date_edt.setText(dateFormat.format(calendar.getTime()));
//                    },
//                    calendar.get(Calendar.YEAR),
//                    calendar.get(Calendar.MONTH),
//                    calendar.get(Calendar.DAY_OF_MONTH)
//            );
//            datePickerDialog.show();
//        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        if (userId == null) {
            Toast.makeText(getContext(), "User ID is missing", Toast.LENGTH_SHORT).show();
            return view;
        }

        DatabaseReference expenditureDetailRef = FirebaseDatabase.getInstance()
                .getReference("expenditure")
                .child(userId);

        DatabaseReference revenueDetailRef = FirebaseDatabase.getInstance()
                .getReference("revenue")
                .child(userId)
                .child(expenditure.getId());

        if (expenditure!=null){
            buyer_edt.setText(expenditure.getNameSeller());
            plant_edt.setText(expenditure.getNameProduct());
            if(expenditure.getStatus() == RevenueExpenditure.TYPE_NOT_CONFIRMED){
                status_edt.setTextColor(getResources().getColor(R.color.black));
                status_edt.setText("Chưa xác nhận");

                saveBtn.setBackgroundColor(getResources().getColor(R.color.green));
                ExpenditureViewModel expenditureViewModel =
                        new ViewModelProvider(requireActivity()).get(ExpenditureViewModel.class);

                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        expenditure.setStatus(1);
                        expenditure.setAdress(adress_edt.getText().toString().trim());
                        expenditureViewModel.updateExpenditure(expenditure);

                        expenditureDetailRef.child(expenditure.getId()).setValue(expenditure);

                        revenueDetailRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Revenue revenue = snapshot.getValue(Revenue.class);
                                if (revenue == null) {
                                    Toast.makeText(getContext(), "Hoá đơn chi không tồn tại", Toast.LENGTH_SHORT).show();
                                }else {
                                    revenue.setAdress(adress_edt.getText().toString().trim());
                                    revenueDetailRef.setValue(revenue).addOnCompleteListener(task -> {
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
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e("FirebaseError", "Error: " + error.getMessage());
                            }
                        });
                    }
                });

                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        revenueDetailRef.removeValue().addOnSuccessListener(aVoid -> {
//                            Toast.makeText(getContext(), "Xoá hoá đơn thành công", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        });

                        expenditureDetailRef.child(expenditure.getId()).removeValue().addOnSuccessListener(aVoid -> {
                            Toast.makeText(getContext(), "Xoá hoá đơn thành công", Toast.LENGTH_SHORT).show();
                            if (requireActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                                requireActivity().getSupportFragmentManager().popBackStack();
                            } else {
                                requireActivity().finish();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }else if(expenditure.getStatus() == RevenueExpenditure.TYPE_CONFIRM){
                status_edt.setTextColor(getResources().getColor(R.color.search_opaque));
                status_edt.setText("Đã xác nhận");
                deleteBtn.setVisibility(View.GONE);
                adress_edt.setFocusable(false);
                adress_edt.setFocusableInTouchMode(false);
            }else if(expenditure.getStatus() == RevenueExpenditure.TYPE_SUCCESS){
                status_edt.setTextColor(getResources().getColor(R.color.red));
                status_edt.setText("Thành công");
                deleteBtn.setVisibility(View.GONE);
                adress_edt.setFocusable(false);
                adress_edt.setFocusableInTouchMode(false);
            }
            adress_edt.setText(expenditure.getAdress());
            date_edt.setText(dateFormat.format(expenditure.getDate()));
            total_edt.setText(String.valueOf(expenditure.getTotal())+" cây");
            payment_edt.setText(expenditure.getProductCost().toString()+" vnđ");
            totalPayment_edt.setText(expenditure.getTotalPayment().toString()+" vnđ");
        }
        return view;
    }
}