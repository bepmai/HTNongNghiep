package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Container.UI.analysis_InformationTree_view;
import tlu.edu.vn.ht63.htnongnghiep.Model.TreeLib;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class Classify_tree_to_feature_adapter extends RecyclerView.Adapter<Classify_tree_to_feature_adapter.ViewHolder> {
    private List<TreeLib> treelib;

    public Classify_tree_to_feature_adapter(List<TreeLib> treelib) {
        this.treelib = treelib;
    }

    public void setTreelib(List<TreeLib> treelib) {
        this.treelib = treelib;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_tree_in_viewlibr, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TreeLib treelibPos = this.treelib.get(position);
        holder.nameTree.setText(treelibPos.getName());
        if (treelibPos.getImages() != null && !treelibPos.getImages().isEmpty()) {
            Glide.with(holder.ImageTree).load(treelibPos.getImages().get(0)).into(holder.ImageTree);
        } else {
            Glide.with(holder.ImageTree).load(R.drawable.congtao).into(holder.ImageTree);
        }
        holder.viewDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = ((AppCompatActivity) holder.itemView.getContext()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(
                        R.anim.fade_in,
                        R.anim.fade_out
                );
                Fragment fragment = new analysis_InformationTree_view(treelibPos);
                fragmentTransaction.replace(R.id.main, fragment);
                fragmentTransaction.addToBackStack("InformationTree_view");
                fragmentTransaction.commit();
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
