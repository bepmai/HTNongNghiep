package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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
import tlu.edu.vn.ht63.htnongnghiep.Model.Search;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class ChatActivity extends AppCompatActivity {
    private List<InforUser> chatList;
    private RecyclerView recyclerView;


    private ChatAdapter adapter;
    private ImageView imageView;
    private FirebaseDatabase database;
    private List<Search> list;
    private SearchView searchView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);

        // Set layout for the activity
        setContentView(R.layout.activity_chat);

        // Initialize views

        recyclerView = findViewById(R.id.chatRecyclerView);
        imageView = findViewById(R.id.menu_chat);
        searchView = findViewById(R.id.chatsearch);
        searchView.clearFocus();

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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
    }
    private void filterList(String text){
        List<InforUser> filteredList = new ArrayList<>();
        for (InforUser search: chatList){
            if(search.getFullName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(search);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }else{
            adapter.setFilteredList(filteredList);
        }
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