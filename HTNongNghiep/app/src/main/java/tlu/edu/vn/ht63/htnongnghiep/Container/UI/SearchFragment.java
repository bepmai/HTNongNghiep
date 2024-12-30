package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.SearchAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.Chat;
import tlu.edu.vn.ht63.htnongnghiep.Model.Search;


public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    SearchAdapter adapter;
    private List<Search> list;

    public SearchFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        list = new ArrayList<>();
        list.add(new Search("John Doe", "https://www.w3schools.com/w3images/avatar2.png"));
        list.add(new Search("Jane Doe", "https://www.w3schools.com/w3images/avatar6.png"));
        adapter = new SearchAdapter(list, getContext());
        recyclerView.setAdapter(adapter);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void updateSearchResults(List<Search> results) {
        if(list == null) list = new ArrayList<>();
        list.clear();
        list.addAll(results);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            // Log lỗi hoặc khởi tạo adapter nếu cần
            Log.e("SearchFragment", "Adapter is null. Update failed.");
        }
    }
    private void init(View view) {
        recyclerView = requireView().findViewById(R.id.chatRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}