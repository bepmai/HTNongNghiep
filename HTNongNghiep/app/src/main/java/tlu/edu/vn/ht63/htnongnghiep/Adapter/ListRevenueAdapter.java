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

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Component.Interface.OnItemRevenueClickListener;
import tlu.edu.vn.ht63.htnongnghiep.Model.Revenue;
import tlu.edu.vn.ht63.htnongnghiep.Model.RevenueExpenditure;
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
        TextView listNameBuyer,listStatus,listNameProduct,listTotal,listPayment,listTime,listTotalPayment;
        ImageView imageView4;

        public SellerViewHolder(@NonNull View itemView) {
            super(itemView);
            listStatus = itemView.findViewById(R.id.listStatus);
            listNameBuyer = itemView.findViewById(R.id.listNameBuyer);
            imageView4 = itemView.findViewById(R.id.imageView4);
            listNameProduct = itemView.findViewById(R.id.listNameProduct);
            listTotal = itemView.findViewById(R.id.listTotal);
            listPayment = itemView.findViewById(R.id.listPayment);
            listTime = itemView.findViewById(R.id.listTime);
            listTotalPayment = itemView.findViewById(R.id.listTotalPayment);
        }

        public void bind(Revenue revenue) {
            listNameBuyer.setText("  "+revenue.getNameBuyer());
            if(revenue.getStatus() == RevenueExpenditure.TYPE_NOT_CONFIRMED){
                listStatus.setTextColor(Color.parseColor("#FF000000"));
                listStatus.setText("Chưa xác nhận");
            }else if(revenue.getStatus() == RevenueExpenditure.TYPE_CONFIRM){
                listStatus.setTextColor(Color.parseColor("#ffaa3f"));
                listStatus.setText("Đã xác nhận");
            }else if(revenue.getStatus() == RevenueExpenditure.TYPE_SUCCESS){
                listStatus.setTextColor(Color.parseColor("#FF6F69"));
                listStatus.setText("Thành công");
            }
            Glide.with(imageView4)
                    .load(revenue.getProductImage())
                    .placeholder(R.drawable.group260) // Ảnh hiển thị khi đang tải
                    .error(R.drawable.group260)       // Ảnh hiển thị khi lỗi
                    .into(imageView4);
            listNameProduct.setText(revenue.getNameProduct());
            listTotal.setText("Số lượng: "+revenue.getTotal()+" cây");
            listPayment.setText("Số tiền: đ"+revenue.getProductCost());
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            listTime.setText(dateTimeFormat.format(revenue.getDate()));
            listTotalPayment.setText("Tổng số tiền ("+revenue.getTotal()+" cây): đ"+revenue.getTotalPayment());
        }
    }
}
