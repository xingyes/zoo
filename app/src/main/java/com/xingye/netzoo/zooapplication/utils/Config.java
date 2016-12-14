package com.xingye.netzoo.zooapplication.utils;


public class Config {
	
	public static final boolean DEBUG = false;
	public static final boolean isCustomerTestVersion = false;
	public static final String COMPILE_TIME = "04/15/2014";
	public static final String APP_ID = "wxb406849bf1dd54fc";

	public static final String NET_RROR = "悲剧出错了";

	public static final int NOT_LOGIN = 500;

	public static final String SLEF_BROADCAST_PERMISSION = "com.jingdong.jdrdm.permission.self_broadcast";
	
	public static final int MAX_ASYNC_IMAGE_NUM = 20;

	public static final int MAX_SDRAM_PIC_NUM = 30;

	public static final int INNER_DATABASE_VERSION = 2;

	public static final String INNER_DATABASE_NAME = "jingdong.jdrdm.db";

	public static final int SD_DATABASE_VERSION = 8;

	public static final String SD_DATABASE_NAME = "jingdong.jdrdm.db";

	public static final String SD_DATABASE_VERSION_NAME = "sdcard_database_version";
 
	public static final int GET_DATA_TIME_OUT = 20 * 1000;

	public static final int POST_DATA_TIME_OUT = 5 * 1000;

	public static final int CONNECT_TIME_OUT = 5 * 1000;

	public static final int CHANNEL_CACHE_TIME = 5 * 60;
	
	public static final String TMPDIRNAME = "jingdong.jdrdm";
	
	public static final String BROADCAST_FRIENDREQ = "com.jingdong.jdrdm.new_req_friend";
	public static final String BROADCAST_EMPTY_MY = "com.jingdong.jdrdm.empty_myinfo";
	
	public static final String LOG_NAME = "fatal_error.log";

	// Folder name for local image cache
	public static final String PIC_CACHE_DIR    = "pic_cache";
	public static final String APK_DOWNLOAD_DIR = "apk_downloaded";
	public static final String EVET_PIC_DIR  = "event_pic";
	
	
	public static final String CHANNEL_PIC_DIR  = "channel_pic";
	
	public static final int PROINFO_WIDTH = 680;
	public static final int PROINFO_HEIGHT = 290;

	public static final long PIC_CACHE_DIR_TIME = 5 * 24 * 3600 * 1000;

	public static final long MIN_SD_SIZE_SPARE = 5;
	
	// Max cache for gallery count.
	public static final int MAX_GALLERY_CACHE = 8;
	
	// Extra data key definition.
	public static final String EXTRA_BARCODE = "barcode";
	public static final String EXTRA_PUSHMSG = "pushmsg";
	public static final String EXTRA_WEIXIN = "weixin_url";
	//public static final String EXTRA_ALI_USERID = "alipay_user_id";

	////////// Server api configuration.
	public static final String URL_SEND_EMAIL		= "URL_SEND_EMAIL";
	public static final String URL_LOGIN			= "URL_LOGIN";
	public static final String URL_SMSLOGIN			= "URL_SMSLOGIN";
	public static final String URL_VERIFY			= "URL_VERIFY";
	public static final String URL_CHECK_VERSION	= "URL_CHECK_VERSION";
	public static final String URL_PUSHNOTIFY_GET	= "URL_PUSHNOTIFY_GET";
	public static final String URL_APK_DETAIL		= "URL_APK_DETAIL";
	public static final String URL_FEEDBACK			= "URL_FEEDBACK";
	public static final String URL_FEEDBACK_LIST	= "URL_FEEDBACK_LIST";
	public static final String URL_PRO_LIST			= "URL_PRO_LIST";
	public static final String URL_PRODUCT_HISTROY	= "URL_PRODUCT_HISTROY";
	public static final String URL_PRODUCT_DOWNLOAD	= "URL_PRODUCT_DOWNLOAD";
	public static final String URL_SENDSMS			= "URL_SENDSMS";
	public static final String URL_DELCOMMENT		= "URL_DELCOMMENT";


	//99max
	public static final int    MAXNUM_PER_ORDER = 99;
	
		
}
