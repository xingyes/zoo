package com.xingye.netzoo.zooapplication.bookregister;

import com.xingye.netzoo.zooapplication.mycenter.Base3tvModel;

/**
 * Created by yx on 17/6/11.
 */

public class DoctorModel extends Base3tvModel {
    public String name;
    public String head;
    public String title;
    public String category;
    public String brief;

    public String get1Txt(){return category;}
    public String get2Txt(){return name;}
    public String get3Txt(){return title;}

}
