package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tlu.edu.vn.ht63.htnongnghiep.R;


public class ComunityFragment extends Fragment {

    public ComunityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comunity, container, false);
    }
}