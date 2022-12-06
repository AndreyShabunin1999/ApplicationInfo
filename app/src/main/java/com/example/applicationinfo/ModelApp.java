package com.example.applicationinfo;

import android.graphics.drawable.Drawable;

import java.io.File;

public class ModelApp {

    public ModelApp(Integer uid, String titles, Drawable mImages, File file, String packageName, String version) {
        this.uid = uid;
        this.titles = titles;
        this.mImages = mImages;
        this.file = file;
        this.packageName = packageName;
        this.version = version;
    }

    private Integer uid;

    private String titles;

    private Drawable mImages;

    private File file;

    private String packageName;

    private String version;

    public void setUid(Integer uid) {this.uid = uid;}

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public void setmImages(Drawable mImages) {
        this.mImages = mImages;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setPackageName(String package_name) {
        this.packageName = package_name;
    }

    public Integer getUid() {return uid;}

    public String getTitles() {
        return titles;
    }

    public Drawable getmImages() {
        return mImages;
    }

    public File getFile() {
        return file;
    }

    public String getPackage_name() {
        return packageName;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
