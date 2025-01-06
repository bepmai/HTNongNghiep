package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import tlu.edu.vn.ht63.htnongnghiep.Activity.AddPlant;
import tlu.edu.vn.ht63.htnongnghiep.Model.TreeLib;
import tlu.edu.vn.ht63.htnongnghiep.Model.TreeUser;
import tlu.edu.vn.ht63.htnongnghiep.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link analysis_AnalysisResult_view#newInstance} factory method to
 * create an instance of this fragment.
 */
public class analysis_AnalysisResult_view extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private GestureDetector gestureDetector;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TreeLib treelib;
    private TreeUser treeUsr;

    public analysis_AnalysisResult_view() {
        // Required empty public constructor
    }
    public analysis_AnalysisResult_view(TreeLib tree){
        this.treelib = tree;
    }
    public analysis_AnalysisResult_view(TreeUser tree){
        this.treeUsr = tree;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AnalysisResult_view.
     */
    // TODO: Rename and change types and number of parameters
    public static analysis_AnalysisResult_view newInstance(String param1, String param2) {
        analysis_AnalysisResult_view fragment = new analysis_AnalysisResult_view();
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
        View view = inflater.inflate(R.layout.fragment_analysis_result_view, container, false);

        TextView treeResult = view.findViewById(R.id.treeResult);
        ImageView imageResult = view.findViewById(R.id.imageResult);
        Button addTreeForFarm = view.findViewById(R.id.addTreeForFarm);

        // set text
        treeResult.setText(treelib.getName());

        // set ảnh
        if (treelib.getImages() != null && !treelib.getImages().isEmpty()) {
            Glide.with(imageResult)
                    .load(treelib.getImages().get(0))
                    .into(imageResult);
        } else {
            Glide.with(imageResult)
                    .load(R.drawable.baseline_cloud_24)
                    .into(imageResult);
        }

        addTreeForFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPlant = new Intent(requireActivity(), AddPlant.class);
                addPlant.putExtra("treelib", treelib);
                startActivity(addPlant);
            }
        });

        // khoảng thoát
        FrameLayout outrange = view.findViewById(R.id.outrange);
        outrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        // set vị trí kéo để chuyển trang
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (e1 == null || e2 == null)
                {
                    onSwipeUp();
                    return true;
                }
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();

                if (Math.abs(diffX) < Math.abs(diffY)) {
                    if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffY < 0) {
                            onSwipeUp();
                        } else {
                            onSwipeDown();
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        // set sự kiện cho việc kéo và thả ở scroll view
        View scrollResult = view.findViewById(R.id.scrollResult);
        scrollResult.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });

        return view;
    }
    private void onSwipeUp() {
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out
        );
        Fragment fragment = new analysis_InformationTree_view(treelib);
        fragmentTransaction.replace(R.id.main,fragment);
        fragmentTransaction.addToBackStack("InformationTree_view");
        fragmentTransaction.commit();
    }

    private void onSwipeDown() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}
