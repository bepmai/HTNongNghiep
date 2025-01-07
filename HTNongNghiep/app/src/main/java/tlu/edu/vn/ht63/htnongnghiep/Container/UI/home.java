package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import static android.content.Context.MODE_PRIVATE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;
import tlu.edu.vn.ht63.htnongnghiep.Activity.RevenueExpenditureActivity;
import tlu.edu.vn.ht63.htnongnghiep.Activity.Weather;
import tlu.edu.vn.ht63.htnongnghiep.Model.Expenditure;
import tlu.edu.vn.ht63.htnongnghiep.Model.Revenue;
import tlu.edu.vn.ht63.htnongnghiep.R;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class home extends Fragment {
    public home() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
    }

    WebView webView;
    TextView detailButton, humidityTV, windSpeedTV, cityNameTV, temperatureTV, conditionTV;
    Spinner filterSpinner;
    ImageView iconIV;
    BarChart barChart1;

    ArrayList<Expenditure> expenditureArrayList = new ArrayList<>();
    ArrayList<Revenue> revenueArrayList = new ArrayList<>();

    DatabaseReference expenditureRef,revenueRef;
    ValueEventListener eventExpenditureListener,eventRevenueListener;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private FusedLocationProviderClient fusedLocationClient;

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
        filterSpinner = view.findViewById(R.id.filterSpinner);
        barChart1 = view.findViewById(R.id.barchart1);

        LinearLayout formWeather = view.findViewById(R.id.formweather);

        formWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Dùng Intent để chuyển sang ActivityWeather
                Intent intent = new Intent(getActivity(), Weather.class);
                startActivity(intent);
            }
        });

