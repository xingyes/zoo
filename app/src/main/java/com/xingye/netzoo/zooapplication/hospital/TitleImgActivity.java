package com.xingye.netzoo.zooapplication.hospital;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xingye.netzoo.xylib.utils.DPIUtil;
import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;


public class TitleImgActivity extends Activity implements View.OnClickListener{

    private TextView     titlev;
    private TextView     briefv;
    private SimpleDraweeView    imgv;
    private ImageView    titleimv;

    public static String NAV_TXT = "NAV_TXT";
    public static String TITLE_TXT = "TITLE_TXT";
    public static String TITLE_IMG = "TITLE_IMG";
    public static String BRIEF_TXT = "BRIEF_TXT";
    public static String IMG_URL = "IMG_URL";
    public static String IMG_RID = "IMG_RID";
    private int       titlePreImgRid;
    private String    navTxt;
    private String    titleTxt;
    private String    briefTxt;
    private String    imgUrl;
    private int       imgRid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent==null)
        {
            finish();
            return;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_img);


        NaviBar naviBar = (NaviBar)findViewById(R.id.title_img_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                                finish();
                                           }
                                       });

        titleimv = (ImageView)findViewById(R.id.pre_title_imgv);
        titlev = (TextView)findViewById(R.id.title_v);
        briefv = (TextView)findViewById(R.id.brief_tv);
        imgv = (SimpleDraweeView)findViewById(R.id.info_imv);

        navTxt = intent.getStringExtra(NAV_TXT);
        titleTxt = intent.getStringExtra(TITLE_TXT);
        titlePreImgRid = intent.getIntExtra(TITLE_IMG,-1);
        briefTxt = intent.getStringExtra(BRIEF_TXT);
        imgRid = intent.getIntExtra(IMG_RID,-1);
        imgUrl = intent.getStringExtra(IMG_URL);
        naviBar.setTitle(navTxt);
        titlev.setText(titleTxt);

        if(titlePreImgRid>0)
            titleimv.setImageResource(titlePreImgRid);

        briefv.setVisibility(TextUtils.isEmpty(briefTxt) ? View.GONE : View.VISIBLE);
        briefv.setText(TextUtils.isEmpty(briefTxt) ? "": briefTxt);

        Bitmap bitmap;
        ViewGroup.LayoutParams lp = imgv.getLayoutParams();
        if(imgRid>=0)
        {
            bitmap = BitmapFactory.decodeResource(getResources(),imgRid);
            lp.width = DPIUtil.getWidth()-20;
            lp.height = bitmap.getHeight()*lp.width/bitmap.getWidth();
            imgv.setLayoutParams(lp);
            imgv.setImageURI("res:///" + imgRid);
        }else if(!TextUtils.isEmpty(imgUrl))
        {
            UiUtils.setControllerListener(imgv,imgUrl,DPIUtil.getWidth()-20);
        }
    }


    @Override
    protected void onResume()
    {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            default:
                break;
        }
    }



}
