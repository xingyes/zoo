package com.xingye.netzoo.zooapplication.splash;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.main.MainActivity;


public class SplashActivity extends Activity{

    private Handler handler = new Handler(Looper.myLooper());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                UiUtils.startActivity(SplashActivity.this, MainActivity.class,true);
                finish();
            }
        },1000);


    }


    @Override
    protected void onResume()
    {
        super.onResume();
    }



}
