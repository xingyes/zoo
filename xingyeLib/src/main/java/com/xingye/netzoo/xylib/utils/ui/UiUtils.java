package com.xingye.netzoo.xylib.utils.ui;

import java.util.HashMap;
import java.util.concurrent.Callable;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.xingye.netzoo.xylib.R;


public class UiUtils {
	
	
	private static Toast pToast; 
	public static void cancelToast()
	{
		if(null!=pToast)
			pToast.cancel();
	}

	/**
	 * @param context
	 * @param nResId
	 */
	public static void makeToast(Context context, int nResId) {
		String strText = context.getString(nResId);
		UiUtils.makeToast(context, strText,false);
	}
	
	/**
	 * @param context
	 * @param nResId
	 * @param longflag
	 */
	public static void makeToast(Context context, int nResId,boolean longflag) {
		String strText = context.getString(nResId);
		UiUtils.makeToast(context, strText,longflag);
	}
	/**
	 * @param context
	 * @param strText
	 */
	public static void makeToast(Context context, String strText) {
		if( null == context || TextUtils.isEmpty(strText) )
			return ;
		
		LayoutInflater pInflater = (LayoutInflater)context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View pLayout = pInflater.inflate(R.layout.toast_layout, null);
		if ( null != pLayout )
		{
			// Update the text.
			TextView pMessage = (TextView)pLayout.findViewById(R.id.toast_message);
			pMessage.setText(strText);
			
			// Show toast.
			UiUtils.showToast(context, pLayout,false);
		}
	}
	
	public static void makeToast(Context context, String strText,boolean longflag) {
		if( null == context || TextUtils.isEmpty(strText) )
			return ;
		
		LayoutInflater pInflater = (LayoutInflater)context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View pLayout = pInflater.inflate(R.layout.toast_layout, null);
		if ( null != pLayout )
		{
			// Update the text.
			TextView pMessage = (TextView)pLayout.findViewById(R.id.toast_message);
			pMessage.setText(strText);
			
			// Show toast.
			UiUtils.showToast(context, pLayout,longflag);
		}
	}
	
	
	/**
	 * show toast and save instance to global application instance
	 * @param aContext
	 * @param aLayout
	 */
	private static void showToast(Context aContext, View aLayout, boolean longflag) {
		
		if(longflag)
		{
			Toast longToast = new Toast(aContext);
			longToast.setGravity(Gravity.CENTER, 0, 0);
			longToast.setDuration(Toast.LENGTH_SHORT);
			if( null != aLayout ) {
				longToast.setView(aLayout);
			}
			
			longToast.show();
			return;
		}
		else
		{
			// Cancel previous instance.
			if(null == pToast)
			{
				pToast = new Toast(aContext.getApplicationContext());
				pToast.setGravity(Gravity.CENTER, 0, 0);
				pToast.setDuration(Toast.LENGTH_SHORT);
			}
		
			if( null != aLayout ) {
				pToast.setView(aLayout);
			}
				
			// Update the toast information.
			pToast.show();
		}
	}
	
	
	public static AppDialog showDialog(Context aContext, int nCaptionResId, String strMessage, int nPositiveResId) {
		String strCaption = aContext.getString(nCaptionResId);
		return UiUtils.showDialog(aContext, strCaption, strMessage, nPositiveResId);
	}
	
	public static AppDialog showDialog(Context aContext, int nCaptionResId, int nMessageResId, int nPositiveResId) {
		return UiUtils.showDialog(aContext, nCaptionResId, nMessageResId, nPositiveResId, 0, null);
	}
	
	public static AppDialog showDialog(Context aContext, String strCaption, String strMessage, int nPositiveResId, AppDialog.OnClickListener aListener) {
		return UiUtils.showDialog(aContext, strCaption, strMessage, nPositiveResId, 0, aListener);
	}
	
