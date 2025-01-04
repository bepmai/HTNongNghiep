package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Activity.AddPlantProductActivity;
import tlu.edu.vn.ht63.htnongnghiep.Activity.ItemDetailActivity;
import tlu.edu.vn.ht63.htnongnghiep.Activity.PlantListActivity;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.PlantAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.Plant;
import tlu.edu.vn.ht63.htnongnghiep.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
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
    PlantAdapter adapter;
    List<Plant> plantList;
    Button button1,button2;
    View bottomBolder1,bottomBolder2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        bottomBolder1 = view.findViewById(R.id.bottomBolder1);
        bottomBolder2 = view.findViewById(R.id.bottomBolder2);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddPlantProductActivity.class);
                startActivity(intent);
            }
        });

        // Set GridLayoutManager with 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        plantList = new ArrayList<>();
        plantList.add(new Plant("Cây Ổi", "500.000đ", "-20%", "4.8 (100 đánh giá)", R.drawable.img));
        plantList.add(new Plant("Cây Xoài", "750.000đ", "-10%", "4.5 (80 đánh giá)", R.drawable.img_1));
        plantList.add(new Plant("Cây Cam", "600.000đ", "-15%", "4.7 (90 đánh giá)", R.drawable.img_2));
        plantList.add(new Plant("Cây Bưởi", "900.000đ", "-5%", "4.9 (120 đánh giá)", R.drawable.img_3));
        plantList.add(new Plant("Cây Chanh", "300.000đ", "-25%", "4.6 (110 đánh giá)", R.drawable.img));

        // Set adapter
        adapter = new PlantAdapter(plantList, new PlantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Plant plant) {
                // Handle item click to navigate to another activity
                Log.d("PlantListActivity", "Navigating to ItemDetailActivity");
                Intent intent = new Intent(getContext(), ItemDetailActivity.class);
                intent.putExtra("plant_name", plant.getName());
                intent.putExtra("plant_price", plant.getPrice());
                intent.putExtra("plant_discount", plant.getDiscount());
                intent.putExtra("plant_rating", plant.getRating());
                intent.putExtra("plant_image", plant.getImageResId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        return view;
    }
}