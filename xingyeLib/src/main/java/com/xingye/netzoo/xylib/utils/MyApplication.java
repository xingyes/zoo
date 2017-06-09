package com.xingye.netzoo.xylib.utils;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.xingye.netzoo.xylib.utils.net.RequestFactory;


public class MyApplication extends Application {
	private static final String LOG_TAG =  MyApplication.class.getName();
//	public static AppStorage mStorage = null;
	public static MyApplication app;
	public static boolean APP_RUNNING = false;
	public static int mVersionCode;
	public static String mVersionName;


	@Override
	public void onCreate() {
		super.onCreate();
		MyApplication.app = this;

		MyApplication.start();

		Fresco.initialize(this);

		//CrashReport.initCrashReport(this);
		//String userId = ILogin.getLoginUid() + ""; // 用户ID
		//CrashReport.setUserId(this, userId);
		
//		if (Config.DEBUG) // 正式发布时记得要关闭
//        {
//			CrashReport.setLogAble(true, false);
//        }
		
		//setExceptionStrategy();

		/*
		if (Config.DEBUG) // 正式发布时记得要关闭
        {
             Constants.IS_DEBUG = true; // 输出eup log
             Constants.IS_CORE_DEBUG = true; // 输出更详细的 eup log
             Constants.IS_USETESTSERVER = true; // 使用测试服务器，避免污染正式环境

             // 初始接入时SDK如果发现使用问题时，会抛出异常，SDK接入时建议开启，可以避免使用上的出错。
             Constants.Is_AutoCheckOpen = true;
        }
		
		String userId = ILogin.getLoginUid()+""; // 唯一标识一个用户
        //SDK上报时都是用UploadHandler中的doUpload方法，用户可以根据自己的需要实现自己的上报器进而监控或控制所有的SDK上报，
        //SDK的默认实现可以通过Analytics.getDefaultUpload(this)获取。
         UploadHandler hanlder = ExceptionUpload.getDefaultUpload(this);

         // 监听每次上报结果的MonitorUploadHandler
         // hanlder =createMonitorUploadHandler(this);

          // APP使用了Eup 或 Eup_Gray的jar包：
           //初始化1：
          ExceptionUpload eup = ExceptionUpload.getInstance(this, userId, true, hanlder); //默认不开启异常合并功能

           //初始化2：
          // ExceptionUpload eup = ExceptionUpload.getInstance(this, userId, isStartAfterQuery, hanlder,true); //开启异常合并功能

          // 配置异常上报，注意配置需要在开启前，否则可能无法及时生效
          setExceptionUpload();

          // 配置异常上报，注意配置需要在开启前，否则可能无法及时生效
          eup.setIsUseEup(true);
          */
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
//		DbFactory.closeDataBase();
	}

	public static void start() {
		if (APP_RUNNING == true) {
			return;
		}

		APP_RUNNING = true;

//		try {
//			UExceptionHandler UEHandler = new UExceptionHandler();
//			Thread.setDefaultUncaughtExceptionHandler(UEHandler);
//		} catch (SecurityException ex) {
//			android.util.Log.e(LOG_TAG, "onCreate|" + ex.getMessage());
//		}

		// Retrieve the version code.
		Context pContext = MyApplication.app.getApplicationContext();
		RequestFactory.setContext(pContext);

		MyApplication.getVersionInfo(pContext);


//		mStorage = new AppStorage(pContext);

	}
	
	public static void getVersionInfo(Context aContext) {
		if( (mVersionCode > 0) || (null == aContext) )
			return ;

		PackageInfo pInfo = null;
		try 
		{
			pInfo = aContext.getApplicationContext().getPackageManager().getPackageInfo(aContext.getApplicationContext().getPackageName(), 0);
		}
		catch (NameNotFoundException e) 
		{
			e.printStackTrace();
			pInfo = null;
		}
		mVersionCode = (null != pInfo ? pInfo.versionCode : 0);
		mVersionName = (null != pInfo ? pInfo.versionName : "");
	}


	public static MyApplication getContext() {
		return app;
	}

	public static void exit() {
		APP_RUNNING = false;

//		if( null != mStorage ) {
//			mStorage.save();
//			mStorage = null;
//		}

//		DbFactory.closeDataBase();

		//clear static stuff
//		UiUtils.clear();

	}
}
