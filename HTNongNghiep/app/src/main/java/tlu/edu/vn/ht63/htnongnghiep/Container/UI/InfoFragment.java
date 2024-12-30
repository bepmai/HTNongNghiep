package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Activity.PostCreateActivity;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.Activity.VideoActivity;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.HomeAdapter;
import tlu.edu.vn.ht63.social.model.HomeModel;


public class InfoFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<HomeModel> list;
    private Button btnpost, btnVideo;
    HomeAdapter adapter;

    public InfoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        list = new ArrayList<>();
        list.add(new HomeModel("John Doe", "https://www.w3schools.com/w3images/avatar2.png", "Admin", "2 hours ago", "https://www.w3schools.com/w3images/nature.jpg", "Nature", "Nature is beautiful", "2 hours ago", 10, 5, 2));
        list.add(new HomeModel("Jane Doe", "https://www.w3schools.com/w3images/avatar6.png", "User", "3 hours ago", "https://www.w3schools.com/w3images/nature.jpg", "Nature", "Nature is beautiful", "3 hours ago", 10, 5, 2));
        adapter = new HomeAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
        btnpost = view.findViewById(R.id.button2);
        btnpost.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PostCreateActivity.class);
            startActivity(intent);
        });
        btnVideo = view.findViewById(R.id.button_video);
        btnVideo.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), VideoActivity.class);
            startActivity(intent);
        });
    }
    private void init(View view){
        recyclerView = requireView().findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}