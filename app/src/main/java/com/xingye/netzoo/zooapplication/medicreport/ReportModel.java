package com.xingye.netzoo.zooapplication.medicreport;

/**
 * Created by yx on 17/6/11.
 */

public class ReportModel {
    public String date;
    public String title;
    public String img;

    public String formTitle()
    {
        return date + "  " + title;
    }
}
