package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.R;

public class analysis_imageInfor_adapter extends RecyclerView.Adapter<analysis_imageInfor_adapter.ImageViewHolder> {
    private List<String> imageResourceList;

    public analysis_imageInfor_adapter(List<String> imageInfoList) {
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
        String imageResId = imageResourceList.get(position);
        Uri uri = Uri.parse(imageResId);
        Glide.with(holder.imageView).load(uri).into(holder.imageView);
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
