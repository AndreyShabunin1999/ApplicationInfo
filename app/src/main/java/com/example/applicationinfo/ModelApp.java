package com.example.applicationinfo;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

public class ModelApp implements Parcelable {

    public ModelApp(String titles, Drawable mImages, File file, String package_name, String version) {
        this.titles = titles;
        this.mImages = mImages;
        this.file = file;
        this.package_name = package_name;
        this.version = version;
    }

    private String titles;

    private Drawable mImages;

    private File file;

    private String package_name;

    private String version;

    protected ModelApp(Parcel in) {
        titles = in.readString();
        package_name = in.readString();
        version = in.readString();
    }

    public static final Creator<ModelApp> CREATOR = new Creator<ModelApp>() {
        @Override
        public ModelApp createFromParcel(Parcel in) {
            return new ModelApp(in);
        }

        @Override
        public ModelApp[] newArray(int size) {
            return new ModelApp[size];
        }
    };

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public void setmImages(Drawable mImages) {
        this.mImages = mImages;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

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
        return package_name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] { titles, mImages.toString(), file.toString(), package_name, version });
    }
}