	public static AppDialog showDialog(Context aContext, int nCaptionResId, int nMessageResId, int nPositiveResId, AppDialog.OnClickListener aListener) {
		String strCaption = aContext.getString(nCaptionResId);
		String strMessage = aContext.getString(nMessageResId);
		return UiUtils.showDialog(aContext, strCaption, strMessage, nPositiveResId, 0, aListener);
	}
	
	public static AppDialog showDialog(Context aContext, int nCaptionResId, int nMessageResId, int nPositiveResId, int nNegativeResId, AppDialog.OnClickListener aListener) {
		String strCaption = aContext.getString(nCaptionResId);
		String strMessage = aContext.getString(nMessageResId);
		return UiUtils.showDialog(aContext, strCaption, strMessage, nPositiveResId, nNegativeResId, aListener);
	}
	
	public static AppDialog showDialog(Context aContext, String strCaption, String strMessage, String strPositive, String strNegative, AppDialog.OnClickListener aListener) {
		AppDialog pUiDialog = new AppDialog(aContext, aListener);
		
		pUiDialog.setProperty(strCaption, strMessage, strPositive, strNegative);
		
		// Show the new dialog.
		pUiDialog.show();
		
		return pUiDialog;
	}
	
	public static AppDialog showDialog(Context aContext, String strCaption, String strMessage, int nPositiveResId, int nNegativeResId, AppDialog.OnClickListener aListener) {
		return UiUtils.showDialog(aContext, strCaption, strMessage, aContext.getString(nPositiveResId), (nNegativeResId > 0 ? aContext.getString(nNegativeResId) : ""), aListener);
	}
	
	public static AppDialog showDialog(Context aContext, String strCaption, String strMessage, int nPositiveResId) {
		return UiUtils.showDialog(aContext, strCaption, strMessage, nPositiveResId, 0, null);
	}
	
	public static AppDialog showDialog(Context aContext, String strCaption, String strMessage, String strPositive) {
		return UiUtils.showDialog(aContext, strCaption, strMessage, strPositive, "", null);
	}
	public static AppDialog showDialogWithCheckbox(Context aContext, String strCaption, String strMessage, int nPositiveResId, int nNegativeResId, String strCheckMessage,AppDialog.OnClickListener aListener) {
		AppDialog pUiDialog = new AppDialog(aContext, aListener);
		
		pUiDialog.setProperty(strCaption, strMessage, aContext.getString(nPositiveResId), 
			aContext.getString(nNegativeResId),strCheckMessage);
		
		// Show the new dialog.
		pUiDialog.show();
		
		return pUiDialog;
	}
	/**
	 * @param context
	 * @param options
	 * @param listener
	 * @return
	 */
	public static RadioDialog showListDialog(Context context, String[] options, RadioDialog.OnRadioSelectListener listener) {
		return UiUtils.showListDialog(context, null, options, -1, listener, true);
	}
	
	/**
	 * @param context
	 * @param options
	 * @param checkedItem
	 * @param listener
	 * @return
	 */
	public static RadioDialog showListDialog(Context context, String[] options, int checkedItem, RadioDialog.OnRadioSelectListener listener) {
		return UiUtils.showListDialog(context, null, options, checkedItem, listener, true);
	}
	
	public static RadioDialog showListDialog(Context context, String strCaption, String[] options, int checkedItem, RadioDialog.OnRadioSelectListener listener) {
		return UiUtils.showListDialog(context, strCaption, options, checkedItem, listener, true);
	}
	
	/**
	 * @param context
	 * @param options
	 * @param listener
	 * @param cancelable
	 * @return
	 */
	public static RadioDialog showListDialog(Context context, String[] options, RadioDialog.OnRadioSelectListener listener, boolean cancelable) {
		return UiUtils.showListDialog(context, null, options, -1, listener, true);
	}
	
