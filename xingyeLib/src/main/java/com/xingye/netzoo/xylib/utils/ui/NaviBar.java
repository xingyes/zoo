package com.xingye.netzoo.xylib.utils.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xingye.netzoo.xylib.R;
import com.xingye.netzoo.xylib.utils.DPIUtil;


public class NaviBar extends RelativeLayout {

    private RelativeLayout rootContaniner;
    private ImageView titleRightImgPoint;
    private RelativeLayout leftContainer;
    private RelativeLayout rightContainer;
    private RelativeLayout middleContainer;
    private TextView leftTv;
    private TextView rightTv;
    private TextView titleTv;
    private ImageView titleImv;
    private ImageView rightImv;

    private boolean checkNeedRelayout = true;

    public NaviBar(Context context) {
        this(context, null);
    }

    public NaviBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NaviBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {

        LayoutInflater.from(context).inflate(R.layout.navibar_layout, this, true);
        rootContaniner = (RelativeLayout) findViewById(R.id.title_bar_root_view);
        leftContainer = (RelativeLayout) findViewById(R.id.title_bar_left_container);
        rightContainer = (RelativeLayout) findViewById(R.id.title_bar_right_container);
        middleContainer = (RelativeLayout) findViewById(R.id.title_bar_middle_container);
        leftTv = (TextView) findViewById(R.id.title_bar_left_tv);
        rightTv = (TextView) findViewById(R.id.title_bar_right_tv);
        titleTv = (TextView) findViewById(R.id.title_bar_title_tv);
        titleImv = (ImageView)findViewById(R.id.title_bar_title_imv);
        rightImv = (ImageView)findViewById(R.id.title_bar_right_imv);


        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.NaviBar);

        int color = a.getColor(R.styleable.NaviBar_bgcolor,context.getResources().getColor(R.color.c_7FCCCCCC));
        this.setBackgroundColor(color);
        int resid = a.getResourceId(R.styleable.NaviBar_titleTextImg,-1);
        if(resid>0)
        {
            titleImv.setImageResource(resid);
            titleImv.setVisibility(View.VISIBLE);
        }
        //主标题属性
        String title = a.getString(R.styleable.NaviBar_titleText);
        int titleTextColor = a.getColor(R.styleable.NaviBar_titleTextColor, Color.WHITE);
//        int titleTextSizePX = a.getDimensionPixelSize(R.styleable.TitleView_titleTextSize, getResources().getDimensionPixelSize(20));
//        int titleTextStyleID = a.getResourceId(R.styleable.TitleView_titleTextStyle, -1);
//        setTitleTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSizePX);
//        setTitleTextStyle(context, titleTextStyleID);
        setTitle(title);
        setTitleTextColor(titleTextColor);
        //左标题属性
        String leftText = a.getString(R.styleable.NaviBar_leftText);
        if (!TextUtils.isEmpty(leftText)) {
            setLeftText(leftText);//因为设置text时会去掉返回按钮箭头，如果没有使用到该自定义属性，不设置text
        }
        boolean leftVisible = a.getBoolean(R.styleable.NaviBar_leftVisible,true);
        if(!leftVisible)
            leftContainer.setVisibility(View.GONE);
        //右标题属性
        String rightTitle = a.getString(R.styleable.NaviBar_rightText);
//        int rightTextSize = a.getDimensionPixelSize(R.styleable.TitleView_rightTextSize, getResources().getDimensionPixelSize(R.dimen.text_size_14sp));
        int rightTextColor = a.getColor(R.styleable.NaviBar_rightTextColor, Color.WHITE);
        int rightTextStyleID = a.getResourceId(R.styleable.NaviBar_rightTextStyle, -1);
        int rightImgRid = a.getResourceId(R.styleable.NaviBar_rightImg, -1);

