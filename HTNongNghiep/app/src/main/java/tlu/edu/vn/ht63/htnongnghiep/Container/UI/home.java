package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;

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
    WebView webView;
    TextView detailButton, humidityTV, windSpeedTV, cityNameTV, temperatureTV, conditionTV;

    ImageView iconIV;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        humidityTV = view.findViewById(R.id.idTVHumidity);
        windSpeedTV = view.findViewById(R.id.idTVWindSpeed);
        cityNameTV = view.findViewById(R.id.idTVCityName);
        temperatureTV = view.findViewById(R.id.idTVTemperature);
        conditionTV = view.findViewById(R.id.idTVCondition);
        iconIV = view.findViewById(R.id.idTVIcon);
        webView = view.findViewById(R.id.webView);
        detailButton = view.findViewById(R.id.detailButton);

        LinearLayout formWeather = view.findViewById(R.id.formweather);

        formWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Weather.class);
                startActivity(intent);
            }
        });

        getWeatherInfo("Hanoi");

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
                            Map<String, String> weatherConditionMap = new HashMap<>();
                            weatherConditionMap.put("Sunny", "Trời nắng");
                            weatherConditionMap.put("Clear", "Quang đãng");
                            weatherConditionMap.put("Partly cloudy", "Có mây rải rác");
                            weatherConditionMap.put("Cloudy", "Nhiều mây");
                            weatherConditionMap.put("Overcast", "U ám");
                            weatherConditionMap.put("Mist", "Sương mù nhẹ");
                            weatherConditionMap.put("Patchy rain possible", "Có thể có mưa lác đác");
                            weatherConditionMap.put("Patchy snow possible", "Có thể có tuyết lác đác");
                            weatherConditionMap.put("Patchy sleet possible", "Có thể có mưa tuyết lác đác");
                            weatherConditionMap.put("Patchy freezing drizzle possible", "Có thể có mưa phùn đóng băng lác đác");
                            weatherConditionMap.put("Thundery outbreaks possible", "Có thể có dông");
                            weatherConditionMap.put("Blowing snow", "Tuyết thổi");
                            weatherConditionMap.put("Blizzard", "Bão tuyết");
                            weatherConditionMap.put("Fog", "Sương mù");
                            weatherConditionMap.put("Freezing fog", "Sương mù đóng băng");
                            weatherConditionMap.put("Patchy light drizzle", "Mưa phùn nhẹ lác đác");
                            weatherConditionMap.put("Light drizzle", "Mưa phùn nhẹ");
                            weatherConditionMap.put("Freezing drizzle", "Mưa phùn đóng băng");
                            weatherConditionMap.put("Heavy freezing drizzle", "Mưa phùn đóng băng nặng");
                            weatherConditionMap.put("Patchy light rain", "Mưa nhẹ lác đác");
                            weatherConditionMap.put("Light rain", "Mưa nhẹ");
                            weatherConditionMap.put("Moderate rain at times", "Mưa vừa từng đợt");
                            weatherConditionMap.put("Moderate rain", "Mưa vừa");
                            weatherConditionMap.put("Heavy rain at times", "Mưa nặng hạt từng đợt");
                            weatherConditionMap.put("Heavy rain", "Mưa lớn");
                            weatherConditionMap.put("Light freezing rain", "Mưa đóng băng nhẹ");
                            weatherConditionMap.put("Moderate or heavy freezing rain", "Mưa đóng băng vừa hoặc lớn");
                            weatherConditionMap.put("Light sleet", "Mưa tuyết nhẹ");
                            weatherConditionMap.put("Moderate or heavy sleet", "Mưa tuyết vừa hoặc lớn");
                            weatherConditionMap.put("Patchy light snow", "Tuyết nhẹ lác đác");
                            weatherConditionMap.put("Light snow", "Tuyết nhẹ");
                            weatherConditionMap.put("Patchy moderate snow", "Tuyết vừa lác đác");
                            weatherConditionMap.put("Moderate snow", "Tuyết vừa");
                            weatherConditionMap.put("Patchy heavy snow", "Tuyết lớn lác đác");
                            weatherConditionMap.put("Heavy snow", "Tuyết lớn");
                            weatherConditionMap.put("Ice pellets", "Mưa đá nhỏ");
                            weatherConditionMap.put("Light rain shower", "Mưa rào nhẹ");
                            weatherConditionMap.put("Moderate or heavy rain shower", "Mưa rào vừa hoặc lớn");
                            weatherConditionMap.put("Torrential rain shower", "Mưa rào xối xả");
                            weatherConditionMap.put("Light sleet showers", "Mưa tuyết nhẹ rải rác");
                            weatherConditionMap.put("Moderate or heavy sleet showers", "Mưa tuyết vừa hoặc lớn rải rác");
                            weatherConditionMap.put("Light snow showers", "Tuyết nhẹ rải rác");
                            weatherConditionMap.put("Moderate or heavy snow showers", "Tuyết vừa hoặc lớn rải rác");
                            weatherConditionMap.put("Light showers of ice pellets", "Mưa đá nhỏ nhẹ rải rác");
                            weatherConditionMap.put("Moderate or heavy showers of ice pellets", "Mưa đá nhỏ vừa hoặc lớn rải rác");
                            weatherConditionMap.put("Patchy light rain with thunder", "Mưa nhẹ lác đác có sấm sét");
                            weatherConditionMap.put("Moderate or heavy rain with thunder", "Mưa vừa hoặc lớn có sấm sét");
                            weatherConditionMap.put("Patchy light snow with thunder", "Tuyết nhẹ lác đác có sấm sét");
                            weatherConditionMap.put("Moderate or heavy snow with thunder", "Tuyết vừa hoặc lớn có sấm sét");

                            String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                            String conditionInVietnamese = weatherConditionMap.getOrDefault(condition, "Không rõ điều kiện thời tiết");
                            conditionTV.setText(conditionInVietnamese);

                            String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                            Picasso.get().load("http:".concat(conditionIcon)).into(iconIV);

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
}
