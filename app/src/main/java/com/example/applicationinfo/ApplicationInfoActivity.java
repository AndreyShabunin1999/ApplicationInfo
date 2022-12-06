package com.example.applicationinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

public class ApplicationInfoActivity extends AppCompatActivity {

    Button btnBack, btnOpenApp;
    TextView nameApp;
    ImageView logoApp;
    EditText edtVersion, edtPackageName, edtCrc32;
    Context context;
    static Resources res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_application);

        initView();

        Bundle extras = getIntent().getExtras();
        int index = extras.getInt("index");

        PackageManagerWrapper packageManagerWrapper = new PackageManagerWrapper(getPackageManager());

        res = context.getResources();

        nameApp.setText(packageManagerWrapper.arrayListApp.get(index).getTitles());
        logoApp.setImageDrawable(packageManagerWrapper.arrayListApp.get(index).getmImages());
        edtVersion.setText(res.getString(R.string.VersionCode, packageManagerWrapper.arrayListApp.get(index).getVersion()));
        edtPackageName.setText(res.getString(R.string.NamePackageAppCode, packageManagerWrapper.arrayListApp.get(index).getPackage_name()));

        java.util.zip.CRC32 x = new java.util.zip.CRC32();
        byte[] bytes = new byte[(int) packageManagerWrapper.arrayListApp.get(index).getFile().length()];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            try {
                bytes = Files.readAllBytes(packageManagerWrapper.arrayListApp.get(index).getFile().toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            FileInputStream fileInputStream;
            try
            {
                fileInputStream = new FileInputStream(packageManagerWrapper.arrayListApp.get(index).getFile());
                fileInputStream.read(bytes);
                fileInputStream.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        x.update(bytes);

        edtCrc32.setText(res.getString(R.string.CRC32Code,  Long.toHexString(x.getValue())));

        //Нажатие на кнопку назад
        btnBack.setOnClickListener(view -> onBackPressed());

        btnOpenApp.setOnClickListener(view -> openApp(ApplicationInfoActivity.this, packageManagerWrapper.arrayListApp.get(index).getPackage_name()));

    }

    public static void openApp(Context context, String packageName) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent intent = manager.getLaunchIntentForPackage(packageName);
            if (intent == null) {
                Toast.makeText(context, res.getString(R.string.noActivity, packageName), Toast.LENGTH_LONG).show();
            } else {
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                context.startActivity(intent);
            }
        } catch (ActivityNotFoundException ignored) {
        }
    }


    private void initView(){
        btnBack = findViewById(R.id.btn_back);
        btnOpenApp = findViewById(R.id.btn_open_app);
        nameApp = findViewById(R.id.name_app);
        logoApp = findViewById(R.id.logo_app);
        edtVersion = findViewById(R.id.edt_version);
        context = btnBack.getContext();
        edtPackageName = findViewById(R.id.edt_package_name);
        edtCrc32 = findViewById(R.id.edt_crc32);
    }
}