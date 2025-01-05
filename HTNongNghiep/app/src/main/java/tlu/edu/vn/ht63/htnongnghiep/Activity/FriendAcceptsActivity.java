package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Adapter.FriendAcceptAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.Friend;
import tlu.edu.vn.ht63.htnongnghiep.databinding.ActivityFriendAcceptsBinding;

import tlu.edu.vn.ht63.htnongnghiep.R;

public class FriendAcceptsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FriendAcceptAdapter adapter;
    private List<Friend> list;
    private ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_accepts);
        FirebaseApp.initializeApp(this);
        recyclerView = findViewById(R.id.recyclerViewFriendAccept);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FriendAcceptAdapter(list, this);
        recyclerView.setAdapter(adapter);
        loadFriendAccept();
        backButton = findViewById(R.id.back_friend);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Ends the current activity and returns to the previous one (MainActivity)
            }
        });
    }
    private void loadFriendAccept(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String currentUserId = currentUser.getUid();
        DatabaseReference friendAcceptRef = FirebaseDatabase.getInstance().getReference("FriendRequest");
        friendAcceptRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot reciverSnapshot : snapshot.getChildren()){
                    String senderId = reciverSnapshot.child("senderId").getValue(String.class);
                    String senderName = reciverSnapshot.child("senderName").getValue(String.class);
                    String status = reciverSnapshot.child("status").getValue(String.class);
                    if(status != null && status.equals("accepted")){
                        Friend friend = new Friend();
                        friend.setFriendId(senderId);
                        friend.setName(senderName);
                        friend.setStatus(status);
                        list.add(friend);
                    }else{
                        Log.d("Friend Activtiy", "Status is null");
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FriendAccepts Activity", "DatabaseError: " + error.getMessage());
            }
        });
    }
}