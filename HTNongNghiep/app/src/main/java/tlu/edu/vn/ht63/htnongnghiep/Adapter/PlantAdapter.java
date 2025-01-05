package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Activity.ItemDetailActivity;
import tlu.edu.vn.ht63.htnongnghiep.Model.Plant;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {
    private List<Plant> plantList;
    private final OnItemClickListener listener;

    public PlantAdapter(List<Plant> plantList, OnItemClickListener listener) {
        this.plantList = plantList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_item, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Plant plant = plantList.get(position);
        holder.name.setText(plant.getName());
        holder.price.setText(plant.getPrice());
        holder.rating.setText(plant.getRating().toString());
        holder.image.setImageResource(plant.getImageResId());
        holder.id.setText(plant.getId());
        holder.idPlant.setText(plant.getIdplant());
        holder.idUser.setText(plant.getUserid());
        holder.nameUser.setText(plant.getNameuser());
        holder.dateSell.setText(plant.getDatesell());
        holder.address.setText(plant.getAddress());
        holder.description.setText(plant.getDescription());

        // Thiết lập sự kiện click trực tiếp trong adapter
        holder.itemView.setOnClickListener(v -> {
            // Tạo Intent để chuyển sang activity chi tiết
            Intent intent = new Intent(holder.itemView.getContext(), ItemDetailActivity.class);
            intent.putExtra("plant_name", plant.getName());
            intent.putExtra("plant_price", plant.getPrice());
            intent.putExtra("plant_rating", plant.getRating());
            intent.putExtra("plant_image", plant.getImageResId());
            intent.putExtra("plant_id", plant.getId());
            intent.putExtra("plant_idplant", plant.getIdplant());
            intent.putExtra("plant_iduser", plant.getUserid());
            intent.putExtra("plant_nameuser", plant.getNameuser());
            intent.putExtra("plant_datesell", plant.getDatesell());
            intent.putExtra("plant_address", plant.getAddress());
            intent.putExtra("plant_description", plant.getDescription());

            // Start activity
            holder.itemView.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return plantList.size();
    }

    static class PlantViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, discount, rating, id, idPlant, idUser, nameUser, dateSell, address, description;
        ImageView image;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            discount = itemView.findViewById(R.id.product_discount);
            rating = itemView.findViewById(R.id.product_rating);
            image = itemView.findViewById(R.id.product_image);
            id = itemView.findViewById(R.id.product_id);
            idPlant = itemView.findViewById(R.id.product_idplant);
            idUser = itemView.findViewById(R.id.product_iduser);
            nameUser = itemView.findViewById(R.id.product_nameuser);
            dateSell = itemView.findViewById(R.id.product_datesell);
            address = itemView.findViewById(R.id.product_address);
            description = itemView.findViewById(R.id.product_description);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Plant plant);
    }
}