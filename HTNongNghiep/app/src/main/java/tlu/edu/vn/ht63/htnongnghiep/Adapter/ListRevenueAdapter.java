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

import tlu.edu.vn.ht63.htnongnghiep.Component.Interface.OnItemRevenueClickListener;
import tlu.edu.vn.ht63.htnongnghiep.Model.Revenue;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.ViewModel.RevenueViewModel;

public class ListRevenueAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final ArrayList<Revenue> dataList = new ArrayList<>();
    private final RevenueViewModel revenueViewModel;
    private OnItemRevenueClickListener listener;

    public ListRevenueAdapter(Context context, RevenueViewModel revenueViewModel) {
        this.context = context;
        this.revenueViewModel = revenueViewModel;

        revenueViewModel.getListMutableLiveData().observe((LifecycleOwner) context, new Observer<List<Revenue>>() {
            @Override
            public void onChanged(List<Revenue> revenueList) {
                if (revenueList != null) {
                    dataList.clear();
                    dataList.addAll(revenueList);
                    notifyDataSetChanged();
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemRevenueClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item_revenue, parent, false);
        return new SellerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Revenue revenue = dataList.get(position);

        ((SellerViewHolder) holder).bind(revenue);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(revenue);
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

        public void bind(Revenue revenue) {
            listNameSeller.setText("  "+revenue.getNameSeller());
            listStatus.setText(revenue.getStatus());
            if(revenue.getStatus().equals("Đã thanh toán")){
                listStatus.setTextColor(Color.parseColor("#FF6F69"));
            }else {
                listStatus.setTextColor(Color.parseColor("#ffaa3f"));
            }
//        imageView4.setImageURI();
            listNameProduct.setText(revenue.getNameProduct());
            listTotal.setText("Số lượng: "+revenue.getTotal()+" cây");
            listPayment.setText("Số tiền: đ"+revenue.getProductCost());
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy");
            listTime.setText("Ngày bán: "+dateTimeFormat.format(revenue.getDate()));
            listTotalPayment.setText("Tổng số tiền ("+revenue.getTotal()+" cây): đ"+revenue.getTotalPayment());
        }
    }
}