	/**
	 * @param context
	 * @param options
	 * @param checkedItem
	 * @param listener
	 * @param cancelable
	 * @return
	 */
	public static RadioDialog showListDialog(Context context, String strCaption, String[] options, int checkedItem, RadioDialog.OnRadioSelectListener listener, boolean cancelable) {
		if( null == context || null == options || 0 >= options.length )
			return null;
		
		RadioDialog pDialog = new RadioDialog(context, null, null);
		pDialog.setCanceledOnTouchOutside(true);
		pDialog.setCancelable(cancelable);
		pDialog.setProperty(strCaption);
		
		pDialog.setRadioSelectListener(listener);
		pDialog.setList(options, checkedItem);
		
		pDialog.show();
		
		return pDialog;
	}
	
	public static RadioDialog showListDialog(Context aContext, String strCaption, RadioDialog.RadioAdapter aAdapter, RadioDialog.OnRadioSelectListener aListener) {
		if( null == aContext || null == aAdapter )
			return null;
		
		RadioDialog pDialog = new RadioDialog(aContext, null, aAdapter);
		pDialog.setCanceledOnTouchOutside(true);
		pDialog.setProperty(strCaption);
		
		pDialog.setRadioSelectListener(aListener);
		
		pDialog.show();
		
		return pDialog;
	}
	
	
	/**
	 * ??�示�???��??�????�???�主线�??�????
	 * @param aContext
	 * @param aEditText
	 */
	public static void showSoftInput(Context aContext, EditText aEditText) {
		if( null != aContext && null != aEditText ) {
			InputMethodManager pManager = (InputMethodManager)aContext.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);  
			aEditText.requestFocus(); 
			pManager.showSoftInput(aEditText, InputMethodManager.SHOW_IMPLICIT);
		}
	}
	
	public static void showSoftInputDelayed(final Context context, final EditText editText, Handler uiHandler) {
		
		if(uiHandler == null) {
			return;
		}
		
		uiHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
    			UiUtils.showSoftInput(context, editText);
			}
		}, 300);  // 延�??300ms
	}
	
	/**
	 * @param aContext
	 * @param aEditText
	 */
	public static void hideSoftInput(Context aContext, EditText aEditText) {
		if( null != aContext && null != aEditText ) {
			InputMethodManager pManager = (InputMethodManager) aContext.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE); 
			pManager.hideSoftInputFromWindow(aEditText.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	/**
	 * @param aContext
	 * @param aEditText
	 */
	public static void hideSoftInputDelayed(final Context aContext, final EditText aEditText,Handler uiHandler) {
		if(uiHandler == null) {
			return;
		}
		uiHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
    			UiUtils.hideSoftInput(aContext, aEditText);
			}
		}, 300);  // 延�??300ms
	}
	
	/**
	 * @param aParent
	 * @param aTarget
	 * @param nRequestFlag
	 * @param bClearTop
	 * @return
	 */
	public static boolean startActivity(Activity aParent, Class<?> aTarget, int nRequestFlag, boolean bClearTop) {
		return UiUtils.startActivity(aParent, aTarget, null, nRequestFlag, bClearTop);
	}
	
	/**
	 * @param aParent
	 * @param aTarget
	 * @param bClearTop
	 * @return
	 */
	public static boolean startActivity(Activity aParent, Class<?> aTarget, boolean bClearTop) {
		return UiUtils.startActivity(aParent, aTarget, null, -1, bClearTop);
	}
	
	/**
	 * @param aParent
	 * @param aTarget
	 * @param aBundle
	 * @param bClearTop
	 * @return
	 */
	public static boolean startActivity(Activity aParent, Class<?> aTarget, Bundle aBundle, boolean bClearTop) {
		return UiUtils.startActivity(aParent, aTarget, aBundle, -1, bClearTop);
	}

	/**
	 * @param aParent
	 * @param aTarget
	 * @param aBundle
	 * @param nRequestFlag
	 * @return
	 */
	public static boolean startActivity(Activity aParent, Class<?> aTarget, Bundle aBundle, int nRequestFlag) {
		return UiUtils.startActivity(aParent, aTarget, aBundle, nRequestFlag, false);
	}
	
	/**
	 * @param aParent
	 * @param aTarget
	 * @param aBundle
	 * @param nRequestFlag
	 * @param bClearTop
	 * @return
	 */
	public static boolean startActivity(Activity aParent, Class<?> aTarget, Bundle aBundle, int nRequestFlag, boolean bClearTop) {
		if( null == aParent || null == aTarget )
			return false;
		
		Intent pIntent = new Intent(aParent, aTarget);
		if( null != aBundle ) {
			pIntent.putExtras(aBundle);
		}
		
		if( bClearTop ) {
			pIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		}

		// Start the new instance of activity.
		if( nRequestFlag > 0 ) {
			aParent.startActivityForResult(pIntent, nRequestFlag);
		} else {
			aParent.startActivity(pIntent);
		}
		
		return true;
	}
	
	
	/**
	 * @param context
	 * @param array
	 * @param index
	 * @return
	 */
	public static int getInteger(Context context, TypedArray array, int index) {
		return UiUtils.getInteger(context, array, index, -1);
	}
	
	/**
	 * 
	 * @param context
	 * @param array
	 * @param index
	 * @return
	 */
	public static int getInteger(Context context, TypedArray array, int index, int defaultVal) {
		if( null == context || null == array || 0 > index )
			return defaultVal;
		
		if( !array.hasValue(index) )
			return defaultVal;
		
		return array.getInteger(index, defaultVal);
	}
	
	
	public static float getFloat(Context context, TypedArray array, int index) {
		return UiUtils.getFloat(context, array, index, -1);
	}
	/**
	 * 
	 * @param context
	 * @param array
	 * @param index
	 * @return
	 */
	public static float getFloat(Context context, TypedArray array, int index, float defaultVal) {
		if( null == context || null == array || 0 > index )
			return defaultVal;
		
		if( !array.hasValue(index) )
			return defaultVal;
		
		return array.getFloat(index, defaultVal);
	}
	
	/**
	 * @param context
	 * @param array
	 * @param index
	 * @return
	 */
	public static int getColor(Context context, TypedArray array, int index) {
		if( null == context || null == array || 0 > index )
			return 0;
		
		if( !array.hasValue(index) )
			return 0;
		
		return array.getColor(index, 0);
	}
	
	/**
	 * @param context
	 * @param array
	 * @param index
	 * @return
	 */
	public static float getDimension(Context context, TypedArray array, int index) {
		if( null == context || null == array || 0 > index )
			return 0;
		
		if( !array.hasValue(index) )
			return 0;
		
		return array.getDimension(index, 0);
	}
	
	/**
	 * @param context
	 * @param array
	 * @param index
	 * @return
	 */
	public static String getString(Context context, TypedArray array, int index) {
		final int nResId = UiUtils.getResId(context, array, index);
		return (nResId > 0 ? context.getString(nResId) : array.getString(index));
	}
	
	/**
	 * @param context
	 * @param array
	 * @param index
	 * @return
	 */
	public static boolean getBoolean(Context context, TypedArray array, int index) {
		if( null == context || null == array || 0 > index )
			return true;
		
		if( !array.hasValue(index) )
			return true;
		
		return array.getBoolean(index, true);
	}
	
	/**
	 * @param context
	 * @param array
	 * @param index
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(Context context, TypedArray array, int index, boolean defaultValue) {
		if( null == context || null == array || 0 > index )
			return defaultValue;
		
		if( !array.hasValue(index) )
			return defaultValue;
		
		return array.getBoolean(index, defaultValue);
	}
	
	/**
	 * @param context
	 * @param array
	 * @param index
	 * @return
	 */
	public static int getResId(Context context, TypedArray array, int index) {
		return UiUtils.getResId(context, array, index, 0);
	}
	
	/**
	 * @param context
	 * @param array
	 * @param index
	 * @return
	 */
	public static int getResId(Context context, TypedArray array, int index, int defaultVal) {
		if( null == context || null == array || 0 > index )
			return defaultVal;
		
		if( !array.hasValue(index) )
			return defaultVal;
		
		return array.getResourceId(index, defaultVal);
	}
	
	/**
	 * updateImage
	 * @param aBitmap
	 * @param strUrl
	 * @param aHashMap
	 */
	protected static void updateImage(Bitmap aBitmap, String strUrl, HashMap<String, ImageView> aHashMap) {
		if( (null != aBitmap) && (null != aHashMap) ) {
			ImageView pImageView = aHashMap.get(strUrl);
			if( null != pImageView ) {
				pImageView.setImageBitmap(aBitmap);
			}
		}
	}
	
	/**
	 * @param aImage
	 * @param strPicUrl
	 * @param aLoader
	 * @param aHashMap
	 * @param aListener
	 */
