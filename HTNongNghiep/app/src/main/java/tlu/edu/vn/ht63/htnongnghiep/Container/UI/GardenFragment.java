package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Activity.AddPlant;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.PlantOfUserAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.PlantOfUser;
import tlu.edu.vn.ht63.htnongnghiep.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GardenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GardenFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    FloatingActionButton btn_add;
    RecyclerView recyclerView;
    List<PlantOfUser> plantOfUserList;
    DatabaseReference databaseReference;
    PlantOfUserAdapter plantOfUserAdapter;
    ValueEventListener eventListener;

    public GardenFragment() {
        // Required empty public constructor
    }

    public static GardenFragment newInstance(String param1, String param2) {
        GardenFragment fragment = new GardenFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_garden, container, false);

        btn_add = view.findViewById(R.id.btn_add);
        recyclerView = view.findViewById(R.id.recyclerViewPlant);

        // Thiết lập GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Thiết lập AlertDialog
//        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
//        builder.setCancelable(false);
//        AlertDialog dialog = builder.create();
//        dialog.show();

        // Tạo danh sách và adapter
        plantOfUserList = new ArrayList<>();
        plantOfUserAdapter = new PlantOfUserAdapter(getActivity(), plantOfUserList);
        recyclerView.setAdapter(plantOfUserAdapter);

        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);


//        if (userId == null) {
//            Toast.makeText(requireContext(), "User ID is missing", Toast.LENGTH_SHORT).show();
//            return;
//        }

        // Tham chiếu tới Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("PlantOfUser").child(userId);
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                plantOfUserList.clear();
                for (DataSnapshot idPlantSnapshot : snapshot.getChildren()) {
                    PlantOfUser plantOfUser = idPlantSnapshot.getValue(PlantOfUser.class);
                    if (plantOfUser != null) {
                        plantOfUserList.add(plantOfUser);
                    }
                }
                plantOfUserAdapter.notifyDataSetChanged();
//                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                dialog.dismiss();
                Log.e("FirebaseError", "Error: " + error.getMessage());
            }
        });

        // Xử lý sự kiện click cho nút thêm
        btn_add.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddPlant.class);
            startActivity(intent);
        });

        return view;
    }
}