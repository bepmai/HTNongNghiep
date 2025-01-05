package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Adapter.ChatAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.InforUser;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class ChatActivity extends AppCompatActivity {
    private List<InforUser> chatList;
    private RecyclerView recyclerView;
    private EditText searchInput;
    private ChatAdapter adapter;
    private ImageView imageView;
    private FirebaseDatabase database;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Set layout for the activity
        setContentView(R.layout.activity_chat);

        // Initialize views
        searchInput = findViewById(R.id.search);
        recyclerView = findViewById(R.id.chatRecyclerView);
        imageView = findViewById(R.id.menu_chat);

        // Set up click listener for menu icon
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(ChatActivity.this, GroupChatActivity.class);
            startActivity(intent);
        });

        // Initialize RecyclerView
        initRecyclerView();

        // Initialize Firebase database reference
        initFirebase();

        // Load chat data from Firebase
        loadChatData();
    }

    private void initRecyclerView() {
        chatList = new ArrayList<>();
        adapter = new ChatAdapter(chatList, this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initFirebase() {
        database = FirebaseDatabase.getInstance();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadChatData() {
        database.getReference("inforUser").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    InforUser chat = dataSnapshot.getValue(InforUser.class);
                    if (chat != null) {
                        chat.setUserId(dataSnapshot.getKey());
                        chatList.add(chat);
                    } else {
                        Log.d("ChatActivity", "Null data for: " + dataSnapshot.toString());
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ChatActivity", "DatabaseError: " + error.getMessage());
            }
        });
    }
}
