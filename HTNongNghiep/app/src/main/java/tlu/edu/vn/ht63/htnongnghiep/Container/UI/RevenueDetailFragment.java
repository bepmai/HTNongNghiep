package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import tlu.edu.vn.ht63.htnongnghiep.Model.PlantOfUser;
import tlu.edu.vn.ht63.htnongnghiep.Model.Revenue;
import tlu.edu.vn.ht63.htnongnghiep.Model.RevenueExpenditure;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.ViewModel.ExpenditureViewModel;
import tlu.edu.vn.ht63.htnongnghiep.ViewModel.RevenueViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RevenueDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RevenueDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Revenue revenue;

    public RevenueDetailFragment(Revenue revenue) {
        // Required empty public constructor
        this.revenue = revenue;
    }

    public RevenueDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RevenueDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RevenueDetailFragment newInstance(String param1, String param2) {
        RevenueDetailFragment fragment = new RevenueDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    EditText date_edt,buyer_edt,plant_edt,adress_edt,total_edt,payment_edt,totalPayment_edt,status_edt;
    ImageButton backButton;
    ImageView imageView4;
    Button saveBtn;
    DatabaseReference revenueDetailRef,expenditureDetailRef;
    RevenueViewModel revenueViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_revenue_detail, container, false);

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
        imageView4 = view.findViewById(R.id.imageView4);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("vi"));

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        if (userId == null) {
            Toast.makeText(getContext(), "User ID is missing", Toast.LENGTH_SHORT).show();
            return view;
        }

        revenueDetailRef = FirebaseDatabase.getInstance()
                .getReference("revenue")
                .child(userId);

        expenditureDetailRef = FirebaseDatabase.getInstance()
                .getReference("expenditure")
                .child(revenue.getIdBuyer())
                .child(revenue.getId());

        if (revenue!=null){
            buyer_edt.setText(revenue.getNameBuyer());
            plant_edt.setText(revenue.getNameProduct());
            adress_edt.setText(revenue.getAdress());
            Glide.with(imageView4)
                    .load(revenue.getProductImage())
                    .placeholder(R.drawable.group260) // Ảnh hiển thị khi đang tải
                    .error(R.drawable.group260)       // Ảnh hiển thị khi lỗi
                    .into(imageView4);
            if(revenue.getStatus() == RevenueExpenditure.TYPE_NOT_CONFIRMED){
                status_edt.setTextColor(getResources().getColor(R.color.black));
                status_edt.setText("Chưa xác nhận");
            }else if(revenue.getStatus() == RevenueExpenditure.TYPE_CONFIRM){
                status_edt.setTextColor(getResources().getColor(R.color.search_opaque));
                status_edt.setText("Đã xác nhận");
                saveBtn.setBackgroundColor(getResources().getColor(R.color.green));
                revenueViewModel =
                        new ViewModelProvider(requireActivity()).get(RevenueViewModel.class);

                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Xác nhận thông tin")
                                .setMessage("Bạn có chắc chắn xác nhận hoá đơn này không?")
                                .setPositiveButton("Đồng ý", (dialog, which) -> agreeRevenue())
                                .setNegativeButton("Hủy", null)
                                .show();
                    }
                });
            }else if(revenue.getStatus() == RevenueExpenditure.TYPE_SUCCESS){
                status_edt.setTextColor(getResources().getColor(R.color.red));
                status_edt.setText("Thành công");
            }
            date_edt.setText(dateFormat.format(revenue.getDate()));
            total_edt.setText(String.valueOf(revenue.getTotal())+" cây");
            payment_edt.setText(revenue.getProductCost().toString()+" vnđ");
            totalPayment_edt.setText(revenue.getTotalPayment().toString()+" vnđ");
        }

        return view;
    }

    private void agreeRevenue(){
        revenue.setStatus(2);
        revenueViewModel.updateRevenue(revenue);

        revenueDetailRef.child(revenue.getId()).setValue(revenue);

        expenditureDetailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Expenditure expenditure = snapshot.getValue(Expenditure.class);
                if (expenditure == null) {
                    Toast.makeText(getContext(), "Hoá đơn chi không tồn tại", Toast.LENGTH_SHORT).show();
                }else {
                    expenditureDetailRef.setValue(expenditure).addOnCompleteListener(task -> {
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
}