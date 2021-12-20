package com.example.applicationinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahfa.dnswitch.DayNightSwitch;
import com.mahfa.dnswitch.DayNightSwitchListener;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InfoApplication extends AppCompatActivity {

    ImageButton btn_back, btn_option;
    DayNightSwitch dayNightSwitch;
    boolean switchState;
    TextView name_app;
    ImageView logo_app;
    EditText edt_size, edt_last_launch_date, edt_install;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_application);

        initView();

        SharedPreferences prefs = getSharedPreferences("test", Context.MODE_PRIVATE);
        switchState = prefs.getBoolean("switchState", false);

        dayNightSwitch.setIsNight(switchState);

        if(dayNightSwitch.isNight()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            btn_option.setBackgroundResource(R.drawable.night_option);
            btn_back.setBackgroundResource(R.drawable.night_back);
            edt_install.setTextColor(context.getResources().getColor(R.color.white));
            edt_size.setTextColor(context.getResources().getColor(R.color.white));
            edt_last_launch_date.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            btn_option.setBackgroundResource(R.drawable.day_option);
            btn_back.setBackgroundResource(R.drawable.day_back);
            edt_install.setTextColor(context.getResources().getColor(R.color.black));
            edt_size.setTextColor(context.getResources().getColor(R.color.black));
            edt_last_launch_date.setTextColor(context.getResources().getColor(R.color.black));
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

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        name_app.setText(name);

        final PackageManager pm = getPackageManager();

        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            try {
                String appName = (String) pm.getApplicationLabel(pm.getApplicationInfo(packageInfo.packageName, PackageManager.GET_META_DATA));
                if(appName.equals(name)){
                    logo_app.setImageDrawable(packageInfo.loadIcon(pm));

                    File file = new File(packageInfo.publicSourceDir);
                    Double size = (double) file.length()/(1024*1024);

                    size = new BigDecimal(size).setScale(2, RoundingMode.HALF_EVEN).doubleValue();

                    Resources res = context.getResources();

                    edt_size.setText(res.getString(R.string.Size_text) + " " + String.valueOf(size) + " " + res.getString(R.string.Mb));

                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                    PackageInfo info = pm.getPackageInfo(packageInfo.packageName, 0);
                    String data_install = sdf.format(new Date(info.firstInstallTime));
                    String data_update = sdf.format(new Date(info.lastUpdateTime));

                    edt_install.setText(res.getString(R.string.Install_date) + " " + data_install);
                    edt_last_launch_date.setText(res.getString(R.string.Last_date) + " " + data_update);

                    if(checkUsageStatsPermission()) {
                        showUsageStats(packageInfo.packageName);
                    } else {
                        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        //Нажатие на кнопку назад
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private boolean checkUsageStatsPermission() {
        AppOpsManager appOpsManager;
        int mode = 0;
        appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    private void showUsageStats(String pacm) {
        UsageStatsManager usageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,calendar.getTimeInMillis(),System.currentTimeMillis());
        String stats_data ="";
        Resources res = context.getResources();
        for(int i = 0; i < queryUsageStats.size() - 1; i++) {
            if(pacm.equals(queryUsageStats.get(i).getPackageName())){
                stats_data = covertTime(queryUsageStats.get(i).getLastTimeUsed());
                edt_last_launch_date.setText(res.getString(R.string.Last_date) + " " + stats_data);
            }
        }
    }

    private String covertTime(Long lastTimeUsed) {
        Date date = new Date(lastTimeUsed);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH);
        return sdf.format(date);

    }

    private void initView(){
        btn_back = (ImageButton) findViewById(R.id.btn_back);
        dayNightSwitch = (DayNightSwitch)findViewById(R.id.day_night);
        btn_option = (ImageButton)findViewById(R.id.btn_option);
        name_app = (TextView) findViewById(R.id.name_app);
        logo_app = (ImageView) findViewById(R.id.logo_app);
        edt_size = (EditText) findViewById(R.id.edt_size);
        context = btn_back.getContext();
        edt_last_launch_date = (EditText) findViewById(R.id.edt_last_launch_date);
        edt_install = (EditText) findViewById(R.id.edt_install);
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