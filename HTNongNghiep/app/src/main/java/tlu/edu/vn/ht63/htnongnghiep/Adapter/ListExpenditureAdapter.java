package tlu.edu.vn.ht63.htnongnghiep.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Component.OnItemExpenditureClickListener;
import tlu.edu.vn.ht63.htnongnghiep.Model.Expenditure;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.ViewModel.ExpenditureViewModel;

public class ListExpenditureAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final ArrayList<Expenditure> dataList = new ArrayList<>();
    private final ExpenditureViewModel expenditureViewModel;
    private OnItemExpenditureClickListener listener;

    public ListExpenditureAdapter(Context context, ExpenditureViewModel expenditureViewModel) {
        this.context = context;
        this.expenditureViewModel = expenditureViewModel;

        expenditureViewModel.getListMutableLiveData().observe((LifecycleOwner) context, new Observer<List<Expenditure>>() {
            @Override
            public void onChanged(List<Expenditure> expenditureList) {
                if (expenditureList != null) {
                    dataList.clear();
                    dataList.addAll(expenditureList);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getViewType();
    }

    public void setOnItemClickListener(OnItemExpenditureClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Expenditure.TYPE_BUY) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_expenditure, parent, false);
            return new SellerViewHolder(view);
        } else if (viewType == Expenditure.TYPE_PRODUCT) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_product, parent, false);
            return new ProductViewHolder(view);
        }
        throw new IllegalArgumentException("Invalid view type");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Expenditure expenditure = dataList.get(position);

        if (holder instanceof SellerViewHolder) {
            ((SellerViewHolder) holder).bind(expenditure);
        } else if (holder instanceof ProductViewHolder) {
            ((ProductViewHolder) holder).bind(expenditure);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(expenditure);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // ViewHolder for Seller
    static class SellerViewHolder extends RecyclerView.ViewHolder {
        TextView listNameSeller,listStatus,listNameProduct,listTotal,listPayment,listTime,listTotalPayment;
        ImageView imageView4;

        public SellerViewHolder(@NonNull View itemView) {
            super(itemView);
            listStatus = itemView.findViewById(R.id.listStatus);
            listNameSeller = itemView.findViewById(R.id.listNameSeller);
            imageView4 = itemView.findViewById(R.id.imageView4);
            listNameProduct = itemView.findViewById(R.id.listNameProduct);
            listTotal = itemView.findViewById(R.id.listTotal);
            listPayment = itemView.findViewById(R.id.listPayment);
            listTime = itemView.findViewById(R.id.listTime);
            listTotalPayment = itemView.findViewById(R.id.listTotalPayment);
        }

        public void bind(Expenditure expenditure) {
            listNameSeller.setText("  "+expenditure.getNameSeller());
            listStatus.setText(expenditure.getStatus());
            if(expenditure.getStatus().equals("Đã thanh toán")){
                listStatus.setTextColor(Color.parseColor("#FF6F69"));
            }else {
                listStatus.setTextColor(Color.parseColor("#ffaa3f"));
            }
//        imageView4.setImageURI();
            listNameProduct.setText(expenditure.getNameProduct());
            listTotal.setText("Số lượng: "+expenditure.getTotal()+" cây");
            listPayment.setText("Số tiền: đ"+expenditure.getProductCost());
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
            listTime.setText("Ngày bán: "+dateTimeFormat.format(expenditure.getDate()));
            listTotalPayment.setText("Tổng số tiền ("+expenditure.getTotal()+" cây): đ"+expenditure.getTotalPayment());
        }
    }

    // ViewHolder for Product
    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView listNameProduct,listTotal,listPayment,listTime,listTotalPayment;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            listNameProduct = itemView.findViewById(R.id.listNameProduct);
            listTotal = itemView.findViewById(R.id.listTotal);
            listPayment = itemView.findViewById(R.id.listPayment);
            listTime = itemView.findViewById(R.id.listTime);
            listTotalPayment = itemView.findViewById(R.id.listTotalPayment);
        }

        public void bind(Expenditure expenditure) {
            listNameProduct.setText(expenditure.getNameProduct());
            listTotal.setText("Số lượng: "+expenditure.getTotal()+" sản phẩm");
            listPayment.setText("Số tiền: đ"+expenditure.getProductCost());
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
            listTime.setText(dateTimeFormat.format(expenditure.getDate()));
            listTotalPayment.setText("Tổng số tiền ("+expenditure.getTotal()+" sản phẩm): đ"+expenditure.getTotalPayment());
        }
    }
}
