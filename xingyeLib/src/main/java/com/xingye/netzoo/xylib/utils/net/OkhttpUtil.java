package com.xingye.netzoo.xylib.utils.net;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xingye.netzoo.xylib.utils.MyApplication;

import java.io.IOException;
import java.io.Reader;
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

public class OkhttpUtil {
    /**
     * 本地缓存（延时CACHE_DELAY_NET_MILLISECONDS） 和网络竞速
     * 谁快谁返回，都失败返回最后一个失败.
     * 网络如果成功会被缓存anyway
     */
    public final static int CACHED_NETWORK_RACE = 2;
    public final static int ONLY_NETWORK = 0;  //只查询网络数据
    public final static int ONLY_CACHED  = 1;  //  只查询本地缓存

    private static final int CACHE_DELAY_NET_MILLISECONDS = 200;
    private static Gson   gson = new Gson();
    private static Handler handler = new Handler(Looper.myLooper());
    private static Interceptor REWRITE_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            //方案一：有网和没有网都是先读缓存
            Request request = chain.request();
            Response response = chain.proceed(request);
            String cacheControl = request.cacheControl().toString();
            if (TextUtils.isEmpty(cacheControl)) {
                cacheControl = "public, max-age=1";
            }
            //no-cache will cause nostore...don't know why
            if(cacheControl.contains("no-cache"))
                return response.newBuilder()
                        .header("Cache-Control", "public, max-age=0,max-stale=86400*7")
                        .removeHeader("Pragma")
                        .build();
            else
                return  response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .addHeader("Cache-Control", "public, max-age=0,max-stale=86400*7")
                    .removeHeader("Pragma")
                    .build();

        }};

    private static final OkHttpClient mOkHttpClient = new OkHttpClient.Builder().
            connectTimeout(15, TimeUnit.SECONDS).
            readTimeout(30, TimeUnit.SECONDS).
            writeTimeout(30, TimeUnit.SECONDS).
            cache(new Cache(MyApplication.getContext().getCacheDir(),1024*1024*10)).
            addNetworkInterceptor(REWRITE_INTERCEPTOR).
            addInterceptor(REWRITE_INTERCEPTOR).
            build();

    public static Call execute(Request request){
        return mOkHttpClient.newCall(request);

    }


    public static Call enqueue(Request request, Callback responseCallback){
        Call call;
        if(request.cacheControl().onlyIfCached())
            call = mOkHttpClient.newBuilder().connectTimeout(10,TimeUnit.MILLISECONDS)
            .readTimeout(100,TimeUnit.MILLISECONDS).build().newCall(request);
        else
            call = mOkHttpClient.newCall(request);
        call.enqueue(responseCallback);
        return call;
    }

    public static Call enqueue(Request request){
        return enqueue(request,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {/*do nothing*/}
            @Override
            public void onResponse(Call call, Response response){/*do nothing*/}
        });
    }


    public static void request(final Request.Builder builder, int cacheType,final Callback callback)
    {
       switch (cacheType) {
            case OkhttpUtil.ONLY_CACHED:
                enqueue(builder.cacheControl(CacheControl.FORCE_CACHE).build(),callback);
                break;
            case OkhttpUtil.CACHED_NETWORK_RACE:
                /**
                 * make sure only one response to callback (net/cache, whoever is faster)
                 */
                final Callback CacheNetRaceCallback =  new Callback() {
                    private boolean isResponsed = false;
                    private boolean isFailure = false;
                    @Override
                    public void onFailure(Call call, IOException e) {
                        synchronized (this) {
                            if (!isFailure) {
                                isFailure = true;
                            }else // isFailure = ture and again Fail
                                callback.onFailure(call, e);
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        synchronized (this) {
                            if(response.code() >=200 && response.code()<300)
                            {
                                if (!isResponsed) {
                                    isResponsed = true;
                                    callback.onResponse(call, response);
                                }
                            }
                            else
                                this.onFailure(call,new IOException(response.message()));
                        }
                    }
                };
                enqueue(builder.cacheControl(CacheControl.FORCE_NETWORK)
                        .build(),CacheNetRaceCallback);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        enqueue(builder.cacheControl(CacheControl.FORCE_CACHE)
                                .build(), CacheNetRaceCallback);
                    }
                },CACHE_DELAY_NET_MILLISECONDS);
                break;
            case OkhttpUtil.ONLY_NETWORK:
            default:
                enqueue(builder.cacheControl(CacheControl.FORCE_NETWORK).build(),callback);
                break;
        }
    }



    public  static void request(final Request.Builder builder, final int cacheType, final JsonCallback callback){
        request(builder,cacheType,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                if(callback!=null){
                    callback.onFailure(call,e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(callback!=null) {
                    JSONRespLogin base = gson.fromJson(response.body().string(), JSONRespLogin.class);
                    callback.onResponse(call, base);
                }
            }
        });
    }

}
