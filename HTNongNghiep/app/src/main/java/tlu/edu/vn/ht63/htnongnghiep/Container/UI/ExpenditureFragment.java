package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import tlu.edu.vn.ht63.htnongnghiep.Component.Interface.OnItemExpenditureClickListener;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.ListExpenditureAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.Expenditure;
import tlu.edu.vn.ht63.htnongnghiep.Model.PlantOfUser;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.ViewModel.ExpenditureViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpenditureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenditureFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExpenditureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpenditureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpenditureFragment newInstance(String param1, String param2) {
        ExpenditureFragment fragment = new ExpenditureFragment();
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

    RecyclerView recyclerView;
    ListExpenditureAdapter adapter;
    DatabaseReference databaseExpenditureReference;
    ValueEventListener eventExpenditureListener;
    ArrayList<Expenditure> expenditureList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenditure, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        expenditureList = new ArrayList<>();

//        expenditureList.add(new Expenditure(
//                "EXP002",
//                0,
//                "SELLER002",
//                "PRODUCT002",
//                "https://example.com/image2.jpg",
//                "Nguyễn Văn A",
//                "456 Đường XYZ, Hà Nội",
//                new Date(),
//                0,
//                "Sản phẩm A",
//                1,
//                200.0f,
//                200.0f
//        ));
//        expenditureList.add(new Expenditure(
//                "EXP003",
//                1,
//                "SELLER003",
//                "PRODUCT003",
//                "https://example.com/image2.jpg",
//                "Nguyễn Văn B",
//                "456 Đường XYZ, Hà Nội",
//                new Date(),
//                2,
//                "Sản phẩm B",
//                1,
//                200.0f,
//                200.0f
//        ));
//        expenditureList.add(new Expenditure(
//                "EXP004",
//                0,
//                "SELLER003",
//                "PRODUCT003",
//                "https://example.com/image2.jpg",
//                "Nguyễn Văn C",
//                "456 Đường XYZ, Hà Nội",
//                new Date(),
//                1,
//                "Sản phẩm C",
//                1,
//                200.0f,
//                200.0f
//        ));

        ExpenditureViewModel expenditureViewModel =
                new ViewModelProvider(requireActivity()).get(ExpenditureViewModel.class);

        adapter = new ListExpenditureAdapter(getContext(),expenditureViewModel);
        recyclerView.setAdapter(adapter);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        databaseExpenditureReference = FirebaseDatabase.getInstance().getReference("expenditure").child(userId);
        eventExpenditureListener = databaseExpenditureReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                expenditureList.clear();
                for (DataSnapshot idExpenditureSnapshot : snapshot.getChildren()) {
                    Expenditure expenditure = idExpenditureSnapshot.getValue(Expenditure.class);
                    if (expenditure != null) {
                        expenditureList.add(expenditure);
                    }
                }
                expenditureViewModel.setData(expenditureList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error: " + error.getMessage());
            }
        });

        adapter.setOnItemClickListener(new OnItemExpenditureClickListener() {
            @Override
            public void onItemClick(Expenditure expenditure) {
                if (expenditure.getViewType() == Expenditure.TYPE_BUY){
                    Fragment expenditureDetailFragment = new ExpenditureDetailFragment(expenditure);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main, expenditureDetailFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }else {
                    Fragment productDetailFragment = new ProductDetailFragment(expenditure);;
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.main, productDetailFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        return view;
    }
}