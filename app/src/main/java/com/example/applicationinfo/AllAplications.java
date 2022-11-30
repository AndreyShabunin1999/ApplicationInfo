package com.example.applicationinfo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class AllAplications extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_aplications);

        initView();

        PackageMan packageMan = new PackageMan(getPackageManager());

        adapter = new MyAdapter(AllAplications.this, packageMan.arrayListApp);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(AllAplications.this, 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(adapter);
    }

    private void initView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview1);
    }
}