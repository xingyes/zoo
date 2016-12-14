package com.xingye.netzoo.xylib.utils.net;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xingye.netzoo.xylib.utils.MyApplication;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yx on 16/11/24.
 */

public abstract class JsonCallback<T> {

        public abstract void onFailure(Call call, Exception e);
        public abstract void onResponse(Call call,T object) throws IOException;

        Type getType(){
            Type type = ((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
            if(type instanceof Class){
                return type;
            }else{
                return new TypeToken<T>(){}.getType();
            }
        }
    }

