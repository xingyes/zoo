package com.xingye.netzoo.zooapplication.utils;

/**
 * Created by yx on 17/3/1.
 */

public class AddFuncUtils {

    static{
        System.loadLibrary("AddFuncUtils");
    }

    public native int add(int a,int b);
}
