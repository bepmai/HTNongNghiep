package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.FriendAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.FriendSuggestAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.Friend;
import tlu.edu.vn.ht63.htnongnghiep.Model.FriendSuggest;


public class FriendFragment extends Fragment {
    private RecyclerView recyclerView, recyclerViewSuggest;
    FriendAdapter adapter;
    FriendSuggestAdapter adapterSuggest;
    private List<Friend> list;
    private List<FriendSuggest> listFriendSuggest;
    public FriendFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friend, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initSuggest(view);
        list = new ArrayList<>();
        listFriendSuggest = new ArrayList<>();
        list.add(new Friend("John Doe", "https://www.w3schools.com/w3images/avatar2.png", "Admin", "2 hours ago", "10 friends"));
        list.add(new Friend("Jane Doe", "https://www.w3schools.com/w3images/avatar6.png", "User", "3 hours ago", "5 friends"));
        adapter = new FriendAdapter(list, getContext());
        listFriendSuggest.add(new FriendSuggest("https://www.w3schools.com/w3images/avatar2.png", "https://www.w3schools.com/w3images/avatar2.png", "Admin", "John Doe", "10 mutual friends"));
        listFriendSuggest.add(new FriendSuggest("https://www.w3schools.com/w3images/avatar6.png", "https://www.w3schools.com/w3images/avatar6.png", "User", "Jane Doe", "5 mutual friends"));
        listFriendSuggest.add(new FriendSuggest("https://www.w3schools.com/w3images/avatar2.png", "https://www.w3schools.com/w3images/avatar2.png", "Admin", "John Doe", "10 mutual friends"));
        adapterSuggest = new FriendSuggestAdapter(listFriendSuggest, getContext());
        recyclerView.setAdapter(adapter);
        recyclerViewSuggest.setAdapter(adapterSuggest);
    }
    private void initSuggest(View view){
        recyclerViewSuggest = requireView().findViewById(R.id.recycler_view_suggest);
        recyclerViewSuggest.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerViewSuggest.setLayoutManager(gridLayoutManager);
    }
    private void init(View view){
        recyclerView = requireView().findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}