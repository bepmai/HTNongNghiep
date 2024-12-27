package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import tlu.edu.vn.ht63.htnongnghiep.Container.Weather.Weather;
import tlu.edu.vn.ht63.htnongnghiep.Activity.RevenueExpenditureActivity;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        humidityTV = view.findViewById(R.id.idTVHumidity);
        windSpeedTV = view.findViewById(R.id.idTVWindSpeed);
        cityNameTV = view.findViewById(R.id.idTVCityName);
        temperatureTV = view.findViewById(R.id.idTVTemperature);
        webView = view.findViewById(R.id.webView);
        detailButton = view.findViewById(R.id.detailButton);

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
}
