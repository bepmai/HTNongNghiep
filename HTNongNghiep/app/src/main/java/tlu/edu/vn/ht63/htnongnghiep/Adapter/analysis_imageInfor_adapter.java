package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.R;

public class analysis_imageInfor_adapter extends RecyclerView.Adapter<analysis_imageInfor_adapter.ImageViewHolder> {
    private List<Integer> imageResourceList;

    public analysis_imageInfor_adapter(List<Integer> imageInfoList) {
        this.imageResourceList = imageInfoList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_image, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder,int position){
        int imageResId = imageResourceList.get(position);
        holder.imageView.setImageResource(imageResId);
    }

    @Override
    public int getItemCount(){
        return imageResourceList.size();
    }
    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}