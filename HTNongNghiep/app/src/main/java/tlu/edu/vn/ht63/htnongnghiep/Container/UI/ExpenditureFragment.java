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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import tlu.edu.vn.ht63.htnongnghiep.Model.Revenue;
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
                if(isAdded()){
                    expenditureViewModel.setData(expenditureList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error: " + error.getMessage());
            }
        });
        EditText edt = requireActivity().findViewById(R.id.searchBar);
        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    databaseExpenditureReference = FirebaseDatabase.getInstance().getReference("expenditure").child(userId);
                    eventExpenditureListener = databaseExpenditureReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            expenditureList.clear();
                            for (DataSnapshot idRevenueSnapshot : snapshot.getChildren()) {
                                Expenditure revenue = idRevenueSnapshot.getValue(Expenditure.class);
                                if (revenue != null && (revenue.getNameProduct().toLowerCase().contains(s.toString().toLowerCase())|| revenue.getNameSeller().toLowerCase().contains(s.toString().toLowerCase()) )) {
                                    expenditureList.add(revenue);
                                }
                            }
                            expenditureViewModel.setData(expenditureList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("FirebaseError", "Error: " + error.getMessage());
                        }
                    });
                }
                else{
                    databaseExpenditureReference = FirebaseDatabase.getInstance().getReference("expenditure").child(userId);
                    eventExpenditureListener = databaseExpenditureReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            expenditureList.clear();
                            for (DataSnapshot idRevenueSnapshot : snapshot.getChildren()) {
                                Expenditure revenue = idRevenueSnapshot.getValue(Expenditure.class);
                                if (revenue != null) {
                                    expenditureList.add(revenue);
                                }
                            }
                            expenditureViewModel.setData(expenditureList);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e("FirebaseError", "Error: " + error.getMessage());
                        }
                    });
                }
            }
        });

        expenditureViewModel.setData(expenditureList);

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