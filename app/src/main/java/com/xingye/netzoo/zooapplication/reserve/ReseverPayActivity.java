package com.xingye.netzoo.zooapplication.reserve;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingye.netzoo.xylib.utils.ToolUtil;
import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;


public class ReseverPayActivity extends Activity implements View.OnClickListener{

    public static  final String  RESERVE_MODEL = "RESERVE_MODEL";
    private TextView  titlev;
    private ImageView pretitleimv;

    private ReserveModel reserveModel;
    private int  reserveType;
    private LinearLayout contentLayout;

    private TextView   addrv;
    private TextView   addrbtn;
    public class PaywayHolder
    {
        public CheckBox paycheck;
        public TextView tv;
        public ImageView imv;
    }

    private PaywayHolder alipay;  //0
    private PaywayHolder wxpay;   //1
    private PaywayHolder unionpay; //2

    private int  paychoose = 0;
    private CompoundButton.OnCheckedChangeListener  checkListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if(null == intent)
        {
            finish();
            return;
        }

        reserveModel = intent.getParcelableExtra(RESERVE_MODEL);

        setContentView(R.layout.activity_reserve_pay);
        NaviBar naviBar = (NaviBar) findViewById(R.id.reserve_pay_nav);
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

        findViewById(R.id.pay_btn).setOnClickListener(this);

        alipay = new PaywayHolder();
        View view = findViewById(R.id.alipay_layout);
        view.setOnClickListener(this);
        alipay.tv = (TextView)view.findViewById(R.id.info_key);
        alipay.imv = (ImageView)view.findViewById(R.id.info_icon);
        alipay.paycheck = (CheckBox) view.findViewById(R.id.info_check);

        wxpay = new PaywayHolder();
        view = findViewById(R.id.wxpay_layout);
        view.setOnClickListener(this);
        wxpay.tv = (TextView)view.findViewById(R.id.info_key);
        wxpay.imv = (ImageView)view.findViewById(R.id.info_icon);
        wxpay.paycheck = (CheckBox) view.findViewById(R.id.info_check);

        unionpay = new PaywayHolder();
        view = findViewById(R.id.unionpay_layout);
        view.setOnClickListener(this);
        unionpay.tv = (TextView)view.findViewById(R.id.info_key);
        unionpay.imv = (ImageView)view.findViewById(R.id.info_icon);
        unionpay.paycheck = (CheckBox) view.findViewById(R.id.info_check);

        alipay.tv.setText(R.string.alipay);
        wxpay.tv.setText(R.string.weixinpay);
        unionpay.tv.setText(R.string.unionpay);
        alipay.imv.setImageResource(R.mipmap.icon_alipay);
        wxpay.imv.setImageResource(R.mipmap.icon_weixin);
        unionpay.imv.setImageResource(R.mipmap.icon_unionpay);

        paychoose = 0;


        ShowReserveInfo();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pay_btn:
                UiUtils.makeToast(this,"支付成功" + paychoose);
                break;
            case R.id.alipay_layout:
                paychoose = 0;
                alipay.paycheck.setChecked(true);
                wxpay.paycheck.setChecked(false);
                unionpay.paycheck.setChecked(false);
                break;
            case R.id.wxpay_layout:
                paychoose = 1;
                alipay.paycheck.setChecked(false);
                wxpay.paycheck.setChecked(true);
                unionpay.paycheck.setChecked(false);
                break;
            case R.id.unionpay_layout:
                paychoose = 2;
                alipay.paycheck.setChecked(false);
                wxpay.paycheck.setChecked(false);
                unionpay.paycheck.setChecked(true);
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


    private void ShowReserveInfo()
    {
        //医药费用
        long fee = reserveModel.medicFee;
        if(reserveModel.reserveTime!=null)
        {
            if(reserveModel.reserveTime.length>1) {
                addReservItem(getString(R.string.medic_fee), fee + "*" + reserveModel.reserveTime.length);
                fee = fee * reserveModel.reserveTime.length;
            }
            else
                addReservItem(getString(R.string.medic_fee),""+fee);
        }

        //配送费用
        addReservItem(getString(R.string.deliver_fee),""+reserveModel.deliverFee);
        fee+=reserveModel.deliverFee;

        addReservItem(getString(R.string.total_fee),""+fee);
    }

}
