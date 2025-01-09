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

import tlu.edu.vn.ht63.htnongnghiep.Model.InforProductShop;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class shop_listProduct_adapter extends RecyclerView.Adapter<shop_listProduct_adapter.ProductViewHolder> {
    private List<InforProductShop> listProduct;

    public shop_listProduct_adapter(List<InforProductShop> listProduct) {
        this.listProduct = listProduct;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_infortree_cardtreeinshop, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        InforProductShop inforProductShop = listProduct.get(position);
    }

    @Override
    public int getItemCount() {
        return listProduct.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private ImageView treeImageIntro;
        private ImageView ownerImage;
        private TextView ownerName;
        private TextView nameOfTree;
        private Button addCart;
        private View owner;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            this.treeImageIntro = itemView.findViewById(R.id.treeImageIntro);
            this.ownerName = itemView.findViewById(R.id.ownerName);
            this.owner = itemView.findViewById(R.id.owner);
            this.nameOfTree = itemView.findViewById(R.id.nameOfTree);
            this.addCart = itemView.findViewById(R.id.addCart);
            this.ownerImage = itemView.findViewById(R.id.ownerImage);
        }
    }
}