//        if (checkLocationPermission()) {
//            fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(requireActivity(), location -> {
//                    if (location != null) {
//                        String cityName = getCityTime(location.getLongitude(), location.getLatitude());
//                        if (cityName!=null&&!cityName.isEmpty()){
//                            getWeatherInfo(cityName);
//                        }else {
//                            getWeatherInfo("Hanoi");
//                        }
//                    } else {
//                        getWeatherInfo("Hanoi");
//                        Log.d("Location", "No location found.");
//                    }
//                });
//        }

        getWeatherInfo("Hanoi");

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

        ArrayAdapter<CharSequence> filterAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.filter_array,
                R.layout.filter_item
        );

        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(filterAdapter);

        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setBarChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        if(userId !=null) {
            expenditureRef = FirebaseDatabase.getInstance()
                    .getReference("expenditure")
                    .child(userId);

            eventExpenditureListener = expenditureRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@android.support.annotation.NonNull DataSnapshot snapshot) {
                    expenditureArrayList.clear();
                    for (DataSnapshot idExpenditureSnapshot : snapshot.getChildren()) {
                        Expenditure expenditure = idExpenditureSnapshot.getValue(Expenditure.class);
                        if (expenditure != null) {
                            expenditureArrayList.add(expenditure);
                        }
                        setBarChart();
                    }
                }

                @Override
                public void onCancelled(@android.support.annotation.NonNull DatabaseError error) {
                    Log.e("FirebaseError", "Error: " + error.getMessage());
                }
            });

            revenueRef = FirebaseDatabase.getInstance()
                    .getReference("revenue")
                    .child(userId);

            eventRevenueListener = revenueRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@android.support.annotation.NonNull DataSnapshot snapshot) {
                    revenueArrayList.clear();
                    for (DataSnapshot idRevenueSnapshot : snapshot.getChildren()) {
                        Revenue revenue = idRevenueSnapshot.getValue(Revenue.class);
                        if (revenue != null) {
                            revenueArrayList.add(revenue);
                        }
                        setBarChart();
                    }
                }

                @Override
                public void onCancelled(@android.support.annotation.NonNull DatabaseError error) {
                    Log.e("FirebaseError", "Error: " + error.getMessage());
                }
            });
        }
        setBarChart();

        return view;
    }

    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Request permission
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastKnownLocation();
            } else {
                Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getLastKnownLocation() {
        try {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(requireActivity(), new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Check if location is not null
                                if (location != null) {
                                    double latitude = location.getLatitude();
                                    double longitude = location.getLongitude();
                                    Log.d("Location", "Latitude: " + latitude + ", Longitude: " + longitude);
                                } else {
                                    Log.d("Location", "No last known location available.");
                                }
                            }
                        })
                        .addOnFailureListener(e -> Log.e("Location", "Failed to get location", e));
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private String getCityTime(double longitude, double latitude){
        String cityName = null;
        Geocoder gcd = new Geocoder(getActivity().getBaseContext(), Locale.getDefault());
        try{
            List<Address> addresses = gcd.getFromLocation(latitude, longitude, 10);
            for (Address adr : addresses){
                if(adr!=null){
                    String city = adr.getLocality();
                    if(city!=null && !city.equals("")){
                        cityName = city;
                    }else {
                        Log.d("TAG", "city not found");
                        Toast.makeText(getContext(), "User city not found", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return cityName;
    }

    private void setBarChart(){
        BarDataSet barDataSet1 = new BarDataSet(barEntries1(),"Thu");
        barDataSet1.setColors(getResources().getColor(R.color.green));

        BarDataSet barDataSet2 = new BarDataSet(barEntries2(),"Chi");
        barDataSet2.setColor(getResources().getColor(R.color.green_white));

        BarData barData1 = new BarData(barDataSet1,barDataSet2);
        barChart1.setData(barData1);
        barChart1.getDescription().setEnabled(false);

        String[] days = new String[]{"Thứ 2","Thứ 3","Thứ 4","Thứ 5","Thứ 6","Thứ 7","Chủ nhật"};
        if (filterSpinner.getSelectedItemPosition() == 1){
            days = getDaysOfCurrentMonth();
        }else if (filterSpinner.getSelectedItemPosition() == 2){
            days = getMonthsOfCurrentYear();
        }
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
    }

    public String[] getDaysOfCurrentMonth() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("vi")); // Định dạng ngày
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int currentMonth = calendar.get(Calendar.MONTH);  // Tháng hiện tại (0-based)
        int currentYear = calendar.get(Calendar.YEAR);    // Năm hiện tại
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); // Số ngày trong tháng

        String[] daysArray = new String[daysInMonth]; // Tạo mảng kích thước bằng số ngày trong tháng

        for (int day = 1; day <= daysInMonth; day++) {
            calendar.set(currentYear, currentMonth, day); // Thiết lập ngày trong tháng
            Date date = calendar.getTime();              // Lấy đối tượng Date
            String formattedDate = dateFormat.format(date); // Định dạng ngày thành chuỗi
            daysArray[day - 1] = formattedDate;          // Lưu vào mảng (0-based index)
        }

        return daysArray;
    }

    public String[] getMonthsOfCurrentYear() {
        SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy", new Locale("vi")); // Định dạng tháng
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int currentYear = calendar.get(Calendar.YEAR); // Năm hiện tại

        String[] monthsArray = new String[12]; // Tạo mảng chứa 12 tháng

        for (int month = 0; month < 12; month++) {
            calendar.set(currentYear, month, 1); // Thiết lập tháng (1st day of the month)
            String formattedMonth = monthFormat.format(calendar.getTime()); // Định dạng tháng
            monthsArray[month] = formattedMonth; // Lưu vào mảng
        }

        return monthsArray;
    }

    private ArrayList<BarEntry> barEntries1(){
        if (filterSpinner.getSelectedItemPosition() == 0){
            float[] totalPayments = new float[7];

            Calendar calendar = Calendar.getInstance(Locale.getDefault());

            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            Date startOfWeek = calendar.getTime();

            calendar.add(Calendar.DAY_OF_WEEK, 6);
            Date endOfWeek = calendar.getTime();

            calendar.setTime(startOfWeek);

            for (Expenditure expenditure : expenditureArrayList) {
                if (expenditure.getDate() != null) {
                    Date expenditureDate = expenditure.getDate();

                    if (!expenditureDate.before(startOfWeek) && !expenditureDate.after(endOfWeek)) {
                        calendar.setTime(expenditureDate);
                        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                        int index = (dayOfWeek == Calendar.SUNDAY) ? 6 : dayOfWeek - Calendar.MONDAY;
                        totalPayments[index] += expenditure.getTotalPayment();
                    }
                }
            }

            ArrayList<BarEntry> barEntries = new ArrayList<>();
            for (int i = 0; i < totalPayments.length; i++) {
                barEntries.add(new BarEntry(i + 1, totalPayments[i]));
            }

            return barEntries;
        }else if (filterSpinner.getSelectedItemPosition() == 1){
            float[] totalPaymentsByDay = new float[31]; // Lưu tổng cho 31 ngày trong tháng

            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            int currentMonth = calendar.get(Calendar.MONTH); // Lấy tháng hiện tại (0-based: 0 = Tháng 1)
            int currentYear = calendar.get(Calendar.YEAR);  // Lấy năm hiện tại

            for (Expenditure expenditure : expenditureArrayList) {
                if (expenditure.getDate() != null) {
                    calendar.setTime(expenditure.getDate());
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);

                    if (month == currentMonth && year == currentYear) {
                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); // Lấy ngày trong tháng (1-31)
                        totalPaymentsByDay[dayOfMonth - 1] += expenditure.getTotalPayment(); // Cộng dồn vào mảng
                    }
                }
            }

            ArrayList<BarEntry> barEntries = new ArrayList<>();
            for (int i = 0; i < totalPaymentsByDay.length; i++) {
                barEntries.add(new BarEntry(i + 1, totalPaymentsByDay[i])); // i + 1 là ngày trong tháng
            }

            return barEntries;
        }else if (filterSpinner.getSelectedItemPosition() == 2){
            float[] totalPaymentsByMonth = new float[12]; // Lưu tổng cho 12 tháng trong năm

            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            int currentYear = calendar.get(Calendar.YEAR); // Lấy năm hiện tại

            for (Expenditure expenditure : expenditureArrayList) {
                if (expenditure.getDate() != null) {
                    calendar.setTime(expenditure.getDate());
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);

                    if (year == currentYear) {
                        totalPaymentsByMonth[month] += expenditure.getTotalPayment(); // Cộng dồn vào mảng
                    }
                }
            }

            ArrayList<BarEntry> barEntries = new ArrayList<>();
            for (int i = 0; i < totalPaymentsByMonth.length; i++) {
                barEntries.add(new BarEntry(i + 1, totalPaymentsByMonth[i])); // i + 1 là tháng trong năm
            }

            return barEntries;
        }
        return null;
    }

    private ArrayList<BarEntry> barEntries2(){
        if (filterSpinner.getSelectedItemPosition() == 0){
            float[] totalPayments = new float[7];

            Calendar calendar = Calendar.getInstance(Locale.getDefault());

            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            Date startOfWeek = calendar.getTime();

            calendar.add(Calendar.DAY_OF_WEEK, 6);
            Date endOfWeek = calendar.getTime();

            calendar.setTime(startOfWeek);

            for (Revenue revenue : revenueArrayList) {
                if (revenue.getDate() != null) {
                    Date expenditureDate = revenue.getDate();

                    if (!expenditureDate.before(startOfWeek) && !expenditureDate.after(endOfWeek)) {
                        calendar.setTime(expenditureDate);
                        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                        int index = (dayOfWeek == Calendar.SUNDAY) ? 6 : dayOfWeek - Calendar.MONDAY;
                        totalPayments[index] += revenue.getTotalPayment();
                    }
                }
            }

            ArrayList<BarEntry> barEntries = new ArrayList<>();
            for (int i = 0; i < totalPayments.length; i++) {
                barEntries.add(new BarEntry(i + 1, totalPayments[i]));
            }

            return barEntries;
        }else if (filterSpinner.getSelectedItemPosition() == 1){
            float[] totalPaymentsByDay = new float[31]; // Lưu tổng cho 31 ngày trong tháng

            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            int currentMonth = calendar.get(Calendar.MONTH); // Lấy tháng hiện tại (0-based: 0 = Tháng 1)
            int currentYear = calendar.get(Calendar.YEAR);  // Lấy năm hiện tại

            for (Revenue revenue : revenueArrayList) {
                if (revenue.getDate() != null) {
                    calendar.setTime(revenue.getDate());
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);

                    if (month == currentMonth && year == currentYear) {
                        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); // Lấy ngày trong tháng (1-31)
                        totalPaymentsByDay[dayOfMonth - 1] += revenue.getTotalPayment(); // Cộng dồn vào mảng
                    }
                }
            }

            ArrayList<BarEntry> barEntries = new ArrayList<>();
            for (int i = 0; i < totalPaymentsByDay.length; i++) {
                if (totalPaymentsByDay[i] > 0) { // Chỉ thêm vào nếu có giá trị
                    barEntries.add(new BarEntry(i + 1, totalPaymentsByDay[i])); // i + 1 là ngày trong tháng
                }
            }

            return barEntries;
        }else if (filterSpinner.getSelectedItemPosition() == 2){
            float[] totalPaymentsByMonth = new float[12]; // Lưu tổng cho 12 tháng trong năm

            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            int currentYear = calendar.get(Calendar.YEAR); // Lấy năm hiện tại

            for (Revenue revenue : revenueArrayList) {
                if (revenue.getDate() != null) {
                    calendar.setTime(revenue.getDate());
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);

                    if (year == currentYear) {
                        totalPaymentsByMonth[month] += revenue.getTotalPayment(); // Cộng dồn vào mảng
                    }
                }
            }

            ArrayList<BarEntry> barEntries = new ArrayList<>();
            for (int i = 0; i < totalPaymentsByMonth.length; i++) {
                if (totalPaymentsByMonth[i] > 0) {
                    barEntries.add(new BarEntry(i + 1, totalPaymentsByMonth[i])); // i + 1 là tháng trong năm
                }
            }

            return barEntries;
        }
        return null;
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
