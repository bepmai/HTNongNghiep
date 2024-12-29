package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Model.TreeLib;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class classify_tree_to_feature_adapter extends RecyclerView.Adapter<classify_tree_to_feature_adapter.ViewHolder> {
    private List<TreeLib> treelib;
    public classify_tree_to_feature_adapter(List<TreeLib> treelib){
        this.treelib = treelib;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_tree_in_viewlibr,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TreeLib treelibPos = this.treelib.get(position);

        holder.viewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.treelib.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ImageTree;
        TextView nameTree;
        Button viewDetail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ImageTree = itemView.findViewById(R.id.ImageTree);
            this.nameTree = itemView.findViewById(R.id.nameTree);
            this.viewDetail = itemView.findViewById(R.id.viewDetail);
        }
    }
}
