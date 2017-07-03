package com.xingye.netzoo.zooapplication.reserve;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xingye.netzoo.xylib.utils.DPIUtil;
import com.xingye.netzoo.xylib.utils.ToolUtil;
import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.bookregister.RegisterDetailActivity;
import com.xingye.netzoo.zooapplication.main.MainActivity;
import com.xingye.netzoo.zooapplication.patient.PatientEditActivity;
import com.xingye.netzoo.zooapplication.patient.PatientManageActivity;
import com.xingye.netzoo.zooapplication.patient.PatientModel;

import java.util.zip.Inflater;


public class ReseverDetailActivity extends Activity implements View.OnClickListener{

    private TextView  titlev;
    private ImageView pretitleimv;

    private ReserveModel reserveModel;
    private int  reserveType;
    private LinearLayout contentLayout;

    private TextView   addrv;
    private TextView   addrbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(null == intent)
        {
            finish();
            return;
        }

        setContentView(R.layout.activity_reserve_detail);
        NaviBar naviBar = (NaviBar) findViewById(R.id.reserve_detail_nav);
        naviBar.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        reserveType = intent.getIntExtra(ReserveModel.RESERVE_TYPE,ReserveModel.RESERVE_PLASTER);
        titlev = (TextView)findViewById(R.id.title_v);
        pretitleimv = (ImageView)findViewById(R.id.pre_title_imgv);

        contentLayout = (LinearLayout)findViewById(R.id.content_layout);

        View view = findViewById(R.id.address_info);
        addrv = (TextView)view.findViewById(R.id.info_key);
        addrbtn = (TextView)view.findViewById(R.id.info_func);
        addrbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiUtils.makeToast(ReseverDetailActivity.this,"选择地址");
            }
        });
        addrv.setText("南京东路100弄10号1001");
        findViewById(R.id.register_btn).setOnClickListener(this);

        fakeOne();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.register_btn:
                bundle.putParcelable(ReseverPayActivity.RESERVE_MODEL,reserveModel);
                UiUtils.startActivity(this,ReseverPayActivity.class,bundle,true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == CHOOSE_PATIENT && resultCode == RESULT_OK && data != null) {
//            PatientModel patient = data.getParcelableExtra(PatientEditActivity.PATIENT_RESULT);
//            patientGp.keyv.setText(patient.getBrief());
//
//        } else
//            super.onActivityResult(requestCode, resultCode, data);
    }


    public void addReservItem(final String key, final String value) {
        View itemv = getLayoutInflater().inflate(R.layout.item_key_value_withsep, null);
        TextView tv = (TextView) itemv.findViewById(R.id.info_key);
        tv.setText(key);
        tv = (TextView) itemv.findViewById(R.id.info_value);
        tv.setText(value);
        tv.setVisibility(View.VISIBLE);
        contentLayout.addView(itemv);
    }


    private void fakeOne()
    {
        reserveModel = new ReserveModel(this,reserveType);
        //预约时间
        for(int i=0; null!=reserveModel.reserveTime && i< reserveModel.reserveTime.length;i++)
        {
            String datestr = ToolUtil.toDate(reserveModel.reserveTime[i],
                    getString(com.xingye.netzoo.xylib.R.string.x_day));
            if(i == 0)
                addReservItem(getString(R.string.reserve_time),datestr);
            else
                addReservItem("",datestr);
        }

        //服务次数
        addReservItem(getString(R.string.serve_count),""+reserveModel.reserveTime.length);

        if(null!=reserveModel.doc && !TextUtils.isEmpty(reserveModel.doc.name))
            //医生
            addReservItem(getString(R.string.register_doc),reserveModel.doc.name);
        if(null!=reserveModel.patient && !TextUtils.isEmpty(reserveModel.patient.name))
            // 病人
            addReservItem(getString(R.string.patient),reserveModel.patient.name);


        //医药费用
        addReservItem(getString(R.string.medic_fee),""+reserveModel.medicFee);
        //配送费用
        addReservItem(getString(R.string.deliver_fee),""+reserveModel.deliverFee);


    }

}
