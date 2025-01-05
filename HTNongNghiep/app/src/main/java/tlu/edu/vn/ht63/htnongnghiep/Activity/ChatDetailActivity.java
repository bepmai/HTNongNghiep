package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import tlu.edu.vn.ht63.htnongnghiep.Adapter.ChatAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.ChatUserAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.MessageModel;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.databinding.ActivityChatDetailBinding;


public class ChatDetailActivity extends AppCompatActivity {
    ActivityChatDetailBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_chat_detail);
        binding = ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Objects.requireNonNull(getSupportActionBar()).hide();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        String userName = getIntent().getStringExtra("userName");
        String image = getIntent().getStringExtra("image");

        String senderId = auth.getCurrentUser().getUid();
        String recieveId = getIntent().getStringExtra("userId");
        Toast.makeText(this,"User ID:" + recieveId, Toast.LENGTH_SHORT).show();

        binding.userName.setText(userName);
        if (image != null && !image.isEmpty()) {
            Picasso.get().load(image).placeholder(R.drawable.ic_user_chat).into(binding.profileImage);
        } else {
            // Đặt hình ảnh mặc định nếu không có hình ảnh
            binding.profileImage.setImageResource(R.drawable.ic_user_chat);
        }

        back = findViewById(R.id.backArrow);
        back.setOnClickListener(v -> {
            finish();
        });

       final ArrayList<MessageModel> messageModels = new ArrayList<>();
       final ChatUserAdapter chatUserAdapter = new ChatUserAdapter(messageModels, this, recieveId);

        binding.chatRecyclerView.setAdapter(chatUserAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        final String senderRoom = senderId + recieveId;
        final String receiverRoom = recieveId + senderId;

        database.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();

                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            MessageModel model = snapshot1.getValue(MessageModel.class);
                            model.setMessageId(snapshot1.getKey());
                            messageModels.add(model);
                        }

                        chatUserAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("FirebaseError", error.getMessage());
                    }
                });

        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.enterMessage.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimestamp((new Date().getTime()));
                binding.enterMessage.setText(" ");

                database.getReference().child("chats")
                        .child(senderRoom)
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("chats")
                                        .child(receiverRoom)
                                        .push()
                                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                            }
                        });
            }
        });


    }

}