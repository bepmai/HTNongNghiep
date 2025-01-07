package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Adapter.Classify_tree_classic;
import tlu.edu.vn.ht63.htnongnghiep.Component.Subcomponent.MenuFragment;
import tlu.edu.vn.ht63.htnongnghiep.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Library_informationTree_view#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Library_informationTree_view extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Library_informationTree_view() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment library_informationTree_view.
     */
    // TODO: Rename and change types and number of parameters
    public static Library_informationTree_view newInstance(String param1, String param2) {
        Library_informationTree_view fragment = new Library_informationTree_view();
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
        View view  = inflater.inflate(R.layout.fragment_library_information_tree_view, container, false);

        View menu = view.findViewById(R.id.menu);
        MenuFragment.setMenu((AppCompatActivity)requireActivity(),menu);

        RecyclerView contentRecycleView = view.findViewById(R.id.contentRecycleView);
        contentRecycleView.setLayoutManager(new LinearLayoutManager(requireContext()));

        List<String> list = new ArrayList<>();
        list.add("Cây thân thảo");
        list.add("Cây thân gỗ");
        list.add("Cây thân leo");
        list.add("Cây thủy sinh");
        list.add("Cây khí sinh");
        Classify_tree_classic classify_tree_classic = new Classify_tree_classic(list);
        classify_tree_classic.loadTreeData();
        contentRecycleView.setAdapter(classify_tree_classic);

        return view;
    }
}