package com.example.applicationinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        btnScan.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ApplicationListActivity.class);
            startActivity(intent);
        });
    }

    //Функция инициализации
    private void initView(){
        btnScan = findViewById(R.id.btn_scan);
    }
}