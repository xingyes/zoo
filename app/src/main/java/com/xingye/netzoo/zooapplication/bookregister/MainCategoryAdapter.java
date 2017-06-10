package com.xingye.netzoo.zooapplication.bookregister;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingye.netzoo.zooapplication.R;
import com.xingye.netzoo.zooapplication.main.MainFragment;

import java.util.ArrayList;

/**
 * Created by yx on 17/6/10.
 */

public class MainCategoryAdapter extends BaseAdapter{

    private ArrayList<String> info = new ArrayList<String>();
    private int choice = 0;
    private LayoutInflater inflater;
    private Context mContext;
    public MainCategoryAdapter(Context context, ArrayList<String > list)
    {
        mContext = context;
        inflater = LayoutInflater.from(context);
        setInfo(list);
    }

    public MainCategoryAdapter(Context context, String[] list)
    {
        mContext = context;
        inflater = LayoutInflater.from(context);
        setInfo(list);
    }

    public void setInfo(String[]list)
    {
        for(String item : list)
            info.add(item);
    }

    public void setChoice(int pos)
    {
        choice = pos;
    }

    public int getChoice()
    {
        return choice;
    }
    public void setInfo(ArrayList<String> list)
    {
        info.clear();
        info.addAll(list);
    }

    @Override
    public int getCount() {
        return info.size();
    }

    @Override
    public Object getItem(int position) {
        return position>=info.size() ? null : info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_first_text, null);

            ViewHolder vh = new ViewHolder();
//            vh.imv = (ImageView) convertView.findViewById(R.id.item_imv);
            vh.tv = (TextView) convertView.findViewById(R.id.item_tv);
            convertView.setTag(vh);
        }
        final ViewHolder vh = (ViewHolder) convertView.getTag();
        vh.tv.setText(info.get(position));

        convertView.setBackgroundColor(mContext.getResources().getColor(position == choice ?
                    R.color.c_FFFFFF: R.color.c_F2EBD8));
        vh.tv.setTextColor(mContext.getResources().getColor(position == choice ?
                R.color.c_5E4D3F: R.color.c_666666));
//        vh.imv.setImageResource(enImvArray[position]);
        return convertView;

    }


    public class ViewHolder
    {
        TextView tv;
        ImageView imv;
    }
}
