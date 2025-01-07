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
import java.util.Date;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Activity.ItemDetailActivity;
import tlu.edu.vn.ht63.htnongnghiep.Activity.PlantShopActivity;
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
                Intent intent = new Intent(getContext(), PlantShopActivity.class);
                startActivity(intent);
            }
        });

        // Set GridLayoutManager with 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        plantList = new ArrayList<>();
        plantList.add(new Plant(
                "1", "PL001", "U001", "John Doe", new Date(),
                "123 Green St, Springfield",
                "Beautiful indoor plant",
                "Aloe Vera", 10.5f, 4.8f,
                "https://upload.wikimedia.org/wikipedia/commons/3/36/Aloe_vera_flower.JPG"
        ));
        plantList.add(new Plant(
                "2", "PL002", "U002", "Jane Smith", new Date(),
                "456 Oak Dr, Shelbyville",
                "Perfect for your garden",
                "Rose", 15.0f, 4.5f,
                "https://upload.wikimedia.org/wikipedia/commons/3/39/Rose_flower.JPG"
        ));
        plantList.add(new Plant(
                "3", "PL003", "U003", "Alice Johnson", new Date(),
                "789 Pine Ln, Ogdenville",
                "Low maintenance succulent",
                "Cactus", 7.0f, 4.9f,
                "https://upload.wikimedia.org/wikipedia/commons/e/e8/Cactaceae_-_Echinopsis_pachanoi.jpg"
        ));
        plantList.add(new Plant(
                "4", "PL004", "U004", "Bob Brown", new Date(),
                "101 Maple St, North Haverbrook",
                "Aromatic herb plant",
                "Basil", 5.0f, 4.7f,
                "https://upload.wikimedia.org/wikipedia/commons/a/a1/Basil-Basilico-Ocimum_basilicum-albahaca.jpg"
        ));
        plantList.add(new Plant(
                "5", "PL005", "U005", "Charlie Green", new Date(),
                "202 Elm Ave, Capital City",
                "Brightens up any space",
                "Sunflower", 12.0f, 4.6f,
                "https://upload.wikimedia.org/wikipedia/commons/4/40/Sunflower_sky_backdrop.jpg"
        ));

        // Set adapter
        adapter = new PlantAdapter(plantList, new PlantAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Plant plant) {
                Log.d("PlantListActivity", "Navigating to ItemDetailActivity");
                Intent intent = new Intent(getContext(), ItemDetailActivity.class);
                intent.putExtra("plant_name", plant.getName());
                intent.putExtra("plant_price", plant.getPrice());
                intent.putExtra("plant_rating", plant.getRating());
                intent.putExtra("plant_image", plant.getImage());
                intent.putExtra("plant_id", plant.getId());
                intent.putExtra("plant_idplant", plant.getIdplant());
                intent.putExtra("plant_iduser", plant.getUserid());
                intent.putExtra("plant_nameuser", plant.getNameuser());
                intent.putExtra("plant_datesell", plant.getDatesell());
                intent.putExtra("plant_address", plant.getAddress());
                intent.putExtra("plant_description", plant.getDescription());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        return view;
    }
}