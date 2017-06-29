package com.xingye.netzoo.zooapplication.login;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yx on 17/6/29.
 */

public class LoginUser extends RealmObject {

    @PrimaryKey
    private  String id;

    private String name;
    private String phone;
    private String headimg;

    public String getId(){return id;}
    public void setId(final String aStr){ id = aStr;}

    public String getName(){return name;}
    public void setName(final String aStr){ name = aStr;}

    public String getPhone(){return phone;}
    public void setPhone(final String aStr){ phone = aStr;}

    public String getHeadimg(){return headimg;}
    public void setHeadimg(final String aStr){headimg = aStr;}
}
