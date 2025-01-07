package tlu.edu.vn.ht63.htnongnghiep.Adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


import tlu.edu.vn.ht63.htnongnghiep.Model.Tree;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class TreeAdapter extends RecyclerView.Adapter<TreeAdapter.TreeViewHolder> {
    private List<Tree> treeList;

    public TreeAdapter(List<Tree> treeList) {
        this.treeList = treeList;
    }

    @NonNull
    @Override
    public TreeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info_produt, parent, false);
        return new TreeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TreeViewHolder holder, int position) {
        Tree tree = treeList.get(position);
        holder.reviewerName.setText(tree.getReviewerName());
        holder.reviewDate.setText(tree.getReviewDate());
        holder.rating.setText(tree.getRating().toString());
        holder.reviewContent.setText(tree.getReviewContent());
    }
    @Override
    public int getItemCount() {
        return treeList.size();
    }

    static class TreeViewHolder extends RecyclerView.ViewHolder {
        TextView reviewerName, reviewDate, reviewContent, rating,reviewCount;
        ImageView image;

        public TreeViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewerName = itemView.findViewById(R.id.reviewer_name);
            reviewDate = itemView.findViewById(R.id.review_date);
            reviewContent = itemView.findViewById(R.id.review_content);
            rating = itemView.findViewById(R.id.rating_value);
        }
    }
}