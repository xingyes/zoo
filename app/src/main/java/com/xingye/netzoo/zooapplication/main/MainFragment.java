package com.xingye.netzoo.zooapplication.main;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xingye.netzoo.xylib.utils.ToolUtil;
import com.xingye.netzoo.xylib.utils.net.JsonCallback;
import com.xingye.netzoo.xylib.utils.net.OkhttpUtil;
import com.xingye.netzoo.xylib.utils.net.RequestFactory;
import com.xingye.netzoo.xylib.utils.ui.CarouseFigureVPAdatper;
import com.xingye.netzoo.xylib.utils.ui.CarouselFigureViewPager;
import com.xingye.netzoo.xylib.utils.ui.NestGridView;
import com.xingye.netzoo.xylib.utils.ui.UiUtils;
import com.xingye.netzoo.xylib.utils.ui.ViewpagerCursor;
import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.bookregister.BookRegisterActivity;
import com.xingye.netzoo.zooapplication.hospital.HospitalInfoActivity;
import com.xingye.netzoo.zooapplication.medicreport.ReportQueryActivity;
import com.xingye.netzoo.zooapplication.medicsuggest.SuggestCatelistActivity;
import com.xingye.netzoo.zooapplication.reserve.ReserveChooseActivity;
import com.xingye.netzoo.zooapplication.utils.Config;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import okhttp3.Call;
import okhttp3.Request;


public class MainFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    private ViewpagerCursor          bannerCursor;
    private CarouselFigureViewPager  bannerView;
    private PagerAdapter             bannerAdapter;
    private NestGridView gridView;
    private BaseAdapter              gridAdapter;
    private RelativeLayout           bannerLayout;
    private int[]      enTvArray = {R.string.entrance_0,R.string.entrance_1,R.string.entrance_2,
            R.string.entrance_3,R.string.entrance_4,R.string.entrance_5,
            R.string.entrance_6,R.string.entrance_7,R.string.entrance_8};

    private int[]      enImvArray = {R.mipmap.icon_entrance_0,R.mipmap.icon_entrance_1,R.mipmap.icon_entrance_2,
            R.mipmap.icon_entrance_3,R.mipmap.icon_entrance_4,R.mipmap.icon_entrance_5,
            R.mipmap.icon_entrance_6,R.mipmap.icon_entrance_7,R.mipmap.icon_entrance_8};

    @Override
    public View initView() {
        mRoot = mContext.getLayoutInflater().inflate(R.layout.fragment_main,null);
        bannerLayout = (RelativeLayout)mRoot.findViewById(R.id.carouse_vp_layout);
        bannerView = (CarouselFigureViewPager)mRoot.findViewById(R.id.home_banner);
        bannerAdapter = new CarouseFigureVPAdatper(this.getActivity(), true,
                new CarouseFigureVPAdatper.CarouseFigureImageAdapterListener() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public String getImageUrl(int position) {
                return "res://" + MainFragment.this.getActivity().getPackageName() +"/" + R.mipmap.banner_photo;
            }

            @Override
            public void onClick(int position) {
                UiUtils.startActivity(MainFragment.this.getActivity(), HospitalInfoActivity.class,false);
            }
        });
        bannerView.setAdapter(bannerAdapter);
        bannerCursor = new ViewpagerCursor();
        bannerCursor.initCursorContentView(getActivity(),bannerView,null,20);
        bannerCursor.createCursor(bannerView.getRealCount(),bannerLayout,
                bannerView.toRealPosition(bannerView.getCurrentItem()));
        bannerView.addOnPageChangeListener(this);

        gridView = (NestGridView)mRoot.findViewById(R.id.home_grid);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                switch (position)
                {
                    case 0:
                        UiUtils.startActivity(MainFragment.this.getActivity(), BookRegisterActivity.class,true);
                        break;
                    case 1:
                        bundle.putBoolean(BookRegisterActivity.ONLY_TODAY,true);
                        UiUtils.startActivity(MainFragment.this.getActivity(), BookRegisterActivity.class,bundle,true);
                        break;
                    case 2:
                        UiUtils.startActivity(MainFragment.this.getActivity(), ReportQueryActivity.class,true);
                        break;
                    case 3:
                        UiUtils.startActivity(MainFragment.this.getActivity(), HospitalInfoActivity.class,false);
                        break;
                    case 5:
                        UiUtils.startActivity(MainFragment.this.getActivity(),ReserveChooseActivity.class,true);
                        break;
                    case 8:
                        UiUtils.startActivity(MainFragment.this.getActivity(), SuggestCatelistActivity.class,true);
                        break;
                    default:
                        break;
                }
            }
        });
        gridAdapter = new BaseAdapter() {
                LayoutInflater inflater = LayoutInflater.from(MainFragment.this.getActivity());
                @Override
                public int getCount() {
                    return enTvArray.length;
                }

                @Override
                public Object getItem(int position) {
                    if(position>=enTvArray.length)
                        return null;
                    return enTvArray[position];
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

//                @Override
//                public int getViewTypeCount() {
//                    return 2;
//                }
//
//                @Override
//                public int getItemViewType(int position) {
//                    if (position == 0) {
//                        return 1;
//                    }
//                    return 0;
//                }
//
                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {
                    if (convertView == null) {
                        convertView = inflater.inflate(R.layout.item_entrance, null);

                        ViewHolder vh = new ViewHolder();
                        vh.imv = (ImageView) convertView.findViewById(R.id.item_imv);
                        vh.tv = (TextView) convertView.findViewById(R.id.item_tv);
                        convertView.setTag(vh);
                    }
                    final ViewHolder vh = (ViewHolder) convertView.getTag();
                    vh.tv.setText(enTvArray[position]);
                    vh.imv.setImageResource(enImvArray[position]);
                    return convertView;
                }
            };
        gridView.setAdapter(gridAdapter);
        return mRoot;
    }

    @Override
    public void initData() {
//        RequestBody reqBody = new FormBody.Builder().add("username", "aaa").add("password", "bbb").build();
        Request.Builder  rb = RequestFactory.getRequest(Config.URL_ZHIHU,null,null,null);
        if (null == rb) return;

        try {
            OkhttpUtil.request(rb, OkhttpUtil.CACHED_NETWORK_RACE,
                    new JsonCallback<ZhihuModel>(){

                        @Override
                        public void onFailure(Call call, Exception e) {
//                            Object px = Proxy.newProxyInstance(JsonCallback.class.getClassLoader(),
//                                    new Class<?>[]{JsonCallback.class},
//                                    new InvocationHandler() {
//                                        @Override
//                                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                                            return null;
//                                        }
//                                    });
                        }

                        @Override
                        public void onResponse(Call call, ZhihuModel object) throws IOException {
                            int i = 0;
                            i++;
                        }
                    });


//


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if(null!=bannerCursor) {
            bannerCursor.onPageSelected(position);
            bannerCursor.setCurrentCursorPosition(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public class ViewHolder
    {
        ImageView imv;
        TextView  tv;
    }
}
