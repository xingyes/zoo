package com.xingye.netzoo.zooapplication.medicreport;

import com.xingye.netzoo.xylib.utils.ToolUtil;
import com.xingye.netzoo.zooapplication.mycenter.Base3tvModel;

/**
 * Created by yx on 17/6/11.
 */

public class ReportModel extends Base3tvModel {
    public long  time;
    public String title;
    public String img;

    public String formTitle()
    {
        return ToolUtil.toDate(time,"yyyy-MM-dd") + "  " + title;
    }

    @Override
    public String get1Txt() {
        return ToolUtil.toDate(time,"yyyy-MM-dd");
    }

    @Override
    public String get2Txt() {
        return title;
    }
}
