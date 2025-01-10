package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.HomeAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.HomeModel;

public class PostFragment extends Fragment {
    private RecyclerView recyclerView;
    HomeAdapter adapter;
    private DatabaseReference databaseReference;
    private List<HomeModel> list;
    public PostFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        init(view);
        list = new ArrayList<>();
        getListPostData();
        list.add(new HomeModel("Trees Of Life", "https://www.w3schools.com/w3images/avatar3.png", "Admin", "1 hours ago", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQhYg36JNEl--Lu9bUDVWF9SlG_64SSAhOINw&s", "Hello", "Hello", "1 hours ago", 100, 8, 2));
        list.add(new HomeModel("Triệu Thị Hậu", "https://www.w3schools.com/w3images/avatar2.png", "User", "2 hours ago", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSCyXg5x44xE1Kkenhwi3B3nefm1IisIaY1rQ&s", "Nature", "Nature is beautiful", "2 hours ago", 10, 5, 2));
        list.add(new HomeModel("Nguyễn Phương Mai", "https://www.w3schools.com/w3images/avatar6.png", "User", "3 hours ago", "hhttps://mowgarden.com/wp-content/uploads/2021/04/Monstera-adansonii-Philodendron-Monkey-Mask-MOWGARDEN-2.jpg", "Nature", "Nature is beautiful", "3 hours ago", 10, 5, 2));
        adapter = new HomeAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
    }
    private void getListPostData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Post");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear existing data
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot postSnapshot : userSnapshot.getChildren()) {
                        // Map the data from Firebase to HomeModel
                        HomeModel model = postSnapshot.getValue(HomeModel.class);
                        list.add(model);
                    }
                }
                adapter.notifyDataSetChanged(); // Notify adapter for data changes
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }
    private void init(View view){
        recyclerView = requireView().findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}