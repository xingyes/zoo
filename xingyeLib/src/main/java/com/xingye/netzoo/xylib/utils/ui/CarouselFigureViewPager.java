package com.xingye.netzoo.xylib.utils.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import com.xingye.netzoo.xylib.R;
import com.xingye.netzoo.xylib.utils.DPIUtil;


public class CarouselFigureViewPager extends ViewPager {

    protected boolean isCarousel = false;
    private int realHeight;
    public CarouselFigureViewPager(Context context) {
        super(context);
        init();
    }

    public CarouselFigureViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.CarouselFigureViewPager);
        if(typedArray!=null)
        {
            float layoutHeightBy750Design = typedArray.getDimension(R.styleable.CarouselFigureViewPager_hdpi_len,0);
            realHeight = DPIUtil.getWidthByDesignValue720(layoutHeightBy750Design);
            isCarousel = typedArray.getBoolean(R.styleable.CarouselFigureViewPager_isCarousel,false);
            typedArray.recycle();
        }

        init();
    }


    public void init()
    {
        super.setOnPageChangeListener(onPageChangeListener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int childrenHeightMeasureSpec = heightMeasureSpec;
        int specMode = View.MeasureSpec.getMode(heightMeasureSpec);
        if(specMode != MeasureSpec.EXACTLY && realHeight!=0)
            childrenHeightMeasureSpec = MeasureSpec.makeMeasureSpec(realHeight,MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec,childrenHeightMeasureSpec);
    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        // 只有循环需要 设置 0 1，到1
        if (adapter != null && isCarousel && adapter.getCount() > 1) {
            setCurrentItem(1, false);
        }
    }


    private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
        private float mPreviousOffset = -1;
        private float mPreviousPosition = -1;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (isCarousel && null != getAdapter() && getAdapter().getCount() > 3) {
                final int nextPosition = getEdgeNextPosition(position);
                if (positionOffset == 0 && mPreviousOffset == 0 &&
                        (position == 0 || position == getAdapter().getCount() - 1)) {
                    post(new Runnable() {
                        @Override
                        public void run() {
                            setCurrentItem(nextPosition, false);
                        }
                    });
                }
            }
            mPreviousOffset = positionOffset;

        }

        @Override
        public void onPageSelected(int position) {
            int realPosition = toRealPosition(position);
            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (isCarousel && null != getAdapter() && getAdapter().getCount() > 3) {
                int position = getCurrentItem();
                if (state == ViewPager.SCROLL_STATE_IDLE &&
                        (position == 0 || position == getAdapter().getCount() - 1)) {
                    int nextPosition = getEdgeNextPosition(position);
                    setCurrentItem(nextPosition, false);
                }
            }
        }
    };


    /**
     * @param position
     * @return
     */
    public int toRealPosition(int position) {
        if (null == getAdapter())
            return 0;

        if (!isCarousel)
            return position;

        int realCount = getRealCount();
        if (realCount == 0)
            return 0;
        /**
         *  0 1 2 3    realcount 4
         *
         *  0 1 2 3 4 5  position
         *  3 0 1 2 3 0    realpos
         */
        int realPosition = (position - 1) % realCount;
        if (realPosition < 0)
            realPosition += realCount;
        return realPosition;
    }

    /**
     * @return
     */
    public int getRealCount() {
        if (null == getAdapter())
            return 0;
        //循环特殊处理
        if (isCarousel && getAdapter().getCount() > 3)
            return getAdapter().getCount() - 2;
        return getAdapter().getCount();
    }

    /**
     * 边缘位置 跳转到下一个 可左右滑的位置
     * 0 1 2 3 4 5    里面的 0 5
     * 3 0 1 2 3 0      对应 4 1
     *
     * @param position
     * @return
     */
    private int getEdgeNextPosition(int position) {
        int nextPosition = position;
        if (isCarousel && getRealCount() > 1) {
            if (position == 0) {
                nextPosition = getRealCount();
            } else if (position == getRealCount() + 1) {
                nextPosition = 1;
            }
        }
        return nextPosition;
    }
}


