package com.xingye.netzoo.zooapplication;

import com.xingye.netzoo.xylib.utils.MyApplication;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by yx on 16/11/24.
 */

public class ZooApplication extends MyApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name("chinamedic.ln")
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }
}
