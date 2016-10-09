package com.liu.wheeldemo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.liu.wheeldemo.R;

import java.util.List;

import kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

/**
 * @Description: 描述
 * @AUTHOR 刘楠  Create By 2016/10/9 0009 13:12
 */
public class TimerAdapter extends AbstractWheelTextAdapter {


    List<String> mDatas;
    Context      mContext;


    public TimerAdapter(Context context, List<String> datas) {

        super(context, R.layout.item_time, NO_RESOURCE);

        mContext = context;
        mDatas = datas;
        setItemTextResource(R.id.time);
    }

    @Override
    protected CharSequence getItemText(int index) {
        return mDatas.get(index);
    }

    @Override
    public int getItemsCount() {
        return mDatas.size();
    }

    @Override
    public View getItem(int index, View convertView, ViewGroup parent) {

        View view = super.getItem(index, convertView, parent);

        return view;


    }


}
