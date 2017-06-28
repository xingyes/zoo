package com.xingye.netzoo.zooapplication.outcall;

import com.xingye.netzoo.zooapplication.bookregister.DoctorModel;

/**
 * Created by yx on 17/6/11.
 */

public class OutcallDocModel {
    public DoctorModel doc;
    public String timetxt;

    public OutcallDocModel()
    {
        doc = new DoctorModel();
        timetxt="";
    }
}
