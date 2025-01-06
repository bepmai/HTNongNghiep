package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.SearchAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.Search;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private List<Search> list;
    private SearchView searchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search); // Đảm bảo bạn tạo tệp layout này
        searchView = findViewById(R.id.search);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });
        init();
        list = new ArrayList<>();
        list.add(new Search("Nguyễn Phương Mai", "https://www.w3schools.com/w3images/avatar2.png"));
        list.add(new Search("Nguyễn Đức Anh", "https://www.w3schools.com/w3images/avatar6.png"));
        list.add(new Search("Lê Quang Thanh", "https://www.w3schools.com/w3images/avatar2.png"));
        list.add(new Search("Nguyễn Tuấn Ngọc", "https://www.w3schools.com/w3images/avatar6.png"));
        adapter = new SearchAdapter(list, this);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateSearchResults(List<Search> results) {
        if (list == null) list = new ArrayList<>();
        list.clear();
        list.addAll(results);
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        } else {
            // Log lỗi hoặc khởi tạo adapter nếu cần
            Log.e("SearchActivity", "Adapter is null. Update failed.");
        }
    }
    private void filterList(String text){
        List<Search> filteredList = new ArrayList<>();
        for (Search search: list){
            if(search.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(search);
            }
        }
        if(filteredList.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }else{
            adapter.setFilteredList(filteredList);
        }
    }
    private void init() {
        recyclerView = findViewById(R.id.searchRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
