package com.google.android.gms.samples.vision.ocrreader;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Medicine implements Parcelable {

    String drugName = "";
    String Generic = "";
    String GenericPrice = "";
    ArrayList<String> Similars = new ArrayList<String>();
    ArrayList<String> SimilarsPrices = new ArrayList<String>();

    public Medicine() {}

    protected Medicine(Parcel in) {
        Generic = in.readString();
        GenericPrice = in.readString();
        Similars = in.createStringArrayList();
        SimilarsPrices = in.createStringArrayList();
    }

    public static final Creator<Medicine> CREATOR = new Creator<Medicine>() {
        @Override
        public Medicine createFromParcel(Parcel in) {
            return new Medicine(in);
        }

        @Override
        public Medicine[] newArray(int size) {
            return new Medicine[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Generic);
        dest.writeString(GenericPrice);
        dest.writeStringList(Similars);
        dest.writeStringList(SimilarsPrices);
    }
}
