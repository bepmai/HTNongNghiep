package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Activity.AddPlant;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.analysis_featureInforTree_adapter;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.analysis_imageInfor_adapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.InforTreeFeature;
import tlu.edu.vn.ht63.htnongnghiep.Model.TreeLib;
import tlu.edu.vn.ht63.htnongnghiep.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link analysis_InformationTree_view#newInstance} factory method to
 * create an instance of this fragment.
 */
public class analysis_InformationTree_view extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TreeLib tree;

    public analysis_InformationTree_view() {
        // Required empty public constructor
    }

    public analysis_InformationTree_view(TreeLib tree){
        this.tree = tree;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InformationTree_view.
     */
    // TODO: Rename and change types and number of parameters
    public static analysis_InformationTree_view newInstance(String param1, String param2) {
        analysis_InformationTree_view fragment = new analysis_InformationTree_view();
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
        View view =  inflater.inflate(R.layout.fragment_information_tree_view, container, false);
        List<String> imageResourceList = new ArrayList<>();
        ImageView imgTree = view.findViewById(R.id.imgTree);

        TextView treeHeight = view.findViewById(R.id.treeHeight);
        TextView treeTemp = view.findViewById(R.id.treeTemp);
        TextView treeEncounter = view.findViewById(R.id.treeEncounter);
        TextView treeArea = view.findViewById(R.id.treeArea);
        TextView treeName = view.findViewById(R.id.treeName);
        TextView phapdanh = view.findViewById(R.id.phapdanh);
        if(tree !=null){
            imageResourceList = tree.getImages();
            Glide.with(imgTree).load(Uri.parse(tree.getImages().get(0))).into(imgTree);
            treeHeight.setText(tree.getHeightMean());
            treeTemp.setText(tree.getTemperature());
            treeEncounter.setText(tree.getTrunk());
            treeArea.setText(tree.getEnvironmentLive());
            treeName.setText(tree.getName());
            phapdanh.setText(tree.getUnique());
        }

        // Khởi tạo RecyclerView và thiết lập LayoutManager ngang
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewTree);
        LinearLayoutManager layoutManagerHorizontal = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManagerHorizontal);

        analysis_imageInfor_adapter adapter = new analysis_imageInfor_adapter(imageResourceList);
        recyclerView.setAdapter(adapter);

        List<InforTreeFeature> inforTreeFeatures = new ArrayList<>();
        inforTreeFeatures.add(new InforTreeFeature("Tổng quan",tree != null ? tree.getDiscription() : "Không có thông tin"));
        inforTreeFeatures.add(new InforTreeFeature("Đặc điểm",tree != null ? tree.getDistribution() : "Không có thông tin"));
        inforTreeFeatures.add(new InforTreeFeature("Tác dụng",tree != null ? tree.getFeature() : "Không có thông tin"));
        inforTreeFeatures.add(new InforTreeFeature("Ý nghĩa",tree != null ? tree.getMean() : "Không có thông tin"));
        inforTreeFeatures.add(new InforTreeFeature("Thời gian trung bình tắm nắng",tree != null ? tree.getSuns() : "Không có thông tin"));
        inforTreeFeatures.add(new InforTreeFeature("Thời gian trung bình tưới nước",tree != null ? tree.getWaters() : "Không có thông tin"));
        inforTreeFeatures.add(new InforTreeFeature("Khu vực",tree != null ? tree.getArea() : "Không có thông tin"));
        inforTreeFeatures.add(new InforTreeFeature("Mua bán",""));



        RecyclerView recyclerViewFeature = view.findViewById(R.id.recyclerViewFeature);
        LinearLayoutManager layoutManagerVertical = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewFeature.setLayoutManager(layoutManagerVertical);

        analysis_featureInforTree_adapter adapter1 = new analysis_featureInforTree_adapter(inforTreeFeatures);
        recyclerViewFeature.setAdapter(adapter1);

        ImageView back = view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

        Button addTree = view.findViewById(R.id.buttonAddTree);
        addTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPlant = new Intent(requireActivity(), AddPlant.class);
                addPlant.putExtra("treelib", tree);
                startActivity(addPlant);
            }
        });

        return view;
    }
}