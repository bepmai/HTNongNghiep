package tlu.edu.vn.ht63.htnongnghiep.Container.RevenueExpenditure.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import tlu.edu.vn.ht63.htnongnghiep.Model.Revenue;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class ListRevenueAdapter extends ArrayAdapter<Revenue> {
    public ListRevenueAdapter(@NonNull Context context, ArrayList<Revenue> dataArrayList) {
        super(context, R.layout.list_item_revenue, dataArrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        Revenue data = getItem(position);
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_revenue, parent, false);
        }

        TextView listNameSeller = view.findViewById(R.id.listNameSeller);
        ImageView imageView4 = view.findViewById(R.id.imageView4);
        TextView listNameProduct = view.findViewById(R.id.listNameProduct);
        TextView listTotal = view.findViewById(R.id.listTotal);
        TextView listPayment = view.findViewById(R.id.listPayment);
        TextView listTime = view.findViewById(R.id.listTime);
        TextView listTotalPayment = view.findViewById(R.id.listTotalPayment);

        listNameSeller.setText("  "+data.getNameSeller());
//        imageView4.setImageURI();
        listNameProduct.setText(data.getNameProduct());
        listTotal.setText("Số lượng: "+data.getTotal()+" cây");
        listPayment.setText("Số tiền: đ"+data.getProductCost());
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        listTime.setText(dateTimeFormat.format(data.getDate()));
        listTotalPayment.setText("Tổng số tiền ("+data.getTotal()+" cây): đ"+data.getTotalPayment());

        return view;
    }
}
