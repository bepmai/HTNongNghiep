package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.Model.HomeModel;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeHolder> {
    private List<HomeModel> list;
    Context context;

    public HomeAdapter(List<HomeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false);
        return new HomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeHolder holder, int position) {
        holder.userName.setText(list.get(position).getUserName());
        holder.postTime.setText(list.get(position).getPostTime());
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        holder.userName.setTextColor(color);
        holder.postContent.setText(list.get(position).getPostContent());
        int likeCount = list.get(position).getLikeCount();
        int commentCount = list.get(position).getCommentCount();
        int shareCount = list.get(position).getShareCount();
        String likeText = "👍 " + String.valueOf(likeCount);
        String commentText = "💬 " + String.valueOf(commentCount);
        String shareText = "🔗 " + String.valueOf(shareCount);

        holder.likeCount.setText(likeText);
        holder.commentCount.setText(commentText);
        holder.shareCount.setText(shareText);

        Glide.with(context.getApplicationContext())
                .load(list.get(position).getUserImage())
                .placeholder(R.drawable.ic_ellipse)
                .into(holder.profileImage);
        Glide.with(context.getApplicationContext())
                .load(list.get(position).getPostImage())
                .placeholder(R.drawable.ic_image_trees)
                .into(holder.postImage);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    static  class HomeHolder extends RecyclerView.ViewHolder {
        private CircleImageView profileImage;
        private TextView userName, postTime, postContent, postDescription, likeCount, commentCount, shareCount;
        private ImageView postImage;
        private ImageView likeBtn, commentBtn, shareBtn,saveBtn;
        public HomeHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            postImage = itemView.findViewById(R.id.post_image);
            userName = itemView.findViewById(R.id.nameTV);
            postTime = itemView.findViewById(R.id.time);
            postContent = itemView.findViewById(R.id.postContent);
            likeCount = itemView.findViewById(R.id.likeTotal);
            commentCount = itemView.findViewById(R.id.commentTotal);
            shareCount = itemView.findViewById(R.id.shareTotal);
            likeBtn = itemView.findViewById(R.id.likeBtn);
            commentBtn = itemView.findViewById(R.id.commentBtn);
            shareBtn = itemView.findViewById(R.id.shareBtn);
            saveBtn = itemView.findViewById(R.id.saveBtn);
        }
    }
}
