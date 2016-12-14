/**
 * Copyright (C) 2013 Tencent Inc.
 * All rights reserved, for internal usage only.
 * 
 * Project: SosoNovel
 * FileName: AppDialog.java
 * 
 * Description: 
 * Author: lorenchen (lorenchen@tencent.com)
 * Created: Jul 11, 2012
 */
package com.xingye.netzoo.xylib.utils.ui;

import com.xingye.netzoo.xylib.R;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


public class AppDialog extends Dialog implements View.OnClickListener
{
	
	public interface OnClickListener
	{
		/**
		 * onDialogClick
		 * @param nButtonId
		 */
		void onDialogClick(int nButtonId);
	}
	
	/**
	 * Create a new instance of AppDialog
	 * @param aContext
	 */
	public AppDialog(Context aContext, OnClickListener aListener)
	{
		super(aContext, R.style.Dialog);
		mListener = aListener;
	}
	
	public AppDialog(Context aContext, OnClickListener aListener, int aTheme)
	{
		super(aContext, aTheme);
		mListener = aListener;
	}
	
	@Override
	public boolean onKeyDown (int keyCode, KeyEvent event) 
	{
		if((null != mListener) && (keyCode == KeyEvent.KEYCODE_BACK))
		{
			mListener.onDialogClick(BUTTON_NEGATIVE);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * setProperty
	 * @param strCaption
	 * @param strMessage
	 * @param nPostiveBtnTextId
	 */
	public void setProperty(String strCaption, String strMessage, int nPostiveBtnTextId)
	{
		setProperty(strCaption, strMessage, nPostiveBtnTextId, 0);
	}
	
	/**
	 * setProperty
	 * @param strCaption
	 * @param strMessage
	 * @param nPostiveBtnTextId
	 * @param nNegativeBtnTextId
	 */
	public void setProperty(String strCaption, String strMessage, int nPostiveBtnTextId, int nNegativeBtnTextId)
	{
		Context pContext = getContext();
		
		setProperty(strCaption, strMessage, pContext.getString(nPostiveBtnTextId), (nNegativeBtnTextId > 0 ? pContext.getString(nNegativeBtnTextId) : "") );
	}
	
	/**
	 * @param strCaption
	 * @param strMessage
	 * @param strPostiveBtnText
	 * @param strNegativeBtnText
	 */
	public void setProperty(String strCaption, String strMessage, 
			String strPostiveBtnText, String strNegativeBtnText,String strCheckboxText) {
		(mCaption = getComponent(mCaption)).mText = strCaption;
		(mMessage = getComponent(mMessage)).mText = strMessage;
		
		(mPositive = getComponent(mPositive)).mText = strPostiveBtnText;
		(mNegative = getComponent(mNegative)).mText = strNegativeBtnText;
		
		mCheckBoxText = strCheckboxText;
		
	}
	
	/**
	 * 
	 * @param strCaption
	 * @param strMessage
	 * @param strPostiveBtnText
	 * @param strNegativeBtnText
	 */
	public void setProperty(String strCaption, String strMessage, 
			String strPostiveBtnText, String strNegativeBtnText) {
		(mCaption = getComponent(mCaption)).mText = strCaption;
		(mMessage = getComponent(mMessage)).mText = strMessage;
		
		(mPositive = getComponent(mPositive)).mText = strPostiveBtnText;
		(mNegative = getComponent(mNegative)).mText = strNegativeBtnText;
		
	}
	
	/**
	 * setProperty
	 * @param nCaptionId
	 * @param nMessageId
	 * @param nPostiveBtnTextId
	 * @param nNegativeBtnTextId
	 */
	public void setProperty(int nCaptionId, int nMessageId, int nPostiveBtnTextId, int nNegativeBtnTextId)
	{
		Context pContext = getContext();
		String strCaption = pContext.getString(nCaptionId);
		String strMessage = pContext.getString(nMessageId);
		
		setProperty(strCaption, strMessage, nPostiveBtnTextId, nNegativeBtnTextId);
		strCaption = null;
		strMessage = null;
	}
	
	/**
	 * onCreate
	 */
	@Override
	protected void onCreate(Bundle aSavedInstanceState)
	{
		super.onCreate(aSavedInstanceState);
		
		// Load the default configuration.
		setContentView(R.layout.dialog_common);
		
		(mCaption = getComponent(mCaption)).mView = (TextView)findViewById(R.id.dialog_caption);
		(mMessage = getComponent(mMessage)).mView = (TextView)findViewById(R.id.dialog_message);
		
		(mPositive = getComponent(mPositive)).mView = (TextView)findViewById(R.id.dialog_btn_positive);
		mPositive.mView.setOnClickListener(this);
		
		(mNegative = getComponent(mNegative)).mView = (TextView)findViewById(R.id.dialog_btn_negative);
		mNegative.mView.setOnClickListener(this);
		
		mSeparator = findViewById(R.id.dialog_btn_separator);
		
		// Set UI configuration.
		final boolean bVisible = (null != mNegative.mText && mNegative.mText.length() > 0);
		final int nVisibility = bVisible ? View.VISIBLE : View.GONE;
		mNegative.mView.setVisibility(nVisibility);
		mSeparator.setVisibility(nVisibility);
		
//		if(!TextUtils.isEmpty(this.mCheckBoxText))
//		{
//			mCheckBoxView =  (CheckBox)findViewById(R.id.checkbox);
//			mCheckBoxView.setVisibility(View.VISIBLE);
//			mCheckBoxView.setTextContent(mCheckBoxText);
//			mCheckBoxView.setOnClickListener(this);
//		}
		// Update ui configuration.
		this.updateUi();
	}
	
	/**
	 * onStart
	 */
	@Override
	protected void onStart()
	{
		// Call the base implementation.
		super.onStart();
		
		TextView pMessage = (null != mMessage ? mMessage.mView : null);
		if ( null != pMessage )
		{
			// Measure the text length.
			TextPaint pPaint = pMessage.getPaint();
			final int nWidth = (null != mMessage.mText && mMessage.mText.length() > 0) ? (int)(pPaint.measureText(mMessage.mText)) : 0;
			pPaint = null;
			
			final int nMsgWidth = mWinWidth - (10 << 1); // 10 pixels in both sides.
			pMessage.setGravity(nMsgWidth > nWidth ? Gravity.CENTER_HORIZONTAL : Gravity.LEFT);
		}
	}
	
	
	@Override
	public void onClick(View aView)
	{
		if ( null != mListener )
		{
			final int nId = aView.getId();
			if(nId ==R.id.dialog_btn_positive)
				mListener.onDialogClick(BUTTON_POSITIVE);
			else if (nId == R.id.dialog_btn_negative)
					mListener.onDialogClick(BUTTON_NEGATIVE);
		}
		
		// Dismiss the dialog.
		if(this.isShowing())
			dismiss();
	}
	
	/**
	 * updateUi
	 * Update the UI configuration.
	 */
	protected void updateUi()
	{
		// Update the text.
		Component aComponents[] = {mCaption, mMessage, mPositive, mNegative};
		for ( int nIdx = 0; nIdx < aComponents.length; nIdx++ )
		{
			Component pComponent = aComponents[nIdx];
			pComponent.mView.setText(pComponent.mText);
		}

		mWinWidth = this.setAttributes();
		
		// Update the width for buttons.
		this.initButtons(mWinWidth);
	}
	
	/**
	 * setAttributes
	 */
	protected int setAttributes()
	{
		Window pWindow = getWindow();
		if ( null == pWindow )
			return 0;
		
		DisplayMetrics pMetrics = new DisplayMetrics();
		pWindow.getWindowManager().getDefaultDisplay().getMetrics(pMetrics);
		
		WindowManager.LayoutParams pParams = pWindow.getAttributes();
		pParams.gravity = Gravity.CENTER_HORIZONTAL;
		pParams.width = (int) (pMetrics.widthPixels * 0.9);
		pWindow.setAttributes(pParams);
		
		// Clean up.
		pMetrics = null;
		pWindow = null;
		
		return pParams.width;
	}
	
	/**
	 * initButtons
	 * @param nWidth
	 */
	protected void initButtons(int nWidth)
	{
		if ( 0 >= nWidth )
			return ;
		
		final int nButtonWidth = (nWidth * 2 / 5);
		(mPositive = getComponent(mPositive)).mView.setWidth(nButtonWidth);
		(mNegative = getComponent(mNegative)).mView.setWidth(nButtonWidth);
	}
	
	/**
	 * getComponent
	 * @param aComponent
	 * @return
	 */
	protected Component getComponent(Component aComponent)
	{
		return (null != aComponent ? aComponent : new Component());
	}
	
	/**
	 * Component
	 * @author lorenchen
	 */
	class Component
	{
		/**
		 * Default constructor of Component
		 */
		public Component()
		{
			mView = null;
			mText = null;
		}
		
		public void setColor(int nTextColor)
		{
			if ( null != mView )
			{
				mView.setTextColor(nTextColor);
			}
		}
		
		public TextView mView;
		public String   mText;
	}
	
//	public boolean isChecked()
//	{
//		if(null != mCheckBoxView)
//			return mCheckBoxView.isChecked();
//		return false;
//	}
	
	// Member instances.
	protected int            mWinWidth;
	protected View           mSeparator;
	protected Component      mCaption;
	protected Component      mMessage;
	protected Component      mPositive;
	protected Component      mNegative;
	protected String         mCheckBoxText;
//	protected CheckBox       mCheckBoxView;
	protected OnClickListener  mListener;
	
	// Constant members.
	public static final int BUTTON_POSITIVE = DialogInterface.BUTTON_POSITIVE;
	  
	  // Field descriptor #17 I
	public static final int BUTTON_NEGATIVE = DialogInterface.BUTTON_NEGATIVE;
	
	  // Field descriptor #17 I
	public static final int BUTTON_NEUTRAL = DialogInterface.BUTTON_NEUTRAL;
	
}
