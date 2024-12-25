package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import tlu.edu.vn.ht63.htnongnghiep.Activity.RevenueExpenditureActivity;
import tlu.edu.vn.ht63.htnongnghiep.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    WebView webView;
    TextView detailButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        webView = view.findViewById(R.id.webView);
        detailButton = view.findViewById(R.id.detailButton);

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

        // Chạy JavaScript sau khi trang tải xong
//        webView.setWebViewClient(new WebViewClient() {
//            @Override
//            public void onPageFinished(WebView view, String url) {
//                // JavaScript để chỉ lấy phần tử cụ thể
//                webView.evaluateJavascript(
//                        "var element = document.querySelector('div[data-test-chart-id=\"72\"]');" +
//                                "if (element) {" +
//                                "    document.body.innerHTML = element.outerHTML;" + // Thay thế nội dung WebView bằng phần tử này
//                                "} else {" +
//                                "    document.body.innerHTML = '<p>Element not found</p>';" +
//                                "}", null);
//            }
//        });

        return view;
    }
}