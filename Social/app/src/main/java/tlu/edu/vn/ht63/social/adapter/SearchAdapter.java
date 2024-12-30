package tlu.edu.vn.ht63.social.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import tlu.edu.vn.ht63.social.R;
import tlu.edu.vn.ht63.social.model.Search;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {
    private List<Search> list;
    Context context;
    public SearchAdapter(List<Search> list, Context context){
        this.list = list;
        this.context = context;
    }
    @NonNull
    @Override
    public SearchAdapter.SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new SearchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.SearchHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        Glide.with(context.getApplicationContext())
                .load(list.get(position).getProfileImage())
                .placeholder(R.drawable.ic_ellipse)
                .into(holder.profileImage);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class SearchHolder extends RecyclerView.ViewHolder {
        ImageView profileImage;
        TextView name;
        public SearchHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profileImage);
            name = itemView.findViewById(R.id.name);
        }
    }
}
