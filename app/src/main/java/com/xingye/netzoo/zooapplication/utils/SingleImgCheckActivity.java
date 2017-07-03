package com.xingye.netzoo.zooapplication.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.xylib.utils.ui.ZoomImageView;
import com.xingye.netzoo.zooapplication.R;


public class SingleImgCheckActivity extends Activity implements View.OnClickListener{

    private ZoomImageView     imgv;
    private String            imgUrl;
    public static final String IMG_URL = "IMG_URL";
    private Handler handler = new Handler(Looper.getMainLooper());
    private NaviBar naviBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent == null)
        {
            finish();
            return;
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_imv_check);
        naviBar = (NaviBar)findViewById(R.id.single_imv_check_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });

        imgUrl = intent.getStringExtra(IMG_URL);
        Uri uri = Uri.parse(imgUrl);
        try {
            UiUtils.fetchBitmap(uri, new UiUtils.FrescoBitmapCallback<Bitmap>() {
                        @Override
                        public void onSuccess(Uri uri, Bitmap result) {
                            final Bitmap bm = result;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    imgv.setImageBitmap(bm);
                                }
                            });

                        }

                        @Override
                        public void onFailure(Uri uri, Throwable throwable) {

                        }

                        @Override
                        public void onCancel(Uri uri) {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        imgv = (ZoomImageView) findViewById(R.id.content_imv);
        imgv.setImgFuncListener(new ZoomImageView.ImgFuncListener() {
            @Override
            public void onDetailShow() {

            }

            @Override
            public void onMenuShiftShowOrHide() {
                if(naviBar.getVisibility() == View.GONE)
                    naviBar.setVisibility(View.VISIBLE);
                else
                    naviBar.setVisibility(View.GONE);
            }
        });

    }


    @Override
    protected void onResume()
    {
        super.onResume();
    }


    @Override
    public void onClick(View v) {

    }
}
