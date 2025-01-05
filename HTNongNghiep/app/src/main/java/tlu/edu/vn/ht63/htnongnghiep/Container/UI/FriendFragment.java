package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.FriendAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.FriendSuggestAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.Friend;
import tlu.edu.vn.ht63.htnongnghiep.Model.FriendSuggest;

public class FriendFragment extends Fragment {
    private RecyclerView recyclerView, recyclerViewSuggest;
    private FriendAdapter adapter;
    private FriendSuggestAdapter adapterSuggest;
    private List<Friend> list;
    private List<FriendSuggest> listFriendSuggest;
    private DatabaseReference databaseReference;

    private Button searchFriend;
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

        // Initialize Firebase
        FirebaseApp.initializeApp(requireContext());
        databaseReference = FirebaseDatabase.getInstance().getReference("inforUser");

        // Initialize RecyclerViews and Lists
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerViewSuggest = view.findViewById(R.id.recycler_view_suggest);
        list = new ArrayList<>();
        listFriendSuggest = new ArrayList<>();

        // Set up RecyclerViews
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSuggest.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Initialize Adapters
        adapter = new FriendAdapter(list, getContext());
        adapterSuggest = new FriendSuggestAdapter(listFriendSuggest, getContext());
        recyclerView.setAdapter(adapter);
        recyclerViewSuggest.setAdapter(adapterSuggest);

        // Load Data
        loadFriendSuggest();
        loadFriendList();

        searchFriend = view.findViewById(R.id.search_friend);
        searchFriend.setOnClickListener(v ->{
            Intent intent = new Intent(getContext(), SearchActivity.class);
            startActivity(intent);
        });
    }

    private void loadFriendSuggest() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listFriendSuggest.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FriendSuggest friend = dataSnapshot.getValue(FriendSuggest.class);
                    if (friend != null) {
                        friend.setId(dataSnapshot.getKey());
                        listFriendSuggest.add(friend);
                    } else {
                        Log.d("FriendFragment", "Null data for " + dataSnapshot.toString());
                    }
                }
                adapterSuggest.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FriendFragment", "DatabaseError: " + error.getMessage());
            }
        });
    }

    private void loadFriendList() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String currentUserId = currentUser.getUid();

        DatabaseReference friendRequestRef = FirebaseDatabase.getInstance().getReference("FriendRequest");
        friendRequestRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot receiverSnapshot : snapshot.getChildren()) {
                    String path = receiverSnapshot.getRef().toString();
                    String senderId = receiverSnapshot.child("senderId").getValue(String.class);
                    String senderName = receiverSnapshot.child("senderName").getValue(String.class);
                    String status = receiverSnapshot.child("status").getValue(String.class);
                    if(status != null && status.equals("pending")){
                        Friend friend = new Friend();
                        friend.setFirebasePath(path);
                        friend.setFriendId(senderId);
                        friend.setName(senderName);
                        friend.setStatus(status);
                        list.add(friend);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FriendFragment", "DatabaseError: " + error.getMessage());
            }
        });
    }

}
