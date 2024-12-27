package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Model.InforTreeFeature;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class analysis_featureInforTree_adapter extends RecyclerView.Adapter<analysis_featureInforTree_adapter.FeatureViewHolder> {
    private List<InforTreeFeature> list;

    public analysis_featureInforTree_adapter(List<InforTreeFeature> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public FeatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_dropdown_text, parent, false);
        return new analysis_featureInforTree_adapter.FeatureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureViewHolder holder, int position) {
        InforTreeFeature inforTreeFeature = this.list.get(position);
        holder.nameOfFeature.setText(inforTreeFeature.getName());
        holder.contentText.setText(inforTreeFeature.getContent());
        holder.flexBoxFeature.setOnClickListener(new View.OnClickListener() {
            private boolean isExpanded = false;

            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    holder.contentText.setVisibility(View.GONE);
                    holder.dropdownImage.animate().rotation(0).setDuration(300).start();
                } else {
                    holder.contentText.setVisibility(View.VISIBLE);
                    holder.dropdownImage.animate().rotation(180).setDuration(300).start();
                }
                isExpanded = !isExpanded;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public class FeatureViewHolder extends RecyclerView.ViewHolder {
        TextView nameOfFeature;
        TextView contentText;
        ImageView dropdownImage;
        View flexBoxFeature;

        public FeatureViewHolder(@NonNull View itemView) {
            super(itemView);
            nameOfFeature = itemView.findViewById(R.id.nameOfFeature);
            contentText = itemView.findViewById(R.id.contentText);
            dropdownImage = itemView.findViewById(R.id.dropdownImage);
            flexBoxFeature = itemView.findViewById(R.id.flexBoxFeature);
        }
    }
}
