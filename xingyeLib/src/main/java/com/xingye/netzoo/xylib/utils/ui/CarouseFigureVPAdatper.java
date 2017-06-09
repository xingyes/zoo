package com.xingye.netzoo.xylib.utils.ui;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by yx on 17/6/8.
 */

public class CarouseFigureVPAdatper extends PagerAdapter{

//    protected final static int LAST_URL = R.id.image_last_url;
    private Context context;
    /**
     * 是否轮播
     */
    private boolean isCarousel;
    private CarouseFigureImageAdapterListener imageAdapterListener;

    private boolean isAllChange;
    /**
     * 焦点图缓存
     */
    private SimpleDraweeView first;
    private SimpleDraweeView last;
//    protected JDDisplayImageOptions displayOptions;

    public CarouseFigureVPAdatper(Context c, boolean isCarousel, CarouseFigureImageAdapterListener listener) {
        context = c;
        this.isCarousel = isCarousel;
        imageAdapterListener = listener;
        initImage();
    }

    @Override
    public void notifyDataSetChanged() {
        initImage();
        super.notifyDataSetChanged();
    }

    /**
     * 所有数据发生变化
     * @param isAllChange
     */
    public void setAllChanged(boolean isAllChange) {
        this.isAllChange = isAllChange;
    }

    @Override
    public int getItemPosition(Object object) {
        if (isAllChange) {
            return POSITION_NONE;
        } else {
            return POSITION_UNCHANGED;
        }
    }

    @Override
    public int getCount() {
        if (imageAdapterListener != null) {
            return imageAdapterListener.getCount() + (isTrueCarousel() ? 2 : 0);
        }
        return 0;
    }

    private boolean isTrueCarousel() {
        if (imageAdapterListener != null && isCarousel) {
            return imageAdapterListener.getCount() >= 2;
        }
        return false;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    private void initImage() {
        if (!isTrueCarousel()) {
            return;
        }
        if (first == null) {
            first = getImageView();
        }
        if (last == null) {
            last = getImageView();
        }

        //初始化最后一张，防止第一次手动向左滑的闪屏
//        displayImage(last, imageAdapterListener.getImageUrl(imageAdapterListener.getCount() - 1), imageAdapterListener.getJDDisplayImageOptions());
    }

    protected int getRealIndex(int position) {
        int index = position;
        if (isCarousel && imageAdapterListener.getCount() > 1) {
            index = (position - 1) % imageAdapterListener.getCount();
            if (index < 0)
                index += imageAdapterListener.getCount();
        }
        return index;
    }

    private SimpleDraweeView getImageView() {
        SimpleDraweeView imageView = new SimpleDraweeView(context);
        imageView.setPadding(0, 0, 0, 0);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!NetUtil.isNetworkAvailable()) {
                    return;
                }
                int position = getRealIndex(v.getId());
                imageAdapterListener.onClick(position);
            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        SimpleDraweeView imageView;
        try {
            if (isCarousel && first != null && position == 1) {
                imageView = first;
            } else if (isCarousel && last != null && position == getCount() - 2) {
                imageView = last;
            } else {
                imageView = getImageView();
            }
            imageView.setId(position);
            container.addView(imageView);
            imageView.setImageURI(imageAdapterListener.getImageUrl(getRealIndex(position)));
        } catch (Exception e) {
            imageView = new SimpleDraweeView(context);
        }
        if (isAllChange) {
            isAllChange = false;
        }
        return imageView;
    }

//    public void displayImage(ImageView imageView, String url, JDDisplayImageOptions options) {
//        if (displayOptions == null) {
//            if (options == null) {
//                displayOptions = new JDDisplayImageOptions().resetViewBeforeLoading(false).showImageOnFail(new ExceptionDrawable(StringUtil.app_name));
//            } else {
//                displayOptions = options;
//            }
//        }
//        displayOptions.bitmapConfig(Bitmap.Config.RGB_565);
//        if (imageView.getTag(LAST_URL) != null && url != null && url.equals(imageView.getTag(LAST_URL))) {
//            if (imageView.getTag(JDImageUtils.STATUS_TAG) == null) {
//                return;
//            }
//            if (!imageView.getTag(JDImageUtils.STATUS_TAG).equals(Integer.valueOf(JDImageUtils.STATUS_FAILED))) {
//                return;
//            }
//        }
//        // 设置ImageView的图片
//        if (imageView.getTag(LAST_URL) != null) {
//            JDImageUtils.displayImage(url, imageView, displayOptions, false);
//        } else {
//            JDImageUtils.displayImage(url, imageView, displayOptions, true);
//        }
//        imageView.setTag(LAST_URL, url);
//    }

    public interface CarouseFigureImageAdapterListener {
        int getCount();

        String getImageUrl(int position);

        void onClick(int position);

//        JDDisplayImageOptions getJDDisplayImageOptions();
    }
}