        a.recycle();
        setRightText(rightTitle);
        setRightResource(rightImgRid);
        setRightTextColor(rightTextColor);
        setRightTextStyle(context, rightTextStyleID);
//        setRightTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
    }


    public void setLeftOnClickListener(OnClickListener onClickListener) {
        if (leftContainer != null) {
            leftContainer.setOnClickListener(onClickListener);
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        int leftWidth = leftContainer.getWidth();
        int rightWidth = rightContainer.getWidth();
        if (checkNeedRelayout) {
            checkNeedRelayout = false;
            if (isMiddleCoverLeft() || isMiddleCoverRight()) {
                int middleMaxWidth = getWidth() - (Math.max(leftWidth, rightWidth) * 2);
                ViewGroup.LayoutParams params = middleContainer.getLayoutParams();
                params.width = middleMaxWidth;
                middleContainer.setLayoutParams(params);
            }
        }
    }

    public boolean isMiddleCoverLeft() {
        return leftContainer.getVisibility() == VISIBLE && leftContainer.getRight() > middleContainer.getLeft();
    }

    public boolean isMiddleCoverRight() {
        return rightContainer.getVisibility() == VISIBLE && middleContainer.getRight() > rightContainer.getLeft();
    }

    public void setRightOnClickListener(OnClickListener onClickListener) {
        if (rightContainer != null) {
            rightContainer.setOnClickListener(onClickListener);
        }
    }

    public void setRightOnTouchListener(OnTouchListener onTouchListener) {
        if (rightContainer != null) {
            rightContainer.setOnTouchListener(onTouchListener);
        }
    }


    public void setRightImgPointVisible(boolean visible) {
        if (titleRightImgPoint != null) {
            titleRightImgPoint.setVisibility(visible ? VISIBLE : GONE);
        }
    }


    /**
     * 设置左标题是否可见
     *
     * @param visible
     */
    public void setLeftVisible(boolean visible) {
        if (leftContainer != null) {
            leftContainer.setVisibility(visible ? VISIBLE : GONE);
        }
    }

    /**
     * 设置右标题是否可见
     *
     * @param visible
     */
    public void setRightVisible(boolean visible) {
        if (rightContainer != null) {
            rightContainer.setVisibility(visible ? VISIBLE : GONE);
        }
    }

    /**
     * 设置左按钮图案
     *
     * @param resid
     */
    public void setLeftResource(int resid) {
        if (leftTv != null) {
            leftTv.setVisibility(VISIBLE);
            leftTv.setBackgroundResource(resid);

        }
    }

    /**
     * 设置左标题字体大小
     * 单位sp
     *
     * @param size
     */
    public void setLeftTextSize(float size) {
        if (leftTv != null) {
            leftTv.setTextSize(size);
        }
    }

    /**
     * 设置左标题字体颜色
     *
     * @param color 颜色值
     */
    public void setLeftTextColor(int color) {
        if (leftTv != null) {
            leftTv.setTextColor(color);
        }
    }


    public void setLeftTextTypeface(Typeface textTypeface) {
        if (leftTv != null) {
            leftTv.setTypeface(textTypeface);
        }
    }


    /**
     * 设置左边标题，同时去除返回箭头
     *
     * @param charSequence
     */
    public void setLeftText(String charSequence) {
        if (leftTv != null) {
            leftTv.setVisibility(VISIBLE);
            leftTv.setText(charSequence);
        }
    }


    /**
     * 设置右标题字体颜色
     *
     * @param color 颜色值
     */
    public void setRightTextColor(int color) {
        if (rightTv != null) {
            rightTv.setTextColor(color);
        }
    }

    /**
     * 设置右标题style
     *
     * @param context
     * @param styleId
     */
    public void setRightTextStyle(Context context, int styleId) {
        if (rightTv != null) {
            rightTv.setTextAppearance(context, styleId);
        }
    }


    public void setRightResource(int resourceId) {
        if (rightImv != null && resourceId>=0) {
            rightImv.setBackgroundResource(resourceId);
        }
    }


    /**
     * 设置右标题字符
     *
     * @param charSequence
     */
    public void setRightText(String charSequence) {
        if (rightTv != null) {
            rightTv.setVisibility(VISIBLE);
            rightTv.setText(charSequence);
        }
    }


    public void setRightTextSize(int unit, float size) {
        if (rightTv != null) {
            rightTv.setTextSize(unit, size);
        }
    }

    /**
     * 设置主标题style
     *
     * @param context
     * @param styleID
     */
    public void setTitleTextStyle(Context context, int styleID) {
        if (titleTv != null) {
            titleTv.setTextAppearance(context, styleID);
        }
    }

    /**
     * 设置主标题字体大小
     * 单位 sp
     *
     * @param size 字体大小
     */
    public void setTitleTextSize(float size) {
        if (titleTv != null) {
            titleTv.setTextSize(size);
        }
    }

    /**
     * 设置主标题字体大小
     *
     * @param unit 字体单位
     * @param size 字体大小
     */
    public void setTitleTextSize(int unit, float size) {
        if (titleTv != null) {
            titleTv.setTextSize(unit, size);
        }
    }


    /**
     * 设置主标题点击事件
     *
     * @param onClickListener
     */
    public void setTitleOnClickListener(OnClickListener onClickListener) {
        if (middleContainer != null) {
            middleContainer.setOnClickListener(onClickListener);
        }
    }


    /**
     * 设置主标题字体颜色
     *
     * @param color 颜色值
     */
    public void setTitleTextColor(int color) {
        if (titleTv != null) {
            titleTv.setTextColor(color);
        }
    }

    /**
     * 设置主标题字符串
     *
     * @param charSequence 字符串
     */
    public void setTitle(String charSequence) {
        if (titleTv != null) {
            titleTv.setVisibility(VISIBLE);
            titleTv.setText(charSequence);
        }
    }

    /**
     * 设置主标题字符串
     *
     * @param resid 资源id
     */
    public void setTitle(int resid) {
        if (titleTv != null) {
            titleTv.setVisibility(VISIBLE);
            titleTv.setText(resid);
        }
    }

    /**
     * 设置主标题背景
     *
     * @param resid
     */
    public void setTitleBackground(int resid) {
        if (titleTv != null) {
            titleTv.setBackgroundResource(resid);
        }
    }


    /**
     * 返回主标题容器
     *
     * @return
     */
    public RelativeLayout getTitleContainer() {
        return middleContainer;
    }


    public RelativeLayout getLeftContainer() {
        return leftContainer;
    }

    public RelativeLayout getRightContainer() {
        return rightContainer;
    }


    /**
     * 设置自定义标题
     *
     * @param customTitle
     */
    public void setCustomTitle(View customTitle) {
        if (rootContaniner != null) {
            rootContaniner.removeAllViews();
            rootContaniner.addView(customTitle);
        }
    }
}


