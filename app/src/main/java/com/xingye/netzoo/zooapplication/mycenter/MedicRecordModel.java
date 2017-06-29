package com.xingye.netzoo.zooapplication.mycenter;

import com.xingye.netzoo.xylib.utils.ToolUtil;

/**
 * Created by yx on 17/6/11.
 */

public class MedicRecordModel extends Base3tvModel{
    public long   time;
    public String category;

    @Override
    public String get1Txt() {
        return ToolUtil.toDate(time,"yyyy-MM-dd");
    }

    @Override
    public String get2Txt() {
        return ToolUtil.toDate(time,"HH:mm:ss");
    }

    @Override
    public String get3Txt() {
        return category;
    }
}
