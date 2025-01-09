package tlu.edu.vn.ht63.htnongnghiep.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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

        Glide.with(context.getApplicationContext())
                .load(list.get(position).getImage())
                .placeholder(R.drawable.ic_ellipse)
                .into(holder.profileImage);
        holder.btnAccept.setOnClickListener(v -> {
            String firbasepath = list.get(position).getFirebasePath();
            Toast.makeText(context,"Path: " + firbasepath,Toast.LENGTH_SHORT).show();
            DatabaseReference requesetRef = FirebaseDatabase.getInstance().getReferenceFromUrl(firbasepath);
            requesetRef.child("status").setValue("accepted")
                    .addOnSuccessListener(aVoid ->{
                        Toast.makeText(context, "Accepted friend request", Toast.LENGTH_SHORT).show();

                    }).addOnFailureListener(e -> {
                        Toast.makeText(context, "Failed to accept request", Toast.LENGTH_SHORT).show();

                    });
        });
        holder.btnDelete.setOnClickListener(v -> {
            String firebasepath = list.get(position).getFirebasePath();
            DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl(firebasepath);
            reference.removeValue()
                    .addOnSuccessListener(aVoid ->{
                        Toast.makeText(context, "Friend request deleted", Toast.LENGTH_SHORT).show();
                        list.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, list.size());
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Failed to delete request", Toast.LENGTH_SHORT).show();
                    });
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class
    FriendHolder extends  RecyclerView.ViewHolder{
        private CircleImageView profileImage;
        private TextView userName, time ;
        private Button btnAccept, btnDelete;

        public FriendHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            userName = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            btnAccept = itemView.findViewById(R.id.btn_accept);
            btnDelete = itemView.findViewById(R.id.btn_delete);



        }
    }
}
