package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import tlu.edu.vn.ht63.htnongnghiep.Model.Plant;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class PlantShopAdapter extends RecyclerView.Adapter<PlantShopAdapter.PlantShopViewHolder> {
    private List<Plant> plantList;

    public PlantShopAdapter(List<Plant> plantList) {
        this.plantList = plantList;
    }

    @NonNull
    @Override
    public PlantShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_plantshop, parent, false);
        return new PlantShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantShopViewHolder holder, int position) {
        Plant plant = plantList.get(position);
        holder.productName.setText(plant.getName());
        holder.productPrice.setText(plant.getPrice());
        holder.productImage.setImageResource(plant.getImageResId());
    }

    @Override
    public int getItemCount() {
        return plantList.size();
    }

    public static class PlantShopViewHolder extends RecyclerView.ViewHolder {
        TextView productName;
        TextView productPrice;
        ImageView productImage;

        public PlantShopViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }
}