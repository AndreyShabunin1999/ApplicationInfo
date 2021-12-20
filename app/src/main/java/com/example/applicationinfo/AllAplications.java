package com.example.applicationinfo;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

import java.util.ArrayList;
import java.util.List;


public class AllAplications extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private List<String> titles;
    private List<Drawable> mImages;
    private MyAdapter adapter;

    DayNightSwitch dayNightSwitch;
    boolean switchState;
    ImageButton btn_option;
    ImageButton btn_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_aplications);

        initView();

        SharedPreferences prefs = getSharedPreferences("test", Context.MODE_PRIVATE);
        switchState = prefs.getBoolean("switchState", false);

        dayNightSwitch.setIsNight(switchState);

        if(dayNightSwitch.isNight()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            btn_option.setBackgroundResource(R.drawable.night_option);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            btn_option.setBackgroundResource(R.drawable.day_option);
        }

        dayNightSwitch.setDuration(450);
        dayNightSwitch.setListener(new DayNightSwitchListener() {
            @Override
            public void onSwitch(boolean is_night) {
                switchState = is_night;
                if(is_night){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        titles = new ArrayList<>();
        mImages = new ArrayList<>();

        adapter = new MyAdapter(AllAplications.this, titles, mImages);

        final PackageManager pm = getPackageManager();

        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            try {
                String appName = (String) pm.getApplicationLabel(pm.getApplicationInfo(packageInfo.packageName, PackageManager.GET_META_DATA));
                Drawable drawable = packageInfo.loadIcon(pm);
                mImages.add(packageInfo.loadIcon(pm));
                titles.add(appName);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(AllAplications.this, 2, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(adapter);
    }

    private void initView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview1);
        dayNightSwitch = (DayNightSwitch)findViewById(R.id.day_night);
        btn_scan = (ImageButton)findViewById(R.id.btn_scan);
        btn_option = (ImageButton)findViewById(R.id.btn_option);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // пишем нужное в SharedPreferences состояние свича
        SharedPreferences.Editor ed = getSharedPreferences("test", Context.MODE_PRIVATE).edit();
        // пишем в SharedPreferences состояние свича
        ed.putBoolean("switchState", switchState);
        ed.commit();
    }
}