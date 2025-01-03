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

import tlu.edu.vn.ht63.htnongnghiep.Adapter.ListRevenueAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Component.Interface.OnItemRevenueClickListener;
import tlu.edu.vn.ht63.htnongnghiep.Model.Expenditure;
import tlu.edu.vn.ht63.htnongnghiep.Model.Revenue;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.ViewModel.RevenueViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RevenueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RevenueFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RevenueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RevenueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RevenueFragment newInstance(String param1, String param2) {
        RevenueFragment fragment = new RevenueFragment();
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
    ListRevenueAdapter adapter;
    DatabaseReference databaseRevenueReference;
    ValueEventListener eventRevenueListener;
    ArrayList<Revenue> revenueList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_revenue, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        revenueList = new ArrayList<>();
//        revenueList.add(new Revenue(
//                "REV001",
//                "BUYER001",
//                "PRODUCT001",
//                "https://example.com/image2.jpg",
//                "Nguyễn Văn A",
//                "456 Đường XYZ, Hà Nội",
//                new Date(),
//                0,
//                "Sản phẩm A",
//                1,
//                150.0f,
//                150.0f
//        ));
//        revenueList.add(new Revenue(
//                "REV002",
//                "BUYER002",
//                "PRODUCT002",
//                "https://example.com/image2.jpg",
//                "Nguyễn Văn B",
//                "456 Đường XYZ, Hà Nội",
//                new Date(),
//                1,
//                "Sản phẩm B",
//                1,
//                150.0f,
//                150.0f
//        ));

        RevenueViewModel revenueViewModel =
                new ViewModelProvider(requireActivity()).get(RevenueViewModel.class);

        adapter = new ListRevenueAdapter(getContext(),revenueViewModel);
        recyclerView.setAdapter(adapter);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        databaseRevenueReference = FirebaseDatabase.getInstance().getReference("revenue").child(userId);
        eventRevenueListener = databaseRevenueReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                revenueList.clear();
                for (DataSnapshot idRevenueSnapshot : snapshot.getChildren()) {
                    Revenue revenue = idRevenueSnapshot.getValue(Revenue.class);
                    if (revenue != null) {
                        revenueList.add(revenue);
                    }
                }
                revenueViewModel.setData(revenueList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error: " + error.getMessage());
            }
        });

        adapter.setOnItemClickListener(new OnItemRevenueClickListener() {
            @Override
            public void onItemClick(Revenue revenue) {
                Fragment revenueDetailFragment = new RevenueDetailFragment(revenue);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main, revenueDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}