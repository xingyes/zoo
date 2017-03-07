package com.xingye.netzoo.zooapplication.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.TextView;

import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.databinding.ActivityLoginBinding;
import com.xingye.netzoo.zooapplication.utils.AddFuncUtils;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mNetworkTips;


    private Handler  mHandler = new Handler();
    private Runnable countDownRunnable;
    private Object  mAct = null;
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new LoginViewModel(this);
        LoginVMComponent loginVMComponent = new LoginVMComponent();
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login,loginVMComponent);
        binding.setLoginVM(loginViewModel);
        initViews();


        int abc = AddFuncUtils.add(1,3);
        abc++;

    }


    @Override
    protected void onResume()
    {
        super.onResume();
    }

    private void initViews() {
        binding.loginPassword.addTextChangedListener(mTextWatcher);
        binding.loginUsername.addTextChangedListener(mTextWatcher);

        mNetworkTips = (TextView) findViewById(R.id.login_networkTips);
        mNetworkTips.setOnClickListener(this);

//        if (null != mAct) {
//            verifyToken();
//        } else {
            showLoginLayout();
//        }
    }


    private void showLoginLayout() {
        mNetworkTips.setVisibility(View.GONE);
        findViewById(R.id.login_layout).setVisibility(View.VISIBLE);
        if (null != mAct) {
//            mUser.setText(mAct.getUserName());
            UiUtils.showSoftInputDelayed(this, binding.loginPassword, new Handler());
        } else {
            mAct = new Object();
            UiUtils.showSoftInputDelayed(this, binding.loginUsername, new Handler());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.get_help:
////                showHelpTips();
//                break;
//            case R.id.support_email:
//                Intent intent = new Intent(Intent.ACTION_SENDTO);
//                intent.setData(Uri.parse("mailto:" + getResources().getString(R.string.login_support_email)));
//                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.login_email_subject));
//                startActivity(intent);
//                break;
            case R.id.login_networkTips:
//                if (null != mAct) {
//                    verifyToken();
//                }
                break;
            default:
                break;
        }
    }

//    private void showTips(final String s) {
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
////                mTips.setText(s);
//            }
//        });
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                mTips.setText("");
//            }
//        }, 2000);
//    }


    private android.text.TextWatcher mTextWatcher = new android.text.TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }
        @Override
        public void afterTextChanged(Editable editable) {
//            checkResendBtn();
        }
    };
}
