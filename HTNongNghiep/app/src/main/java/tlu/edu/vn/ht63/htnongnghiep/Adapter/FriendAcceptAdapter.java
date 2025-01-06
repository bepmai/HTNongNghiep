package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Model.Friend;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class FriendAcceptAdapter extends RecyclerView.Adapter<FriendAcceptAdapter.FriendAcceptHolder> {

    private List<Friend> acceptedFriendList;
    Context context;
    public FriendAcceptAdapter(List<Friend> acceptedFriendList, Context context) {
        this.acceptedFriendList = acceptedFriendList;
        this.context = context;
    }

    @NonNull
    @Override
    public FriendAcceptHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_accept, parent, false);
        return new FriendAcceptHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendAcceptHolder holder, int position) {
        holder.username.setText(acceptedFriendList.get(position).getName());
        Glide.with(context.getApplicationContext())
                .load(acceptedFriendList.get(position).getImage())
                .placeholder(R.drawable.ic_user_chat)
                .into(holder.profileImage);
    }

    @Override
    public int getItemCount() {
        return acceptedFriendList.size();
    }

    static  class FriendAcceptHolder extends RecyclerView.ViewHolder {
        private ImageView profileImage;
        private TextView username;
        public FriendAcceptHolder(@NonNull View itemView){
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            username = itemView.findViewById(R.id.name);
        }
    }
}
