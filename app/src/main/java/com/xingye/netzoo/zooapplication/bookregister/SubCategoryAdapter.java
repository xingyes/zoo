package com.xingye.netzoo.zooapplication.bookregister;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingye.netzoo.zooapplication.R;

import java.util.ArrayList;

/**
 * Created by yx on 17/6/10.
 */

public class SubCategoryAdapter extends BaseAdapter{

    private ArrayList<String> info = new ArrayList<String>();

    private LayoutInflater inflater;

    public SubCategoryAdapter(Context context)
    {
        inflater = LayoutInflater.from(context);
    }

    public void setInfo(String[]list)
    {
        info.clear();
        for(String item : list)
            info.add(item);
    }

    public void setInfo(ArrayList<String> list)
    {
        info.clear();
        if(list!=null)
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
            convertView = inflater.inflate(R.layout.item_sub_text, null);

            ViewHolder vh = new ViewHolder();
//            vh.imv = (ImageView) convertView.findViewById(R.id.item_imv);
            vh.tv = (TextView) convertView.findViewById(R.id.item_tv);
            convertView.setTag(vh);
        }
        final ViewHolder vh = (ViewHolder) convertView.getTag();
        vh.tv.setText(info.get(position));
//        vh.imv.setImageResource(enImvArray[position]);
        return convertView;

    }


    public class ViewHolder
    {
        TextView tv;
    }
}
