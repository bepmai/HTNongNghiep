package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Model.FriendRequest;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.Model.FriendSuggest;

public class FriendSuggestAdapter extends RecyclerView.Adapter<FriendSuggestAdapter.FriendSuggestHolder> {
    private List<FriendSuggest> list;
    Context context;
    public FriendSuggestAdapter(List<FriendSuggest> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public FriendSuggestAdapter.FriendSuggestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_suggest, parent, false);
        return new FriendSuggestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendSuggestAdapter.FriendSuggestHolder holder, int position) {
        holder.name.setText(list.get(position).getfullName());
        //holder.role.setText(list.get(position).getRole());
        //holder.mutualFriends.setText(list.get(position).getMutualFriends());
        Glide.with(context.getApplicationContext())
                .load(list.get(position).getProfileImage())
                .placeholder(R.drawable.ic_ellipse)
                .into(holder.profileImage);
        Glide.with(context.getApplicationContext())
                .load(list.get(position).getBgImage())
                .placeholder(R.drawable.ic_ellipse)
                .into(holder.bgImage);
        holder.addFriend.setOnClickListener(v -> {
            sendFriendRequest(list.get(position).getId());
        });
    }

    private void sendFriendRequest(String friendId){
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("inforUser").child(currentUserId);
        DatabaseReference friendRequestRef = FirebaseDatabase.getInstance().getReference("FriendRequest");
        userRef.child("fullName").get().addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult().getValue() != null){
                String currentUserName = task.getResult().getValue(String.class);
                friendRequestRef.child(friendId).child(currentUserId).setValue(new FriendRequest(currentUserId, currentUserName, "pending"))
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(context, "Đã gửi lời mời kết bạn", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(context, "Lỗi khi gửi lời mời kết bạn", Toast.LENGTH_SHORT).show();
                        });
            }else{
                Toast.makeText(context, "Không thể lấy thông tin người dùng hiện tại", Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    static class FriendSuggestHolder extends RecyclerView.ViewHolder{
        private ImageView bgImage, profileImage;
        private TextView role, name, mutualFriends;
        private Button addFriend;
        public FriendSuggestHolder(@NonNull View itemView) {
            super(itemView);
            bgImage = itemView.findViewById(R.id.bg_proflile);
            profileImage = itemView.findViewById(R.id.profileimg);
            name = itemView.findViewById(R.id.username);

            addFriend = itemView.findViewById(R.id.button);
        }
    }
}
