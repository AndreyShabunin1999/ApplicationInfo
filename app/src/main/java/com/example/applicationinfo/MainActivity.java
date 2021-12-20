package com.example.applicationinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

public class MainActivity extends AppCompatActivity {

    DayNightSwitch dayNightSwitch;
    boolean switchState;
    ImageView img_logo;
    ImageButton btn_option;
    ImageButton btn_scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        SharedPreferences prefs = getSharedPreferences("test", Context.MODE_PRIVATE);
        switchState = prefs.getBoolean("switchState", false);

        dayNightSwitch.setIsNight(switchState);

        if(dayNightSwitch.isNight()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            img_logo.setBackgroundResource(R.drawable.night_logo);
            btn_option.setBackgroundResource(R.drawable.night_option);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            img_logo.setBackgroundResource(R.drawable.day_logo);
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

        //Если пользователь нажал кнопку Scan
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AllAplications.class);
                startActivity(intent);
            }
        });
    }

    //Функция инициализации
    private void initView(){
        dayNightSwitch = (DayNightSwitch)findViewById(R.id.day_night);
        img_logo = (ImageView)findViewById(R.id.img_logo);
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