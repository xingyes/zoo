package com.xingye.netzoo.zooapplication.patient;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

/**
 * Created by yx on 17/6/11.
 */
//Serializable
public class PatientModel implements Parcelable {
    public String name = "";
    public String phone = "";
    public String personid= "";
    public String medicid= "";


    public PatientModel()
    {

    }
    private PatientModel(Parcel in)
    {
        name = in.readString();
        phone = in.readString();
        personid = in.readString();
        medicid = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(personid);
        parcel.writeString(medicid);
    }


    public static final Parcelable.Creator<PatientModel> CREATOR = new Parcelable.Creator<PatientModel>()
    {

        @Override
        public PatientModel createFromParcel(Parcel parcel) {
            return new PatientModel(parcel);
        }

        @Override
        public PatientModel[] newArray(int i) {
            return new PatientModel[i];
        }
    };

    public String getBrief()
    {
        if(!TextUtils.isEmpty(phone))
            return name + " " + phone.substring(0,3)+"****"+phone.substring(7);
        return name;
    }
}
