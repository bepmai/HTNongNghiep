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

import com.bumptech.glide.Glide;

import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Activity.DetailPlant;
import tlu.edu.vn.ht63.htnongnghiep.Model.PlantOfUser;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class PlantOfUserAdapter extends RecyclerView.Adapter<MyViewHolder>{
    private Context context;
    private List<PlantOfUser> plantOfUserList;

    public PlantOfUserAdapter(Context context, List<PlantOfUser> plantOfUserList) {
        this.context = context;
        this.plantOfUserList = plantOfUserList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_plantgarden, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(plantOfUserList.get(position).getImage()).into(holder.plantImage);
        holder.plantName.setText(plantOfUserList.get(position).getNameplant());
        holder.plantAge.setText(String.valueOf(plantOfUserList.get(position).getAgeplant()));
        holder.plantHeight.setText(String.format("%.2f", plantOfUserList.get(position).getHeight()));
        holder.plantType.setText(plantOfUserList.get(position).getType());
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, DetailPlant.class);
                    intent.putExtra("Image", plantOfUserList.get(position).getImage());
                    intent.putExtra("Name", plantOfUserList.get(position).getNameplant());
                    intent.putExtra("Age", plantOfUserList.get(position).getAgeplant());
                    intent.putExtra("Height", plantOfUserList.get(position).getHeight());
//                    intent.putExtra("Type"), plantOfUserList.get(position).getType();
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return plantOfUserList.size();
    }
}
class MyViewHolder extends RecyclerView.ViewHolder{
    ImageView plantImage;
    TextView plantName, plantAge, plantHeight, plantType;
    CardView recCard;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        plantImage = itemView.findViewById(R.id.plantImage);
        plantName = itemView.findViewById(R.id.plantName);
        plantAge = itemView.findViewById(R.id.plantAge);
        plantHeight = itemView.findViewById(R.id.plantHeight);
        plantType = itemView.findViewById(R.id.plantType);
        recCard = itemView.findViewById(R.id.recCard);
    }
}