//	protected static void loadImage(ImageView aImage, String strPicUrl, ImageLoader aLoader, HashMap<String, ImageView> aHashMap, ImageLoadListener aListener) {
//		UiUtils.loadImage(aImage, strPicUrl, aLoader, aHashMap, aListener, R.drawable.i_global_image_none);
//	}
//
//	/**
//	 * @param aImage
//	 * @param strPicUrl
//	 * @param aLoader
//	 * @param aHashMap
//	 * @param aListener
//	 */
//	protected static void loadImage(ImageView aImage, String strPicUrl, ImageLoader aLoader, HashMap<String, ImageView> aHashMap, ImageLoadListener aListener, int nDefaultImageId) {
//		if( null == aImage )
//			return ;
//
//		if( 0 >= nDefaultImageId )
//			nDefaultImageId = R.drawable.i_global_image_none;
//
//		// Update bitmap.
//		if( (TextUtils.isEmpty(strPicUrl)) || (null == aLoader) ) {
//			aImage.setImageBitmap(ImageHelper.getResBitmap(MyApplication.app, nDefaultImageId));
//		} else {
//			final int nLocalId = UiUtils.getLocalImageId(strPicUrl);
//			if( nLocalId > 0 ) {
//				aImage.setImageResource(nLocalId);
//			} else {
//				Bitmap pBitmap = aLoader.get(strPicUrl, aListener);
//				if( null != pBitmap ) {
//					aImage.setImageBitmap(pBitmap);
//				} else {
//					aImage.setImageBitmap(ImageHelper.getResBitmap(MyApplication.app, nDefaultImageId));
//					aHashMap.put(strPicUrl, aImage);
//				}
//			}
//		}
//	}
	
	/**
	 * @param strPicUrl
	 * @return
	 */
	static int getLocalImageId(String strPicUrl) {
		if( TextUtils.isEmpty(strPicUrl) )
			return 0;

		int nLocalId = 0;
		/*
		final String strLocal = "local://";
		if( strPicUrl.startsWith(strLocal) ) {
			String strTag = strPicUrl.substring(strLocal.length());
			if( !TextUtils.isEmpty(strTag) ) {
				if( strTag.equals("icon_sun") ) {
					nLocalId = R.drawable.icon_sun;
				} else if( strTag.equals("icon_moon") ) {
					nLocalId = R.drawable.icon_moon;
				
			}
		}
		*/
		return nLocalId;
	}
	
	/**
	 * 
	 */
	public static void clear()
	{
		if(null!=pToast)
			pToast.cancel();
		pToast = null;
	}
	
	private UiUtils() {
		
	}
	
	
	public static void showSmallToast(Context context,String text, int length) {

		Toast toast = Toast.makeText(context, text, length);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

//	public static PopupWindow popupWindowBottom(Context context, View view, View content) {
//		LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View window = layoutInflater.inflate(R.layout.popup_window_bottom, null);
//		final PopupWindow popupWindow = new PopupWindow(window,
//				ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//		window.findViewById(R.id.popup_window_bottom_close).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				popupWindow.dismiss();
//			}
//		});
//		((LinearLayout) window.findViewById(R.id.popup_window_bottom_content)).addView(content);
//		popupWindow.setFocusable(true);
//		popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//		popupWindow.setAnimationStyle(R.style.DialogAnimation);
//		popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
//		return popupWindow;
//	}


	public static void setControllerListener(final SimpleDraweeView simpleDraweeView, String imagePath,
											 final int imageWidth) {
		final ViewGroup.LayoutParams layoutParams = simpleDraweeView.getLayoutParams();
		ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
			@Override
			public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
				if (imageInfo == null) {
					return;
				}
				int height = imageInfo.getHeight();
				int width = imageInfo.getWidth();
				layoutParams.width = imageWidth;
				layoutParams.height = (int) ((float) (imageWidth * height) / (float) width);
				simpleDraweeView.setLayoutParams(layoutParams);
			}

			@Override
			public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {
			}

			@Override
			public void onFailure(String id, Throwable throwable) {
				throwable.printStackTrace();
			}
		};
		DraweeController controller = Fresco.newDraweeControllerBuilder().setControllerListener(controllerListener).
				setUri(Uri.parse(imagePath)).build();
		simpleDraweeView.setController(controller);
	}

	public void abc(final Context context,final String imgurl)
	{
		ImageRequest imageRequest = ImageRequestBuilder
				.newBuilderWithSource( Uri.parse(imgurl))
				.setProgressiveRenderingEnabled(true)
				.build();

		ImagePipeline imagePipeline = Fresco.getImagePipeline();
		DataSource<CloseableReference<CloseableImage>>
				dataSource = imagePipeline.fetchDecodedImage(imageRequest,context.getApplicationContext());

		dataSource.subscribe(new BaseBitmapDataSubscriber() {
				@Override
				public void onNewResultImpl(@Nullable Bitmap bitmap) {
				}
				 @Override
				public void onFailureImpl(DataSource dataSource) {
									 // No cleanup required here.
								 }
							 },
				CallerThreadExecutor.getInstance());
	}



	public static void fetchBitmap(final Uri uri, final  FrescoBitmapCallback<Bitmap> callback) throws Exception {
		ImageRequestBuilder requestBuilder = ImageRequestBuilder.newBuilderWithSource(uri);
		ImageRequest imageRequest = requestBuilder.build();
		DataSource<CloseableReference<CloseableImage>> dataSource = ImagePipelineFactory.getInstance()
				.getImagePipeline()
				.fetchDecodedImage(imageRequest, null);

		dataSource.subscribe(new BaseBitmapDataSubscriber() {
								 @Override
								 public void onNewResultImpl(@Nullable final Bitmap bitmap) {
									 if (callback == null)
										 return;
									 if (bitmap != null && !bitmap.isRecycled()) {
										 callback.onSuccess(uri, bitmap);
									 }
								 }
//					handlerBackgroundTask(new Callable<Bitmap>() {
//											 @Override
//											 public Bitmap call() throws Exception {
//												 final Bitmap resultBitmap = bitmap.copy(bitmap.getConfig(), bitmap.isMutable());
//												 if (resultBitmap != null && !resultBitmap.isRecycled())
//													 postResult(resultBitmap, uri, callback);
//												 return resultBitmap;
//											 }
//										 });
//									 }
//								 }

								 @Override
								 public void onCancellation(DataSource<CloseableReference<CloseableImage>> dataSource) {
									 super.onCancellation(dataSource);
									 if (callback == null)
										 return;
									 callback.onCancel(uri);
								 }

								 @Override
								 public void onFailureImpl(DataSource dataSource) {
									 if (callback == null)
										 return;
									 Throwable throwable = null;
									 if (dataSource != null) {
										 throwable = dataSource.getFailureCause();
									 }
									 callback.onFailure(uri, throwable);
								 }
							 },
				UiThreadImmediateExecutorService.getInstance());
	}


	public interface FrescoBitmapCallback<T> {

		void onSuccess(Uri uri, T result);

		void onFailure(Uri uri, Throwable throwable);

		void onCancel(Uri uri);
	}
}
