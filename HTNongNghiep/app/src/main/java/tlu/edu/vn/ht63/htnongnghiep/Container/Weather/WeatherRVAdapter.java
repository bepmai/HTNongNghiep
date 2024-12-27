package tlu.edu.vn.ht63.htnongnghiep.Container.Weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.TimeZone;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.squareup.picasso.Picasso;

import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.Model.WeatherRVModal;

public class WeatherRVAdapter extends RecyclerView.Adapter<WeatherRVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<WeatherRVModal> weatherRVModalArrayList;

    public WeatherRVAdapter(Context context, ArrayList<WeatherRVModal> weatherRVModalArrayList){
        this.context = context;
        this.weatherRVModalArrayList = weatherRVModalArrayList;
    }
    public WeatherRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weather_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull WeatherRVAdapter.ViewHolder holder, int position) {
        WeatherRVModal modal = weatherRVModalArrayList.get(position);
        holder.temperatureTV.setText(modal.getTemperature() + "°C");
        Picasso.get().load("http:".concat(modal.getIcon())).into(holder.conditionTV);
        holder.windTV.setText(modal.getWindSpeed() + "Km/h");

        // Định dạng thời gian với xử lý múi giờ
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        input.setTimeZone(TimeZone.getTimeZone("UTC")); // Dữ liệu gốc theo múi giờ UTC

        SimpleDateFormat output = new SimpleDateFormat("HH:mm aa");
        output.setTimeZone(TimeZone.getTimeZone("Asia/Hanoi")); // Múi giờ Việt Nam

        try {
            // Chuyển đổi từ chuỗi thời gian gốc sang định dạng mới
            Date t = input.parse(modal.getTime());
            holder.timeTV.setText(output.format(t)); // Hiển thị thời gian đã chuyển đổi
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return weatherRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView windTV, temperatureTV, timeTV;
        private ImageView conditionTV;
        public ViewHolder(View itemview){
            super(itemview);
            windTV = itemview.findViewById(R.id.idTVWindSpeed);
            temperatureTV = itemview.findViewById(R.id.idTVTemperature);
            timeTV = itemview.findViewById(R.id.idTVTime);
            conditionTV = itemview.findViewById(R.id.idTVCondition);
        }
    }
}
