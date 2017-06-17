//package com.xingye.netzoo.zooapplication.login;
//
//import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.xingye.netzoo.xylib.utils.net.JsonCallback;
//import com.xingye.netzoo.xylib.utils.net.OkhttpUtil;
//import com.xingye.netzoo.xylib.utils.net.RequestFactory;
//import com.xingye.netzoo.xylib.utils.ui.UiUtils;
//import com.xingye.netzoo.zooapplication.R;
//import com.xingye.netzoo.zooapplication.utils.Config;
//
//import java.io.IOException;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.FormBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//import retrofit2.Retrofit;
//import retrofit2.http.GET;
//import retrofit2.http.Path;
//
//
//public class LoginRDMActivity extends AppCompatActivity implements View.OnClickListener{
//
//    private TextView mTips;
//    private EditText mUser;
//    private EditText mPswd;
//    private EditText mVerifyCodeEdit;
//    private Button   mVerifyCodeBtn;
//    private Button   mLoginBtn;
//    private TextView mNetworkTips;
//
//    private int mTickCountdown = 0;
//    private static final int VERIFY_CODE_RESEND_TIME = 60;
//    private Handler  mHandler = new Handler();
//
//    private Object  mAct = null;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//        initViews();
//    }
//
//
//    @Override
//    protected void onResume()
//    {
//        super.onResume();
//    }
//
//    private void initViews() {
//
//        mTips = (TextView) findViewById(R.id.login_tips);
//        mUser = (EditText) findViewById(R.id.login_username);
//        mPswd = (EditText) findViewById(R.id.login_password);
//
//
//
//        mUser.addTextChangedListener(mTextWatcher);
//        mPswd.addTextChangedListener(mTextWatcher);
//        mVerifyCodeEdit = (EditText) findViewById(R.id.verify_code_edit);
//
//
//        mNetworkTips = (TextView) findViewById(R.id.login_networkTips);
//        mNetworkTips.setOnClickListener(this);
//        mVerifyCodeBtn = (Button) findViewById(R.id.verify_code_btn);
//        mVerifyCodeBtn.setOnClickListener(this);
//        mLoginBtn = (Button) findViewById(R.id.login_btn);
//        mLoginBtn.setOnClickListener(this);
//
//        findViewById(R.id.get_help).setOnClickListener(this);
//
////        if (null != mAct) {
////            verifyToken();
////        } else {
//            showLoginLayout();
////        }
//    }
//
//
//    private void showLoginLayout() {
//        mNetworkTips.setVisibility(View.GONE);
//        findViewById(R.id.login_layout).setVisibility(View.VISIBLE);
//        if (null != mAct) {
////            mUser.setText(mAct.getUserName());
//            UiUtils.showSoftInputDelayed(this, mPswd, new Handler());
//        } else {
//            mAct = new Object();
//            UiUtils.showSoftInputDelayed(this, mUser, new Handler());
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.get_help:
////                showHelpTips();
//                break;
////            case R.id.support_email:
////                Intent intent = new Intent(Intent.ACTION_SENDTO);
////                intent.setData(Uri.parse("mailto:" + getResources().getString(R.string.login_support_email)));
////                intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.login_email_subject));
////                startActivity(intent);
////                break;
//            case R.id.verify_code_btn:
//                sendSMS();
//                break;
//            case R.id.login_btn:
////                smsLogin();
//                break;
//            case R.id.login_networkTips:
////                if (null != mAct) {
////                    verifyToken();
////                }
//                break;
//            default:
//                break;
//        }
//    }
//
//
//    private void sendSMS()
//    {
//        String user = mUser.getText().toString();
//        String pswd = mPswd.getText().toString();
//
//        if (TextUtils.isEmpty(user)) {
//            showTips(getString(R.string.please_input_username));
//            return;
//        } else if(TextUtils.isEmpty(pswd)) {
//            showTips(getString(R.string.pswd_not_empty));
//            return;
//        }
//
//        if (mTickCountdown > 0 ) {
//            return;
//        }
//
//        switchVerifyCodeSendState(false);
//
//        RequestBody reqBody = new FormBody.Builder().add("username", user).add("password", pswd).build();
//        Request.Builder  rb = RequestFactory.getRequest(Config.URL_SENDSMS,reqBody,null,null);
//        if (null == rb) return;
//
//        try {
//            OkhttpUtil.request(rb, OkhttpUtil.CACHED_NETWORK_RACE,
//                    new JsonCallback(){
//
//
//                        @Override
//                        public void onFailure(Call call, Exception e) {
//                            Object px = Proxy.newProxyInstance(JsonCallback.class.getClassLoader(),
//                            new Class<?>[]{JsonCallback.class},
//                            new InvocationHandler() {
//                                @Override
//                                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                                    return null;
//                                }
//                            });
//                        }
//
//                        @Override
//                        public void onResponse(Call call, Object object) throws IOException {
//
//                        }
//                    });
//
//
////
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
////            JDJsonObjectRequest jsonObjectRequest = new JDJsonObjectRequest(
////                    ajax.getUrl(),
////                    new com.android.volley.Response.Listener<JSONObject>() {
////                        @Override
////                        public void onResponse(JSONObject v) {
////                            if (v != null) {
////                                onSENDSMSSuccess(v);
////                            } else {
////                                UiUtils.makeToast(thisActivity, R.string.network_error);
////                            }
////                        }
////                    }, new com.android.volley.Response.ErrorListener() {
////                @Override
////                public void onErrorResponse(VolleyError error) {
////                    UiUtils.makeToast(thisActivity, R.string.network_error);
////                }
////            },
////                    params
////            );
////            MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
////        }
//
//    }
//
//    private void verifyToken() {
//
//        Request.Builder reqbuilder = RequestFactory.getRequest(Config.URL_VERIFY,null,null,null);
//        if(null!=reqbuilder)
//            return;
//        mNetworkTips.setVisibility(View.GONE);
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        okHttpClient.newCall(reqbuilder.build()).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//            }
//        });
//
//
////            JDJsonObjectRequest jsonObjectRequest = new JDJsonObjectRequest(
////                    Request.Method.POST,
////                    mAjax.getUrl(),
////                    new com.android.volley.Response.Listener<JSONObject>() {
////                        @Override
////                        public void onResponse(JSONObject response) {
////                            if (response != null) {
////                                onVERIFYSuccess(response);
////                            } else {
////                                mNetworkError.run();
////                                UiUtils.makeToast(thisActivity, R.string.network_error);
////                            }
////                        }
////                    },
////                    new com.android.volley.Response.ErrorListener() {
////                        @Override
////                        public void onErrorResponse(VolleyError error) {
////                            mNetworkError.run();
////                            UiUtils.makeToast(thisActivity, R.string.network_error);
////                        }
////                    }
////            );
////            MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
////        }
//    }
//
//
//
//
//
//    private void switchVerifyCodeSendState(boolean flag) {
//        mVerifyCodeBtn.setBackgroundResource(flag ? R.drawable.button_red : R.drawable.button_gray);
//        mVerifyCodeBtn.setTextColor(flag ? getResources().getColorStateList(R.color.c_FFFFFF) :
//                getResources().getColorStateList(R.color.c_C7C7C7));
//    }
//
//    private void showTips(final String s) {
//        mHandler.post(new Runnable() {
//            @Override
//            public void run() {
//                mTips.setText(s);
//            }
//        });
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mTips.setText("");
//            }
//        }, 2000);
//    }
//
//
//    private android.text.TextWatcher mTextWatcher = new android.text.TextWatcher() {
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//
//        }
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
//
//        }
//        @Override
//        public void afterTextChanged(Editable editable) {
////            checkResendBtn();
//        }
//    };
//}
