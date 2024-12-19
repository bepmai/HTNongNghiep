package tlu.edu.vn.ht63.htnongnghiep.Container.login.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import tlu.edu.vn.ht63.htnongnghiep.R;

public class CustomAdapter extends ArrayAdapter<String> {
    private Context context;
    private String[] items;

    public CustomAdapter(Context context, String[] items) {
        super(context, R.layout.spinner_item, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        TextView textView = view.findViewById(android.R.id.text1);
        textView.setText(items[position]);

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}

