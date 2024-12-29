package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONException;
import org.json.JSONObject;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

import tlu.edu.vn.ht63.htnongnghiep.Activity.RevenueExpenditureActivity;
import tlu.edu.vn.ht63.htnongnghiep.Activity.Weather;
import tlu.edu.vn.ht63.htnongnghiep.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class home extends Fragment {
    public home() {
        // Required empty public constructor
    }

    private TextView humidityTV, windSpeedTV, cityNameTV, temperatureTV;
    WebView webView;
    TextView detailButton;
    BarChart barChart1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        humidityTV = view.findViewById(R.id.idTVHumidity);
        windSpeedTV = view.findViewById(R.id.idTVWindSpeed);
        cityNameTV = view.findViewById(R.id.idTVCityName);
        temperatureTV = view.findViewById(R.id.idTVTemperature);
        webView = view.findViewById(R.id.webView);
        detailButton = view.findViewById(R.id.detailButton);
        barChart1 = view.findViewById(R.id.barchart1);

        LinearLayout formWeather = view.findViewById(R.id.formweather);

        // Gán sự kiện onClick cho LinearLayout
        formWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dùng Intent để chuyển sang ActivityWeather
                Intent intent = new Intent(getActivity(), Weather.class);
                startActivity(intent);
            }
        });

        getWeatherInfo("Hanoi"); // Gọi API cho thành phố Hà Nội

        // Cấu hình WebView
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true); // Phóng to nội dung trang web
        webView.getSettings().setUseWideViewPort(true); // Hỗ trợ chế độ viewport
        webView.getSettings().setAllowFileAccess(true); // Cho phép truy cập tệp cục bộ nếu cần
        webView.getSettings().setAllowContentAccess(true); // Cho phép truy cập nội dung
        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); // Hỗ trợ tải nội dung HTTP và HTTPS
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("http://192.168.55.106:8088/superset/dashboard/p/27Wlg0RLPkE/");

        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), RevenueExpenditureActivity.class);
                startActivity(intent);
            }
        });

        BarDataSet barDataSet1 = new BarDataSet(barEntries1(),"Thu");
        barDataSet1.setColors(getResources().getColor(R.color.green));

        BarDataSet barDataSet2 = new BarDataSet(barEntries2(),"Chi");
        barDataSet2.setColor(getResources().getColor(R.color.green_white));

        BarData barData1 = new BarData(barDataSet1,barDataSet2);
        barChart1.setData(barData1);
        barChart1.getDescription().setEnabled(false);

        String[] days = new String[]{"Thứ 2","Thứ 3","Thứ 4","Thứ 5","Thứ 6","Thứ 7","Chủ nhật"};
        XAxis xAxis = barChart1.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        xAxis.setCenterAxisLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1);
        xAxis.setGranularityEnabled(true);

        barChart1.setDragEnabled(true);
        barChart1.setVisibleXRangeMaximum(4);

        float barSpace = 0.1f;
        float groupSpace = 0.4f;
        barData1.setBarWidth(0.2f);

        barChart1.getXAxis().setAxisMinimum(0);
        barChart1.groupBars(0,groupSpace,barSpace);

        // Điều chỉnh vị trí và kiểu của Legend
        Legend legend = barChart1.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM); // Vị trí ngang dưới cùng
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER); // Ở giữa
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL); // Hiển thị ngang
        legend.setDrawInside(false);
        legend.setTextSize(12f); // Kích thước chữ

        barChart1.invalidate();

        return view;
    }

    private void getWeatherInfo(String cityName) {
        String url = "http://api.weatherapi.com/v1/forecast.json?key=dc808a7efddb44c5a7522941242212&q=" + cityName + "&days=1&aqi=yes&alerts=yes";
        cityNameTV.setText(cityName);
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String humidity = response.getJSONObject("current").getString("humidity");
                            humidityTV.setText(humidity + "%");

                            String temperature = response.getJSONObject("current").getString("temp_c");
                            temperatureTV.setText(temperature + "°C");

                            String windSpeed = response.getJSONObject("current").getString("wind_kph");
                            windSpeedTV.setText(windSpeed + "km/h");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(requireContext(), "Không thể lấy thông tin thời tiết. Vui lòng kiểm tra lại!", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private ArrayList<BarEntry> barEntries1(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1,2000));
        barEntries.add(new BarEntry(2,791));
        barEntries.add(new BarEntry(3,630));
        barEntries.add(new BarEntry(4,450));
        barEntries.add(new BarEntry(5,2724));
        barEntries.add(new BarEntry(6,500));
        barEntries.add(new BarEntry(7,2173));
        return barEntries;
    }

    private ArrayList<BarEntry> barEntries2(){
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1,900));
        barEntries.add(new BarEntry(2,631));
        barEntries.add(new BarEntry(3,1040));
        barEntries.add(new BarEntry(4,382));
        barEntries.add(new BarEntry(5,2614));
        barEntries.add(new BarEntry(6,5000));
        barEntries.add(new BarEntry(7,1173));
        return barEntries;
    }
}
