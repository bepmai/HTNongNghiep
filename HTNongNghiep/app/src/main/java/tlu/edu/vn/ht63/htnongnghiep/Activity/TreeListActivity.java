package tlu.edu.vn.ht63.htnongnghiep.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import tlu.edu.vn.ht63.htnongnghiep.Adapter.TreeAdapter;
import tlu.edu.vn.ht63.htnongnghiep.Model.Tree;
import tlu.edu.vn.ht63.htnongnghiep.R;

public class TreeListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Tree> treeList;
    TreeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_product);

        recyclerView = findViewById(R.id.recyclerView);
        treeList = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        }

    }