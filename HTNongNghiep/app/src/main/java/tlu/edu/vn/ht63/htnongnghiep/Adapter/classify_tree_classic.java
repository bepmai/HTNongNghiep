package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.R;

public class classify_tree_classic extends RecyclerView.Adapter<classify_tree_classic.ViewHolder> {
    public List<String> list;

    public classify_tree_classic(List<String> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_classify_tree, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.constraintLayout10.setOnClickListener(new View.OnClickListener() {
            private boolean isExpanded = false;

            @Override
            public void onClick(View v) {

                if (isExpanded) {

                    //set cho recyclerview;
                    holder.dropdownImage.animate().rotation(0).setDuration(300).start();
                } else {
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View constraintLayout10;
        private RecyclerView treeListContent;
        private  ImageView dropdownImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.constraintLayout10 = itemView.findViewById(R.id.constraintLayout10);
            this.treeListContent = itemView.findViewById(R.id.treeListContent);
            this.dropdownImage = itemView.findViewById(R.id.dropdownImage);
        }
    }
}
