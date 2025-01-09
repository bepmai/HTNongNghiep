package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Activity.DetailPlant;
import tlu.edu.vn.ht63.htnongnghiep.Model.PlantOfUser;
import tlu.edu.vn.ht63.htnongnghiep.Model.ReviewPlant;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class ReviewPlantAdapter extends RecyclerView.Adapter<ReviewPlantAdapter.MyViewHolder> {
    private Context context;
    private List<ReviewPlant> reviewPlantList;

    public ReviewPlantAdapter(Context context, List<ReviewPlant> reviewPlantList) {
        this.context = context;
        this.reviewPlantList = reviewPlantList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_reviewplant, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewPlantAdapter.MyViewHolder holder, int position) {
        holder.username.setText(reviewPlantList.get(position).getNameuser());
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
        holder.date.setText(dateTimeFormat.format(reviewPlantList.get(position).getDate()));
        holder.statrating.setText(reviewPlantList.get(position).getStatrating().toString() + " sao");
        holder.review.setText(reviewPlantList.get(position).getReview());

//        holder.recCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int position = holder.getAdapterPosition();
//                if (position != RecyclerView.NO_POSITION) {
//                    Intent intent = new Intent(context, DetailPlant.class);
//                    ReviewPlant reviewPlant = reviewPlantList.get(position);
//
//                    intent.putExtra("nameuser", reviewPlant.getNameuser());
//                    intent.putExtra("nameplant", reviewPlant.getNameplant());
//                    intent.putExtra("date", reviewPlant.getDate());
//                    intent.putExtra("statrating", reviewPlant.getStatrating());
//                    intent.putExtra("review", reviewPlant.getReview());
//
////                    intent.putExtra("plant",reviewPlant);
//                    context.startActivity(intent);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return reviewPlantList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView username, statrating, date, review;
        CardView recCard;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            statrating = itemView.findViewById(R.id.statrating);
            date = itemView.findViewById(R.id.date);
            review = itemView.findViewById(R.id.review);
            recCard = itemView.findViewById(R.id.recCard);
        }
    }
}
