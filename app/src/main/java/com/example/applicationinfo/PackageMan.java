package com.example.applicationinfo;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PackageMan {

    public ArrayList<ModelApp> arrayListApp;

    PackageMan(PackageManager pm){
        arrayListApp = new ArrayList<>();
        getAllInstallApp(pm);
    }

    public void getAllInstallApp(PackageManager pm){
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            try {
                String appName = (String) pm.getApplicationLabel(pm.getApplicationInfo(packageInfo.packageName, PackageManager.GET_META_DATA));

                PackageInfo info = pm.getPackageInfo(packageInfo.packageName, 0);

                File file = new File(packageInfo.publicSourceDir);

                ModelApp modelApp = new ModelApp(appName, packageInfo.loadIcon(pm), file, info.packageName, info.versionName);

                arrayListApp.add(modelApp);

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
