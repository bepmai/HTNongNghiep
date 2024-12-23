package tlu.edu.vn.ht63.htnongnghiep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.squareup.picasso.Picasso;

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
        holder.windTV.setText(modal.getWindSpeed()+"Km/h");
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat output = new SimpleDateFormat("hh:mm aa");
        try{
            Date t = input.parse(modal.getTime());
            holder.timeTV.setText(output.format(t));
        }catch (ParseException e){
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
