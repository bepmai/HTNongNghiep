package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;

import tlu.edu.vn.ht63.htnongnghiep.Adapter.ListRevenueAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Component.Interface.OnItemRevenueClickListener;
import tlu.edu.vn.ht63.htnongnghiep.Model.Revenue;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.ViewModel.RevenueViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RevenueFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RevenueFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RevenueFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RevenueFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RevenueFragment newInstance(String param1, String param2) {
        RevenueFragment fragment = new RevenueFragment();
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

    RecyclerView recyclerView;
    ListRevenueAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_revenue, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Revenue> revenueList = new ArrayList<>();
        revenueList.add(new Revenue(
                1,                            // id
                "image_url_here",             // productImage
                "Nguyễn Văn Anh",               // nameSeller
                "Hà Nội",
                new Date(),                   // date (hiện tại)
                "Đã thanh toán",              // status
                "Cây Bonsai 20 năm",                      // nameProduct
                10,                           // total (số lượng)
                101,                          // idProduct
                200000.0f,                     // productCost (giá mỗi sản phẩm)
                200000.0f                     // totalPayment (tổng thanh toán)
        ));
        revenueList.add(new Revenue(
                2,                            // id
                "image_url_here",             // productImage
                "Nguyễn Đức Anh",               // nameSeller
                "Hà Nội",
                new Date(),                   // date (hiện tại)
                "Chưa thanh toán",              // status
                "Cây Bonsai 20 năm",                      // nameProduct
                10,                           // total (số lượng)
                101,                          // idProduct
                200000.0f,                     // productCost (giá mỗi sản phẩm)
                200000.0f                     // totalPayment (tổng thanh toán)
        ));

        RevenueViewModel revenueViewModel =
                new ViewModelProvider(requireActivity()).get(RevenueViewModel.class);

        adapter = new ListRevenueAdapter(getContext(),revenueViewModel);
        recyclerView.setAdapter(adapter);

        revenueViewModel.setData(revenueList);

        adapter.setOnItemClickListener(new OnItemRevenueClickListener() {
            @Override
            public void onItemClick(Revenue revenue) {
                Fragment revenueDetailFragment = new RevenueDetailFragment(revenue);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main, revenueDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}