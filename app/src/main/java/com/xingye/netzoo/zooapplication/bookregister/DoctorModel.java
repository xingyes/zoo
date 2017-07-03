package com.xingye.netzoo.zooapplication.bookregister;

import android.os.Parcel;
import android.os.Parcelable;

import com.xingye.netzoo.zooapplication.mycenter.Base3tvModel;
import com.xingye.netzoo.zooapplication.patient.PatientModel;

/**
 * Created by yx on 17/6/11.
 */

public class DoctorModel extends Base3tvModel implements Parcelable{
    public String name;
    public String head;
    public String title;
    public String category;
    public String brief;

    public String get1Txt(){return category;}
    public String get2Txt(){return name;}
    public String get3Txt(){return title;}

    @Override
    public int describeContents() {
        return 5;
    }
    public DoctorModel()
    {
        name="";
        head="";
        title="";
        category="";
        brief="";
    }

    public DoctorModel(Parcel in)
    {
        name = in.readString();
        head = in.readString();
        title = in.readString();
        category = in.readString();
        brief = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(head);
        parcel.writeString(title);
        parcel.writeString(category);
        parcel.writeString(brief);
    }

    public static final Parcelable.Creator<DoctorModel> CREATOR = new Parcelable.Creator<DoctorModel>()
    {

        @Override
        public DoctorModel createFromParcel(Parcel parcel) {
            return new DoctorModel(parcel);
        }

        @Override
        public DoctorModel[] newArray(int i) {
            return new DoctorModel[i];
        }
    };
}
