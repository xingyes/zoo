package com.xingye.netzoo.zooapplication.bookregister;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingye.netzoo.xylib.utils.ToolUtil;
import com.xingye.netzoo.zooapplication.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by yx on 17/6/11.
 */

public class CalendarAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    int    sepcolor = -1;
    int    norcolor = -1;
    int    transparent = -1;
    int white = -1;
    private  int  choice = 0;
    private Calendar  calendar = Calendar.getInstance();
    private ArrayList<String>  weekName = new ArrayList<String>();
    private ArrayList<String>  dateName = new ArrayList<String>();
    public OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener
    {
        public void onItemClick(View view,Object tag);
    };

    public void setChoice(int opt)
    {
        choice = opt;
    }
    public int getChoice()
    {
        return choice;
    }

    public CalendarAdapter(OnItemClickListener listener)
    {
        mOnItemClickListener = listener;
        int mDay;
        int mWay;
        for(int i=0;i < 14;i++)
        {
            mWay = calendar.get(Calendar.DAY_OF_WEEK);
            weekName.add(ToolUtil.WEEKDAYS_CH[mWay]);
            mDay = calendar.get(Calendar.DAY_OF_MONTH);
            dateName.add(""+mDay);

            calendar.add(Calendar.DATE,1);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar, parent, false);
        if(sepcolor<0) {
            sepcolor = parent.getContext().getResources().getColor(R.color.c_5E4D3F);
            transparent = parent.getContext().getResources().getColor(R.color.c_00000000);
            norcolor = parent.getContext().getResources().getColor(R.color.c_FAFAFA);
            white = parent.getContext().getResources().getColor(R.color.c_FFFFFF);
        }
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(null!=mOnItemClickListener)
                    mOnItemClickListener.onItemClick(view,view.getTag());
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).weektv.setText(weekName.get(position));
        ((ViewHolder)holder).monthtv.setText(dateName.get(position));
        if(position == choice)
        {
            ((ViewHolder)holder).monthtv.setTextColor(white);
            ((ViewHolder)holder).monthtv.setBackgroundResource(R.drawable.round_brown);
            ((ViewHolder)holder).sepv.setBackgroundColor(sepcolor);
        }
        else{
            ((ViewHolder)holder).monthtv.setTextColor(sepcolor);
            ((ViewHolder)holder).monthtv.setBackgroundColor(transparent);
            ((ViewHolder)holder).sepv.setBackgroundColor(norcolor);
        }
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return weekName.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView weektv;
        public TextView monthtv;
        public ImageView sepv;
        public ViewHolder(View view){
            super(view);
            weektv = (TextView) view.findViewById(R.id.weekday_tv);
            monthtv = (TextView) view.findViewById(R.id.monthday_tv);
            sepv = (ImageView) view.findViewById(R.id.sep_v);
        }
    }
}
