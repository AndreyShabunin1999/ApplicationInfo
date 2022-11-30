package com.example.applicationinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.nio.file.Files;

public class InfoApplication extends AppCompatActivity {

    Button btn_back;
    TextView name_app;
    ImageView logo_app;
    EditText edt_version, edt_package_name, edt_crc32;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_application);

        initView();

        Bundle extras = getIntent().getExtras();
        int index = extras.getInt("index");

        PackageMan packageMan = new PackageMan(getPackageManager());

        Resources res = context.getResources();

        name_app.setText(packageMan.arrayListApp.get(index).getTitles());
        logo_app.setImageDrawable(packageMan.arrayListApp.get(index).getmImages());
        edt_version.setText(res.getString(R.string.Version) + " " + packageMan.arrayListApp.get(index).getVersion());
        edt_package_name.setText(res.getString(R.string.Name_package_app) + " " + packageMan.arrayListApp.get(index).getPackage_name());

        java.util.zip.CRC32 x = new java.util.zip.CRC32();
        byte[] bytes = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            try {
                bytes = Files.readAllBytes(packageMan.arrayListApp.get(index).getFile().toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        x.update(bytes);

        edt_crc32.setText(res.getString(R.string.CRC32) + " " + Long.toHexString(x.getValue()));

        //Нажатие на кнопку назад
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initView(){
        btn_back = (Button) findViewById(R.id.btn_back);
        name_app = (TextView) findViewById(R.id.name_app);
        logo_app = (ImageView) findViewById(R.id.logo_app);
        edt_version = (EditText) findViewById(R.id.edt_version);
        context = btn_back.getContext();
        edt_package_name = (EditText) findViewById(R.id.edt_package_name);
        edt_crc32 = (EditText) findViewById(R.id.edt_crc32);
    }
}