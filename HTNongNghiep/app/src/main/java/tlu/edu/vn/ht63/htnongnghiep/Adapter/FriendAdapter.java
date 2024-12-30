package tlu.edu.vn.ht63.htnongnghiep.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.Model.Friend;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendHolder> {
    private List<Friend> list;

    Context context;
    public FriendAdapter(List<Friend> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_request, parent, false);
        return new FriendHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendHolder holder, int position) {
        holder.userName.setText(list.get(position).getName());
        holder.time.setText(list.get(position).getTime());
        holder.position.setText(list.get(position).getPosition());
        holder.friendCount.setText(list.get(position).getFriendCount());
        Glide.with(context.getApplicationContext())
                .load(list.get(position).getImage())
                .placeholder(R.drawable.ic_ellipse)
                .into(holder.profileImage);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class FriendHolder extends  RecyclerView.ViewHolder{
        private CircleImageView profileImage;
        private TextView userName, time, position, friendCount;

        public FriendHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            userName = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            position = itemView.findViewById(R.id.position);
            friendCount = itemView.findViewById(R.id.mutualFriends);

        }
    }
}
