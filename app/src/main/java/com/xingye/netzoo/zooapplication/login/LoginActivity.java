package com.xingye.netzoo.zooapplication.login;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xingye.netzoo.xylib.utils.net.JSONRespBase;
import com.xingye.netzoo.xylib.utils.net.JSONRespLogin;
import com.xingye.netzoo.xylib.utils.net.JsonCallback;
import com.xingye.netzoo.xylib.utils.net.OkhttpUtil;
import com.xingye.netzoo.xylib.utils.net.RequestFactory;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.databinding.ActivityLoginBinding;
import com.xingye.netzoo.zooapplication.utils.Config;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;


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
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setLoginVM(loginViewModel);
        initViews();



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

        findViewById(R.id.get_help).setOnClickListener(this);

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
            case R.id.get_help:
//                showHelpTips();
                break;
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
