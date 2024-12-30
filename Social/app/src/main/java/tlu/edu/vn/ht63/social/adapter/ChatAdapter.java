package tlu.edu.vn.ht63.social.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tlu.edu.vn.ht63.social.activity.ChatDetailActivity;
import tlu.edu.vn.ht63.social.R;
import tlu.edu.vn.ht63.social.model.Chat;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatHolder> {
    private List<Chat> list;
    Context context;
    public ChatAdapter(List<Chat> list, Context context){
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public ChatAdapter.ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat, parent, false);
        return new ChatHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ChatHolder holder, int position) {
        holder.userName.setText(list.get(position).getUserName());
        holder.message.setText(list.get(position).getMessage());
        holder.timestamp.setText(list.get(position).getTimestamp());
        Glide.with(context.getApplicationContext())
                .load(list.get(position).getProfileImage())
                .placeholder(R.drawable.ic_ellipse)
                .into(holder.profileImage);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatDetailActivity.class);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    static class ChatHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private TextView userName, message, timestamp;
        public ChatHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImageChat);
            userName = itemView.findViewById(R.id.name);
            message = itemView.findViewById(R.id.message);
            timestamp = itemView.findViewById(R.id.date);
        }
    }
}
