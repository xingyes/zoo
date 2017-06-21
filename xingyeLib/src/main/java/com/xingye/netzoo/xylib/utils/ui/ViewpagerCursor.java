package com.xingye.netzoo.xylib.utils.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xingye.netzoo.xylib.R;

/**
 * Created by yx on 17/6/20.
 */

public class ViewpagerCursor {

    private class CursorParams
    {
        public CursorParams()
        {
            normalRid = R.drawable.icon_cursor_unselect;
            lightRid = R.drawable.icon_cursor_selected;
            width = 20;
            height = 20;
            space= 20;
            marginbottom = 30;

        }
        public int width;
        public int height;
        public int space;
        public int normalRid;
        public int lightRid;
        public int marginbottom;
    }
    /**
     *
     */
    private LinearLayout cursorContent;
    private CursorParams cursorParams = new CursorParams();

    private int oldCursorPosition = 0;
    private int curPosition = 0;

    public void initCursorContentView(Context c, ViewPager pager,
                                      ICursorContentViewPresenter presenter,
                                      int cursorMarginBottom){
        if (cursorContent == null) {
            cursorContent = new LinearLayout(c);
            RelativeLayout.LayoutParams cursorParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            cursorParams.setMargins(0, 0, 0, cursorMarginBottom);
            cursorParams.addRule(RelativeLayout.ALIGN_BOTTOM,pager.getId());
            cursorParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                //cursorParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            cursorContent.setPadding(0, 0, 0, 0);
            cursorContent.setOrientation(LinearLayout.HORIZONTAL);
            cursorContent.setLayoutParams(cursorParams);
        }

        cursorParams = new CursorParams();
        if(presenter!=null) {
            cursorParams.width = presenter.getCursorWidth();
            cursorParams.height = presenter.getCursorHeight();
            cursorParams.normalRid = presenter.getNormalResource();
            cursorParams.lightRid = presenter.getLightResource();
            cursorParams.marginbottom = presenter.getCursorMarginBottom();
        }

    }

    /**
     * 生成轮播图游标
     *
     * @param size
     */
    public void createCursor(int size, ViewGroup vp, int currentItemPosition) {
        if (size < 1) { // 如果没有数据，就不要显示轮播图了
            vp.setVisibility(View.GONE);
            return;
        }

        if(cursorContent == null) return;

        if (vp.getVisibility() == View.GONE)
            vp.setVisibility(View.VISIBLE);

        if (size < 2) {
            cursorContent.setVisibility(View.GONE);
            return;
        }
        if (cursorContent.getVisibility() == View.GONE)
            cursorContent.setVisibility(View.VISIBLE);

        cursorContent.removeAllViews();

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                cursorParams.width, cursorParams.height);
        params.gravity = Gravity.CENTER;
        params.setMargins(0, 0, cursorParams.space, 0);

        for (int i = 0; i < size && size > 1; i++) {
            SimpleDraweeView cursorView = new SimpleDraweeView(cursorContent.getContext());
            cursorView.setLayoutParams(params);
            cursorView.setScaleType(ImageView.ScaleType.CENTER);
            cursorView.setImageResource(cursorParams.normalRid);
            cursorContent.addView(cursorView);
        }

        openLight(currentItemPosition);
        if (cursorContent.getParent() == null)
            vp.addView(cursorContent);
    }

    public void onPageSelected(int position) {
        closeLight(oldCursorPosition);
        openLight(position);
    }

    private void openLight(int index) {
        if ((cursorContent != null) && (index >= 0)) {
            final ImageView v = (ImageView) cursorContent.getChildAt(index);
            if (v != null)
                v.setImageResource(cursorParams.lightRid);
            curPosition = index;
        }
    }

    private void closeLight(int index) {
        if ((cursorContent != null) && (index >= 0)) {
            final ImageView v = (ImageView) cursorContent.getChildAt(index);
            if (v != null)
                v.setImageResource(cursorParams.normalRid);
        }
    }

    public void setCurrentCursorPosition(int position) {
        oldCursorPosition = position;
    }

    public boolean equalsOldCursorPosition(int position) {
        return oldCursorPosition == position;
    }

    public int getCurPosition(){
            return curPosition;
        }

    public void updateCursorBottomMargin(int cursorMarginBottom) {
        if (cursorContent != null) {
            ViewGroup.LayoutParams vplp = cursorContent.getLayoutParams();

            if ((vplp != null) && (vplp instanceof RelativeLayout.LayoutParams)) {
                RelativeLayout.LayoutParams rllp = (RelativeLayout.LayoutParams) vplp;
                rllp.setMargins(0, 0, 0, cursorMarginBottom);
                cursorContent.setLayoutParams(rllp);
            }
        }
    }

    public void showCursorContent() {
        if (cursorContent != null)
            cursorContent.setVisibility(View.VISIBLE);
    }

    public void hideCursorContent() {
        if (cursorContent != null)
            cursorContent.setVisibility(View.GONE);
    }
}