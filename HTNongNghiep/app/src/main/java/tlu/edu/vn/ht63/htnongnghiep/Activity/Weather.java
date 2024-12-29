package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import tlu.edu.vn.ht63.htnongnghiep.Container.UI.home;
import tlu.edu.vn.ht63.htnongnghiep.R;

import tlu.edu.vn.ht63.htnongnghiep.Adapter.WeatherRVAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.WeatherRVModal;
import android.Manifest;

public class Weather extends AppCompatActivity {

    private TextView cityNameTV, temperatureTV, conditionTV;
    private RecyclerView weatherRV;
    private TextInputEditText cityEdt;
    private ImageView backIV, iconIV, searcherIV;
    private ArrayList<WeatherRVModal> weatherRVModalArrayList;
    private WeatherRVAdapter weatherRVAdapter;
    private LocationManager locationManager;
    private int PERMISSON_CODE = 1;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weather);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        cityNameTV = findViewById(R.id.idTVCityName);
        temperatureTV = findViewById(R.id.idTVTemperature);
        conditionTV = findViewById(R.id.idTVCondition);
        weatherRV = findViewById(R.id.idRvWeather);
        cityEdt = findViewById(R.id.idEdtCity);
        backIV = findViewById(R.id.idTVBack);
        iconIV = findViewById(R.id.idTVIcon);
        searcherIV = findViewById(R.id.idTVSearch);
        weatherRVModalArrayList = new ArrayList<>();
        weatherRVAdapter = new WeatherRVAdapter(this, weatherRVModalArrayList);
        weatherRV.setAdapter(weatherRVAdapter);

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Weather.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Weather.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSON_CODE);
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//        cityName = getCityTime(location.getLongitude(), location.getLatitude());
        if (location != null){
            cityName = getCityTime(location.getLongitude(), location.getLatitude());
            getWeatherInfo(cityName);
        } else {
            cityName = "Hanoi";
            getWeatherInfo(cityName);
        }
        cityNameTV.setText(cityName);

        searcherIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = cityEdt.getText().toString();
                if(city.isEmpty()){
                    Toast.makeText(Weather.this, "Plesse enter city name", Toast.LENGTH_SHORT).show();
                }
                else {
                    cityNameTV.setText(cityName);
                    getWeatherInfo(city);
                }
                cityNameTV.setText(cityName);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PERMISSON_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permissons granted...", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Please provide the permissions", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private String getCityTime(double longitude, double latitude){
        String cityName = "Not found";
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try{
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 10);
            for (Address adr : addresses){
                if(adr!=null){
                    String city = adr.getLocality();
                    if(city!=null && !city.equals("")){
                        cityName = city;
                    }else {
                        Log.d("TAG", "city not found");
                        Toast.makeText(this, "User city not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return cityName;
    }
    private void getWeatherInfo(String cityName) {
        String url = "http://api.weatherapi.com/v1/forecast.json?key=dc808a7efddb44c5a7522941242212&q=" + cityName + "&days=1&aqi=yes&alerts=yes";
//        String url = "http://api.weatherapi.com/v1/forecast.json?key=dc808a7efddb44c5a7522941242212&q=Hanoi&days=1&aqi=yes&alerts=yes";
        cityNameTV.setText(cityName);
        RequestQueue requestQueue = Volley.newRequestQueue(Weather.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                weatherRVModalArrayList.clear();

                try{
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

                    String temperature = response.getJSONObject("current").getString("temp_c");
                    temperatureTV.setText(temperature + "°C");
                    int isDay = response.getJSONObject("current").getInt("is_day");
                    String conditionIcon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Picasso.get().load("http:".concat(conditionIcon)).into(iconIV);
//                    if(isDay==1){
//                        Picasso.get().load("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.vn.freelancer.com%2Fcontest%2Fanimated-weather-wallpaper-for-iphone-app-329593-byentry-7777965&psig=AOvVaw3BHZa66tN65m1-vLrYGmqB&ust=1735019574502000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMD-_dyZvYoDFQAAAAAdAAAAABAE").into(backIV);
//                    }else {
//                        Picasso.get().load("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.freepik.com%2Ffree-ai-image%2Fview-3d-cloud_133554256.htm&psig=AOvVaw3sDC8UXcCZChgAEYuGDJXz&ust=1735019643960000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCND_hP-ZvYoDFQAAAAAdAAAAABAJ").into(backIV);
//                    }
                    JSONObject forecastObj = response.getJSONObject("forecast");
                    JSONObject forecastO = forecastObj.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray hourArray = forecastO.getJSONArray("hour");

                    LocalTime now = LocalTime.now();
                    int currentHour = now.getHour();

                    for (int i=currentHour+1; i<hourArray.length(); i++){
                        JSONObject hourObj = hourArray.getJSONObject(i);
                        String time = hourObj.getString("time");
                        String temper = hourObj.getString("temp_c");
                        String img = hourObj.getJSONObject("condition").getString("icon");
                        String wind = hourObj.getString("wind_kph");
                        String humidity = hourObj.getString("humidity");
                        weatherRVModalArrayList.add(new WeatherRVModal(time, temper, img, wind, humidity));
                    }
                    JSONObject hourObj = hourArray.getJSONObject(0);
                    String time = hourObj.getString("time");
                    String temper = hourObj.getString("temp_c");
                    String img = hourObj.getJSONObject("condition").getString("icon");
                    String wind = hourObj.getString("wind_kph");
                    String humidity = hourObj.getString("humidity");
                    weatherRVModalArrayList.add(new WeatherRVModal(time, temper, img, wind, humidity));
                    weatherRVAdapter.notifyDataSetChanged();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Weather.this, "Dien ten thanh pho...", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}