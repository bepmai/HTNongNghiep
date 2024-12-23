package tlu.edu.vn.ht63.htnongnghiep.Container.RevenueExpenditure;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;

import tlu.edu.vn.ht63.htnongnghiep.Component.OnItemClickListener;
import tlu.edu.vn.ht63.htnongnghiep.Container.RevenueExpenditure.Adapter.ListExpenditureAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.Expenditure;
import tlu.edu.vn.ht63.htnongnghiep.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExpenditureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpenditureFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExpenditureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpenditureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpenditureFragment newInstance(String param1, String param2) {
        ExpenditureFragment fragment = new ExpenditureFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expenditure, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ArrayList<Expenditure> expenditureList = new ArrayList<>();

        expenditureList.add(new Expenditure(
                1, // id
                Expenditure.TYPE_BUY, // viewType
                "https://example.com/image1.png", // productImage
                "Seller A", // nameSeller
                "Hà Nội",
                new Date(), // date
                "Đã thanh toán", // status
                "Product A", // nameProduct
                10, // total
                101, // idProduct
                5000.0f, // productCost
                50000.0f // totalPayment
        ));
        expenditureList.add(new Expenditure(
                2, // id
                Expenditure.TYPE_PRODUCT, // viewType
                "https://example.com/image2.png", // productImage
                "Seller B", // nameSeller
                "Hà Nội",
                new Date(), // date
                "Chưa thanh toán", // status
                "Product B", // nameProduct
                5, // total
                102, // idProduct
                3000.0f, // productCost
                15000.0f // totalPayment
        ));
        expenditureList.add(new Expenditure(
                3, // id
                Expenditure.TYPE_BUY, // viewType
                "https://example.com/image3.png", // productImage
                "Seller C", // nameSeller
                "Hà Nội",
                new Date(), // date
                "Chưa thanh toán", // status
                "Product C", // nameProduct
                20, // total
                103, // idProduct
                2500.0f, // productCost
                50000.0f // totalPayment
        ));

        ListExpenditureAdapter adapter = new ListExpenditureAdapter(getContext(), expenditureList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(Expenditure expenditure) {
                if (expenditure.getViewType() == Expenditure.TYPE_BUY){
                    Intent intent = new Intent(getContext(), ExpenditureDetailActivity.class);
                    intent.putExtra("item_id", expenditure.getId());
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getContext(), ProductDetailActivity.class);
                    intent.putExtra("item_id", expenditure.getId());
                    startActivity(intent);
                }
            }
        });

        return view;
    }
}