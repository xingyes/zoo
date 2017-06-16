package com.xingye.netzoo.zooapplication.hospital;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xingye.netzoo.xylib.utils.DPIUtil;
import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.zooapplication.R;


public class TitleImgActivity extends Activity implements View.OnClickListener{

    private TextView     titlev;
    private TextView     briefv;
    private SimpleDraweeView    imgv;
    private ImageView    preimv;

    public static final int TYPE_OUTPATIENT = 0;
    public static final int TYPE_INSIDEMAP = 1;
    public static final int TYPE_TOHOSPITAL = 2;

    public int infoType = TYPE_OUTPATIENT;
    public static String INFO_SHOW_TYPE = "INFO_SHOW_TYPE";

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

        preimv = (ImageView)findViewById(R.id.pre_title_imgv);
        titlev = (TextView)findViewById(R.id.title_v);

        briefv = (TextView)findViewById(R.id.brief_tv);
        imgv = (SimpleDraweeView)findViewById(R.id.info_imv);
        infoType = intent.getIntExtra(INFO_SHOW_TYPE,TYPE_OUTPATIENT);
        Bitmap bitmap;
        ViewGroup.LayoutParams lp = imgv.getLayoutParams();
        switch (infoType)
        {
            case TYPE_INSIDEMAP:
                naviBar.setTitle(R.string.inside_map);
                preimv.setImageResource(R.mipmap.guide);
                titlev.setText(R.string.inside_map);
                briefv.setVisibility(View.GONE);
                bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.hospital_map);
                lp.width = DPIUtil.getWidth()-20;
                lp.height = bitmap.getHeight()*lp.width/bitmap.getWidth();
                imgv.setLayoutParams(lp);
                imgv.setImageURI("res:///" + R.mipmap.hospital_map);
                break;
            case TYPE_TOHOSPITAL:
                naviBar.setTitle(R.string.to_hospital_map);
                preimv.setImageResource(R.mipmap.map_poi);
                titlev.setText(R.string.to_hospital_map);
                briefv.setVisibility(View.VISIBLE);
                briefv.setText(R.string.hospital_addr);
                bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.to_hospital);
                lp.width = DPIUtil.getWidth()-20;
                lp.height = bitmap.getHeight()*lp.width/bitmap.getWidth();
                imgv.setLayoutParams(lp);
                imgv.setImageURI("res:///" + R.mipmap.to_hospital);
                break;
            case TYPE_OUTPATIENT:
            default:
                naviBar.setTitle(R.string.outpatient_guide);
                preimv.setImageResource(R.mipmap.icon_outpatient);
                titlev.setText(R.string.see_doctor_process);
                briefv.setVisibility(View.GONE);
                bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.outpation_detail);
                lp.width = DPIUtil.getWidth()-20;
                lp.height = bitmap.getHeight()*lp.width/bitmap.getWidth();
                imgv.setLayoutParams(lp);
                imgv.setImageURI("res:///" + R.mipmap.outpation_detail);
                break;
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
