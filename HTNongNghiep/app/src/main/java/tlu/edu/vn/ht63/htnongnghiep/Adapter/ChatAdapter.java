package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Activity.ChatDetailActivity;
import tlu.edu.vn.ht63.htnongnghiep.Model.InforUser;
import tlu.edu.vn.ht63.htnongnghiep.R;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {
    private List<InforUser> list;
    Context context;

    public ChatAdapter(List<InforUser> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {
        InforUser inforUser = list.get(position);
        //Picasso.get().load(inforUser.getImage()).placeholder(R.drawable.ic_user_chat).into(holder.image);
        String image = inforUser.getImage();
        if (image != null && !image.isEmpty()) {
            Picasso.get().load(image).placeholder(R.drawable.ic_user_chat).into(holder.image);
        } else {
            // Đặt hình ảnh mặc định nếu không có hình ảnh
            holder.image.setImageResource(R.drawable.ic_user_chat);
        }
        holder.userName.setText(list.get(position).getFullName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatDetailActivity.class);
                intent.putExtra("userId", inforUser.getUserId());
                intent.putExtra("userName", inforUser.getFullName());
                intent.putExtra("image", inforUser.getImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView userName, lastMessage;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            image = itemView.findViewById(R.id.profile_image);
            userName = itemView.findViewById(R.id.userNameList);
            lastMessage = itemView.findViewById(R.id.lastMessage);
    }


    }

}
