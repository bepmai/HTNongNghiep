package tlu.edu.vn.ht63.social.adapter;

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

import tlu.edu.vn.ht63.social.R;
import tlu.edu.vn.ht63.social.model.FriendSuggest;

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
        holder.name.setText(list.get(position).getName());
        holder.role.setText(list.get(position).getRole());
        holder.mutualFriends.setText(list.get(position).getMutualFriends());
        Glide.with(context.getApplicationContext())
                .load(list.get(position).getProfileImage())
                .placeholder(R.drawable.ic_ellipse)
                .into(holder.profileImage);
        Glide.with(context.getApplicationContext())
                .load(list.get(position).getBgImage())
                .placeholder(R.drawable.ic_ellipse)
                .into(holder.bgImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    static class FriendSuggestHolder extends RecyclerView.ViewHolder{
        private ImageView bgImage, profileImage;
        private TextView role, name, mutualFriends;
        public FriendSuggestHolder(@NonNull View itemView) {
            super(itemView);
            bgImage = itemView.findViewById(R.id.bg_proflile);
            profileImage = itemView.findViewById(R.id.profileimg);
            name = itemView.findViewById(R.id.username);
            role = itemView.findViewById(R.id.role_suggest);
            mutualFriends = itemView.findViewById(R.id.mutalFirend);
        }
    }
}
