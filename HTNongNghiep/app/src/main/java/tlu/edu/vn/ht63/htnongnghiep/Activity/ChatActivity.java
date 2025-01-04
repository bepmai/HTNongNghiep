package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Component.Subcomponent.MenuFragment;
import tlu.edu.vn.ht63.htnongnghiep.R;
import tlu.edu.vn.ht63.htnongnghiep.Adapter.ChatAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Container.UI.SearchFragment;
import tlu.edu.vn.ht63.htnongnghiep.Model.Chat;
import tlu.edu.vn.ht63.htnongnghiep.Model.Search;


public class ChatActivity extends AppCompatActivity {
    private List<Chat> chatlst;
    private List<Search> searchlst;
    private RecyclerView recyclerView;
    private EditText search;
    ChatAdapter adapter;
    private SearchFragment searchFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        search = findViewById(R.id.search);
        init();
        chatlst = new ArrayList<>();
        chatlst.add(new Chat("John Doe", "Mai", "Chi có thể để cây ra ban công hứng nắng khoảng 30 phút mỗi buổi sáng....", "2 hours ago"));
        adapter = new ChatAdapter(chatlst, this);
        recyclerView.setAdapter(adapter);
        searchlst = new ArrayList<>();
        View menu = findViewById(R.id.menu);
        MenuFragment.setMenu((AppCompatActivity) this,menu);

        // Thêm dữ liệu vào searchlst nếu cần (giả lập)
        searchlst.add(new Search("John Doe", "https://www.w3schools.com/w3images/avatar2.png"));
        searchlst.add(new Search("Jane Smith", "https://www.w3schools.com/w3images/avatar6.png"));
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    showSearchFragment(s.toString());
                } else {
                    hideSearchFragment();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void showSearchFragment(String query){
        if(searchFragment == null){
            searchFragment = new SearchFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.chatRecyclerView, searchFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        List<Search> filterList = new ArrayList<>();
        for(Search chat: searchlst){
            if(chat.getName().toLowerCase().contains(query.toLowerCase())){
                filterList.add(chat);
            }
        }
        if (searchFragment != null){
            searchFragment.updateSearchResults(filterList);
        }
    }
    private void hideSearchFragment() {
        if (searchFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(searchFragment);
            transaction.commit();
            searchFragment = null;
        }
    }
    private void init(){
        recyclerView = findViewById(R.id.chatRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}