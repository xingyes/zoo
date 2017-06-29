package com.xingye.netzoo.zooapplication.mycenter;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xingye.netzoo.xylib.utils.ui.AppDialog;
import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.RadioDialog;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.login.LoginActivity;
import com.xingye.netzoo.zooapplication.login.LoginUser;
import com.xingye.netzoo.zooapplication.login.UserRegisterActivity;
import com.xingye.netzoo.zooapplication.main.BaseFragment;

import java.util.ArrayList;

import io.realm.Realm;


public class MyCenterFragment extends BaseFragment implements View.OnClickListener {

    private TextView         nametv;
    private SimpleDraweeView headv;

    private Realm     realm;
    private LoginUser loginUser;

    private RadioDialog   radioDialog;
    private RadioDialog.RadioAdapter   radioAdapter;
    private ArrayList<String> medicTypeStrs;
    private ArrayList<String> picTypeStrs;


    @Override
    public View initView() {
        mRoot = mContext.getLayoutInflater().inflate(R.layout.fragment_mycenter,null);
        NaviBar naviBar = (NaviBar)mRoot.findViewById(R.id.my_center_nav);
        naviBar.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiUtils.startActivity(getActivity(),SettingActivity.class,true);
            }
        });

        mRoot.findViewById(R.id.user_layout).setOnClickListener(this);
        nametv = (TextView)mRoot.findViewById(R.id.user_name);
        headv = (SimpleDraweeView)mRoot.findViewById(R.id.user_imv);

        /**
         * verify ID
         */
        View view = mRoot.findViewById(R.id.my_verify);
        TextView itemtv = (TextView)view.findViewById(R.id.info_key);
        itemtv.setText(R.string.my_verify);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiUtils.startActivity(getActivity(), UserRegisterActivity.class,true);
            }
        });
        /**
         * 就诊记录
         */
        view = mRoot.findViewById(R.id.my_medic_history);
        itemtv = (TextView)view.findViewById(R.id.info_key);
        itemtv.setText(R.string.my_medic_history);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiUtils.startActivity(getActivity(), MedicHistoryActivity.class,true);
            }
        });
        /**
         * 我的医生
         */
        view = mRoot.findViewById(R.id.my_doctors);
        itemtv = (TextView)view.findViewById(R.id.info_key);
        itemtv.setText(R.string.my_doctors);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiUtils.startActivity(getActivity(), DoctorHistoryActivity.class,true);
            }
        });
        /**
         * 我的病历
         */
        view = mRoot.findViewById(R.id.my_medic_record);
        itemtv = (TextView)view.findViewById(R.id.info_key);
        itemtv.setText(R.string.my_medic_records);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(medicTypeStrs==null)
                {
                    medicTypeStrs = new ArrayList<String>();
                    medicTypeStrs.add(getString(R.string.out_medic_record));
                    medicTypeStrs.add(getString(R.string.stay_medic_record));
                }
                if(null ==radioAdapter)
                    radioAdapter = new RadioDialog.RadioAdapter(getActivity());

                if (null == radioDialog) {
                    radioDialog = new RadioDialog(getActivity(),
                            new AppDialog.OnClickListener() {
                                @Override
                                public void onDialogClick(int nButtonId) {
                                    if(nButtonId == Dialog.BUTTON_NEGATIVE)
                                        radioDialog.dismiss();
                                }
                            }, radioAdapter);
                    radioDialog.setProperty(R.string.recode_choose);
                    radioDialog.setCancelable(true);
                }
                radioDialog.setRadioSelectListener(new RadioDialog.OnRadioSelectListener() {
                    @Override
                    public void onRadioItemClick(int which) {
                        radioAdapter.setPickIdx(-1);
                        Bundle bundle = new Bundle();
                        bundle.putInt(MedicRecordListActivity.MEDIC_RECORD_TYPE,
                                which ==0 ?
                                        MedicRecordListActivity.OUT_MEDIC_RECORD :
                                        MedicRecordListActivity.STAY_MEDIC_RECORD);
                        UiUtils.startActivity(getActivity(),MedicRecordListActivity.class,bundle,true);
                    }
                });
                radioAdapter.setList(medicTypeStrs, -1);
                radioAdapter.notifyDataSetChanged();

                radioDialog.show();
            }
        });

        /**
         * 检验报告
         */
        view = mRoot.findViewById(R.id.my_check_report);
        itemtv = (TextView)view.findViewById(R.id.info_key);
        itemtv.setText(R.string.my_check_report);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiUtils.startActivity(getActivity(),ReportListActivity.class,true);
            }
        });
        /**
         * 影像报告
         */
        view = mRoot.findViewById(R.id.my_pic_record);
        itemtv = (TextView)view.findViewById(R.id.info_key);
        itemtv.setText(R.string.my_pic_record);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(picTypeStrs==null)
                {
                    picTypeStrs = new ArrayList<String>();
                    picTypeStrs.add(getString(R.string.pic_record_ct));
                    picTypeStrs.add(getString(R.string.pic_record_x));
                    picTypeStrs.add(getString(R.string.pic_record_b));
                }
                if(null ==radioAdapter)
                    radioAdapter = new RadioDialog.RadioAdapter(getActivity());

                if (null == radioDialog) {
                    radioDialog = new RadioDialog(getActivity(),
                            new AppDialog.OnClickListener() {
                                @Override
                                public void onDialogClick(int nButtonId) {
                                    if(nButtonId == Dialog.BUTTON_NEGATIVE)
                                        radioDialog.dismiss();
                                }
                            }, radioAdapter);
                    radioDialog.setProperty(R.string.recode_choose);
                    radioDialog.setCancelable(true);
                }
                radioDialog.setRadioSelectListener(new RadioDialog.OnRadioSelectListener() {
                    @Override
                    public void onRadioItemClick(int which) {
                        radioAdapter.setPickIdx(-1);
                        UiUtils.startActivity(getActivity(),ReportListActivity.class,true);
                    }
                });
                radioAdapter.setList(picTypeStrs, -1);
                radioAdapter.notifyDataSetChanged();

                radioDialog.show();

            }
        });

        return mRoot;
    }

    @Override
    public void onResume()
    {
        getLoginUser();
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.user_layout:
                if(loginUser==null)
                    UiUtils.startActivity(getActivity(),LoginActivity.class,true);
                break;
            default:
                break;
        }
    }

    private void getLoginUser()
    {
        if(realm==null)
            realm = Realm.getDefaultInstance();
        loginUser = realm.where(LoginUser.class).findFirst();
        if(loginUser==null) {
            headv.setImageURI("res://" + getActivity().getPackageName() + "/" + R.mipmap.default_head);
            nametv.setText(R.string.register_login);
        }
        else {
            nametv.setText(loginUser.getName());
            headv.setImageURI(loginUser.getHeadimg());
        }
    }
}
