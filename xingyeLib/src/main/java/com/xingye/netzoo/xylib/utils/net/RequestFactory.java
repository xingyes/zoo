package com.xingye.netzoo.xylib.utils.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.xingye.netzoo.xylib.R;
import com.xingye.netzoo.xylib.utils.MyApplication;

import okhttp3.CacheControl;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public final class RequestFactory {
	
	public static String getUrl(String strKey) {
		return RequestFactory.getUrl(strKey, null);
	}
	
	/**
	 * 通过传入的key(URL字符串常量）得到对应的url，并加上附加的信息
	 * @param strKey  传入的key(URL)常量
	 * @param aInfo	  附加的url信息
	 * @return	实际的需要的url
	 */
	public static String getUrl(String strKey, Object aInfo) {
		JSONObject pObject = (null != mSelf ? mSelf.getObject(strKey) : null);
		
		String strUrl = null;
		if( null != pObject ) {
			strUrl = pObject.optString(TAG_URL);
			
			if( null != aInfo ) {
				strUrl = strUrl + aInfo.toString();
			}
		}
		
		return strUrl;
	}
	
//	public static Request getRequest(String strKey) {
//		return RequestFactory.getRequest(strKey, null);
//	}
//
//	public static Request getRequest(String strKey, Object aInfo) {
//		return RequestConfig.getRequest(strKey, aInfo);
//	}

//	public static Request getRequest(String strKey, Object aInfo, Parser<?, ?> pParser) {
//		return RequestConfig.getRequest(strKey, aInfo, pParser, null);
//	}


    /**
	 *
	 * @param strKey
	 * @param reqBody
	 * @return
     */
    public static Request.Builder getRequest(String strKey, RequestBody reqBody,Headers headers,Object tag)
	{
		JSONObject pObject = null != mSelf ? mSelf.getObject(strKey) : null;
		if( null == pObject )
			return null;

		String strUrl = pObject.optString(TAG_URL);
		String strMethod = pObject.optString(TAG_METHOD);
		if( TextUtils.isEmpty(strUrl) || TextUtils.isEmpty(strMethod) )
			return null;

//        strUrl = "https://www.baidu.com";
//        strMethod = "GET";
		MyApplication.getVersionInfo(mSelf.mContext);


//		if (!TextUtils.isEmpty(strUrl) && !strUrl.contains("appVersion")) {
//			strUrl += (strUrl.contains("?") ? "&" : "?" );
//			strUrl += "appVersion=" + IVersion.getVersionName();
//			Account account = ILogin.getActiveAccount();
//			if (account != null) {
//				strUrl += "&username=" + account.getUserName();
//			}
////		}

		Request.Builder requestBuilder = new Request.Builder().url(strUrl);
		if(null!=headers)
			requestBuilder.headers(headers);
		if(null!=tag)
			requestBuilder.tag(headers);

		requestBuilder.addHeader("appVersion",MyApplication.mVersionName);
		if(strMethod.equalsIgnoreCase("get"))
			return requestBuilder.get();
		else
			return requestBuilder.post(reqBody);

	}

		/**
         * 通过key从mConfig中得到封装着url和method的jason对象
         * @param strKey 字符串常量key
         * @return 封装着url和method的jason对象
         */
	private JSONObject getObject(String strKey) {
		if( (null == mConfig) || (TextUtils.isEmpty(strKey)) ) {
			return null;
		} else {
			return mConfig.optJSONObject(strKey);
		}
	}



	private void parseInfo(JSONObject aObject) {
		if( null != aObject ) {
			mVersion = aObject.optInt(TAG_VERSION);
			mConfig = aObject.optJSONObject(TAG_DATA);

		}
	}
	

	/**
	 * Default constructor of ServerAdapter
	 */
	private RequestFactory(Context aContext) {
		mContext = aContext;
		loadConfig();
	}
	
	/**
	 * Set context for instance.
	 * @param aContext
	 */
	public static void setContext(Context aContext) {
		if( null == mSelf )
			mSelf = new RequestFactory(aContext);
		else
			mSelf.mContext = aContext;
	}
	

	/**
	 * Load configuration from storage, if not exists, set default.
	 */
	private void loadConfig()
	{
		if( null == mContext )
			return ;
		
		String strContent = null;
		// Check the timetag.
		File pFile = mContext.getFileStreamPath(CACHE_FILE);
		if( (null != pFile) && pFile.exists())
		{
			// Load the server configuration from local storage.
			FileInputStream pInputStream = null;
			try {
				pInputStream = mContext.openFileInput(CACHE_FILE);

				byte aBytes[] = new byte[pInputStream.available()];
				pInputStream.read(aBytes);
				strContent = new String(aBytes);
					
				// Parse the json object.
				JSONObject pRoot = new JSONObject(strContent);
				parseInfo(pRoot);
			} catch (FileNotFoundException aException) {
				aException.printStackTrace();
				strContent = null;
			} catch (IOException aException) {
				aException.printStackTrace();
				strContent = null;
			} catch (JSONException aException) {
				aException.printStackTrace();
			} finally {
				if( null != pInputStream ) {
					try {
							pInputStream.close();
						} catch (IOException aException) {
							aException.printStackTrace();
						}
						pInputStream = null;
				}
			}
		}

		
		// Check need to build up default values.
		if( TextUtils.isEmpty(strContent) ) {
			// Build default configuration.
			loadRawInfo();
		}
		

	}
	
	private boolean loadRawInfo() {
		if( null == mContext)
			return false;
		
		InputStream pInputStream  = null;
		boolean bSuccess = true;
		try {
			Resources pResources = mContext.getResources();
			pInputStream = pResources.openRawResource(R.raw.config);
			byte[] aBytes = new byte[pInputStream.available()];
			pInputStream.read(aBytes);
			
			// Compose JSON object info
			String strContect = new String(aBytes);
			JSONObject pObject = new JSONObject(strContect);
			
			// Parse information.
			parseInfo(pObject);
			
			// Save information.
			saveConfig();
		} catch (IOException e) {
			e.printStackTrace();
			bSuccess = false;
		} catch (JSONException aException) {
			aException.printStackTrace();
			bSuccess = false;
		}finally
		{
			if(null!= pInputStream)
			{
				try {
					pInputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
					bSuccess = false;
				}
				pInputStream = null;
			}
		}
		
		return bSuccess;
	}
	

	
	private JSONObject getChild(String strUrl, String strMethod) throws JSONException {
		JSONObject pChild = new JSONObject();
		pChild.put(TAG_URL, strUrl);
		pChild.put(TAG_METHOD, strMethod);
		
		return pChild;
	}
	
	/**
	 * Save server configuration to local storage.
	 */
	private boolean saveConfig() {
		if( (0 >= mVersion) || (null == mConfig) || (null == mContext) )
			return false;
		
		boolean bSuccess = false;
		FileOutputStream pOutputStream = null;
		try {
			// Compose root json object
			JSONObject pRoot = new JSONObject();
			pRoot.put(TAG_VERSION, mVersion);
			pRoot.put(TAG_DATA, mConfig);

			// Save the the output to local storage.
			pOutputStream = mContext.openFileOutput(CACHE_FILE, Context.MODE_PRIVATE);
			pOutputStream.write(pRoot.toString().getBytes());
			bSuccess = true;
		} catch( JSONException aException ) {
			aException.printStackTrace();
		} catch (FileNotFoundException aException) {
			aException.printStackTrace();
		} catch (IOException aException) {
			aException.printStackTrace();
		} finally {
			if( null != pOutputStream ) {
				try {
					pOutputStream.close();
				} catch (IOException aException) {
					aException.printStackTrace();
				}
				pOutputStream = null;
			}
		}
		

		return bSuccess;
	}
	

	// Member instance.
	private static RequestFactory mSelf = null;
	private Context    mContext;
	private int        mVersion; // Latest time tag.
	private JSONObject mConfig;


	// Constants definition in Server Adapter.
	private static final String METHOD_GET    = "get";
	private static final String METHOD_POST   = "post";
//	private static final String METHOD_WAP    = "wap";
//	private static final String METHOD_STREAM = "stream";
	private static final String TAG_DATA      = "data";
	private static final String TAG_DATA_BETA = "data_beta";
	private static final String TAG_VERSION   = "version";
	private static final String TAG_ALIAS     = "alias";
	private static final String TAG_URL       = "url";
	private static final String TAG_METHOD    = "method";
	private static final String TAG_MSG_ARRAY = "msg_arr";
	private static final String TAG_ERR_NO    = "errno";
	private static final String CACHE_FILE    = "jingdong.jdrdm_config.cache";
	
	// Default host configuration.
	public static final String BASE_HOST = "https://rdm.m.jd.com/";	//用fiddler调试时用这个http，不用https
//	public static final String BASE_HOST = "https://rdm.m.jd.com/";
//	public static final String BASE_HOST = "http://rdmbeta.m.jd.com/";

    public static boolean useVolley = true;
}
