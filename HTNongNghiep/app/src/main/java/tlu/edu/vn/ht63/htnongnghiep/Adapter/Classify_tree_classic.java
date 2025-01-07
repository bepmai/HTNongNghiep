package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Model.TreeLib;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class Classify_tree_classic extends RecyclerView.Adapter<Classify_tree_classic.ViewHolder> {
    public List<String> list;

    private final List<TreeLib> treeLib = new ArrayList<>();
    private final List<TreeLib> treeCom = new ArrayList<>();
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    public Classify_tree_classic(List<String> list) {
        this.list = list;
    }


    private void setTreeLib() {
        this.firebaseDatabase.getReference("Plants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    treeLib.clear();
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        TreeLib tree = childSnapshot.getValue(TreeLib.class);
                        treeLib.add(tree);
                        for (int i = 0; i < treeCom.size(); i++) {
                            TreeLib treeC = treeCom.get(i);
                            assert tree != null;
                            if (treeC.getId().equals(tree.getId())) {
                                treeCom.set(i, tree);
                            }
                        }
                    }
                    Log.d("Firebase", "Load data thành công!" + treeLib.get(0).getName());
                    notifyDataSetChanged();
                } else {
                    Log.d("Firebase", "No data found!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error: " + error.getMessage());
            }
        });
    }

    public void loadTreeData() {
        setTreeLib();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_classify_tree, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.classify.setText(list.get(position));
        Classify_tree_to_feature_adapter adapterTreeCom = new Classify_tree_to_feature_adapter(treeCom);
        holder.constraintLayout10.setOnClickListener(new View.OnClickListener() {
            private boolean isExpanded = false;

            @Override
            public void onClick(View v) {

                if (isExpanded) {
                    treeCom.clear();
                    adapterTreeCom.setTreelib(treeCom);
                    holder.classify.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
                    holder.dropdownImage.animate().rotation(0).setDuration(300).start();
                    holder.dropdownImage.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.black));
                } else {
                    try {
                        treeCom.clear();
                        Log.d("Firebase", "Load data thành công!");
                        for (TreeLib tb : treeLib) {
                            if (tb.getTrunk() != null && tb.getTrunk().equals(list.get(holder.getAdapterPosition()))) {
                                treeCom.add(tb);
                            }
                        }
                        adapterTreeCom.setTreelib(treeCom);
                    } catch (Exception e) {

                    }
                    holder.treeListContent.setLayoutManager(new GridLayoutManager(holder.itemView.getContext(), 2));
                    holder.treeListContent.setAdapter(adapterTreeCom);
                    holder.dropdownImage.animate().rotation(180).setDuration(300).start();
                    holder.classify.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.active_bar));
                    holder.dropdownImage.setColorFilter(ContextCompat.getColor(holder.itemView.getContext(), R.color.active_bar));
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
        private ImageView dropdownImage;
        private TextView classify;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.constraintLayout10 = itemView.findViewById(R.id.constraintLayout10);
            this.treeListContent = itemView.findViewById(R.id.treeListContent);
            this.dropdownImage = itemView.findViewById(R.id.dropdownImage);
            this.classify = itemView.findViewById(R.id.classify);
        }
    }
}
