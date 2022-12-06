package com.example.applicationinfo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class ApplicationListActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_aplications);

        initView();

        PackageManagerWrapper packageManagerWrapper = new PackageManagerWrapper(getPackageManager());

        RecyclerView.Adapter<MyAdapter.MyViewHolder> adapter = new MyAdapter(ApplicationListActivity.this, packageManagerWrapper.arrayListApp);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(ApplicationListActivity.this, 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(adapter);
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recyclerview1);
    }
}