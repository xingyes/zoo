package com.xingye.netzoo.zooapplication.mycenter;

import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xingye.netzoo.xylib.utils.ui.NaviBar;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.bookregister.RegisterDetailActivity;
import com.xingye.netzoo.zooapplication.login.LoginActivity;
import com.xingye.netzoo.zooapplication.login.LoginUser;
import com.xingye.netzoo.zooapplication.login.UserRegisterActivity;
import com.xingye.netzoo.zooapplication.main.BaseFragment;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class MyCenterFragment extends BaseFragment implements View.OnClickListener {

    private TextView         nametv;
    private SimpleDraweeView headv;

    private Realm     realm;
    private LoginUser loginUser;

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
                UiUtils.makeToast(getActivity(),R.string.my_doctors);
            }
        });
        /**
         * 我的病历
         */
        view = mRoot.findViewById(R.id.my_medic_record);
        itemtv = (TextView)view.findViewById(R.id.info_key);
        itemtv.setText(R.string.my_medic_record);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UiUtils.makeToast(getActivity(),R.string.my_medic_record);
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
                UiUtils.makeToast(getActivity(),R.string.my_check_report);
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
                UiUtils.makeToast(getActivity(),R.string.my_pic_record);
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
