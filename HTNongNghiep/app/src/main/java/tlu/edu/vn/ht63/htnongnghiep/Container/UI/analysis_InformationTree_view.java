package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Container.Adapter.analysis_featureInforTree_adapter;
import tlu.edu.vn.ht63.htnongnghiep.Container.Adapter.analysis_imageInfor_adapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.InforTreeFeature;
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

    public analysis_InformationTree_view() {
        // Required empty public constructor
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
        List<Integer> imageResourceList = new ArrayList<>();
        imageResourceList.add(R.drawable.tree);
        imageResourceList.add(R.drawable.tree);
        imageResourceList.add(R.drawable.tree);

        // Khởi tạo RecyclerView và thiết lập LayoutManager ngang
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewTree);
        LinearLayoutManager layoutManagerHorizontal = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManagerHorizontal);

        analysis_imageInfor_adapter adapter = new analysis_imageInfor_adapter(imageResourceList);
        recyclerView.setAdapter(adapter);

        List<InforTreeFeature> inforTreeFeatures = new ArrayList<>();
        inforTreeFeatures.add(new InforTreeFeature("Tổng quan","Đuôi Công Táo có tên khoa học là Calathea Orbifolia thuộc họ thực vật Marantaceae. Họ Dong, hay Họ Dong ta, còn gọi là họ Hoàng tinh là một họ các thực vật có hoa một lá mầm. Họ này là một phần của bộ Gừng (Zingiberales), bao gồm 550 loài được chia ra trong 32 chi. Họ này có nguồn gốc ở các vùng nhiệt đới của châu Mỹ, châu Phi và châu Á. Cây Đuôi Công Táo có 2 loại phổ biến thường gặp là cây Đuôi Công Xanh và Tím."));
        inforTreeFeatures.add(new InforTreeFeature("Đặc điểm","Không có gì cả nhé"));
        inforTreeFeatures.add(new InforTreeFeature("Tác dụng","Không có gì theo"));
        inforTreeFeatures.add(new InforTreeFeature("Ý nghĩa","Không có gì cả"));

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
                
            }
        });

        return view;
    }
}