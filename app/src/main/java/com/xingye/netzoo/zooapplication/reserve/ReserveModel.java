package com.xingye.netzoo.zooapplication.reserve;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.bookregister.DoctorModel;
import com.xingye.netzoo.zooapplication.patient.PatientModel;

/**
 * Created by yx on 17/6/11.
 */

public class ReserveModel implements Parcelable {
    public static final String RESERVE_TYPE = "RESERVE_TYPE";
    public int     reserveType;
    public static final int  RESERVE_PLASTER = 0;
    public static final int  RESERVE_PRESCRIP = 1;


    public String category;
    public int       reserveTimeLen;
    public long   [] reserveTime;
    public long  orderTime;

    public DoctorModel doc;
    public PatientModel patient;

    public long   deliverFee;
    public long   medicFee;


    public ReserveModel(final Context context, int type)
    {
        switch(type)
        {
            case RESERVE_PLASTER:
                reserveType = type;
                category = context.getString(R.string.medic_plaster);
                reserveTimeLen = 3;
                reserveTime = new long[reserveTimeLen];
                reserveTime[0] = System.currentTimeMillis() + 86400000;
                reserveTime[1] = System.currentTimeMillis() - 86400000*10;
                reserveTime[2] = System.currentTimeMillis() - 86400000*20;
                deliverFee = 3;
                medicFee = 200;
                doc = new DoctorModel();
                patient = new PatientModel();
                break;
            case RESERVE_PRESCRIP:
                reserveType = type;
                category = context.getString(R.string.china_prescirption);
                reserveTimeLen = 1;
                reserveTime = new long[reserveTimeLen];
                reserveTime[0] = System.currentTimeMillis();
                doc = new DoctorModel();
                doc.name = "牛医生";
                patient = new PatientModel();
                patient.name = "老王";
                deliverFee = 3;
                medicFee = 200;
                break;
            default:
                reserveType = -1;
                category = "";
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeInt(reserveType);
        parcel.writeString(category);
        parcel.writeInt(reserveTimeLen);
        parcel.writeLongArray(reserveTime);
        parcel.writeLong(orderTime);
        parcel.writeLong(medicFee);
        parcel.writeLong(deliverFee);
        parcel.writeParcelable(doc,flag);
        parcel.writeParcelable(patient,flag);

    }

    public ReserveModel(Parcel in)
    {
        reserveType = in.readInt();
        category = in.readString();
        reserveTimeLen = in.readInt();
        if(reserveTimeLen>0) {
            reserveTime =new long[reserveTimeLen];
            in.readLongArray(reserveTime);
        }
        orderTime = in.readLong();
        medicFee = in.readLong();
        deliverFee = in.readLong();
        doc = in.readParcelable(DoctorModel.class.getClassLoader());
        patient = in.readParcelable(PatientModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<ReserveModel> CREATOR = new Parcelable.Creator<ReserveModel>()
    {

        @Override
        public ReserveModel createFromParcel(Parcel parcel) {
            return new ReserveModel(parcel);
        }

        @Override
        public ReserveModel[] newArray(int i) {
            return new ReserveModel[i];
        }
    };
}
