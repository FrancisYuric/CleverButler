package com.example.xushiyun.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.entity.CourierInfo;
import com.example.xushiyun.smartbutler.ui.CourierActivity;

import java.util.List;

/**
 * Created by xushiyun on 2017/12/9.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.adapter
 * File Name:    CourierAdapter
 * Descripetion: Todo
 */

public class CourierAdapter extends BaseAdapter {
    //四个变量分别储存当前的上下文信息,已经储存的快递信息,实例化,单条信息
    private Context context;
    private List<CourierInfo> courierInfos;
    private LayoutInflater layoutInflater;
    private CourierInfo courierInfo;

    public CourierAdapter(Context context, List<CourierInfo> courierInfos) {
        this.context = context;
        this.courierInfos = courierInfos;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return courierInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return courierInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null) {
            //第一次
            viewHolder = new ViewHolder();
            courierInfo = courierInfos.get(i);
            view = layoutInflater.inflate(R.layout.courier_item, null);
            viewHolder.tv_remark = view.findViewById(R.id.tv_remark);
            viewHolder.tv_zone = view.findViewById(R.id.tv_zone);
            viewHolder.tv_datetime = view.findViewById(R.id.tv_time);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        courierInfo = courierInfos.get(i);
        viewHolder.setRemark(courierInfo.getRemark());
        viewHolder.setDatetime(courierInfo.getDatetime());
        viewHolder.setZone(courierInfo.getZone());
        view.setTag(viewHolder);
        return view;
    }

    private class ViewHolder{
        //状态
        private TextView tv_remark;
        //时间
        private TextView tv_datetime;
        //位置
        private TextView tv_zone;

        private void setRemark(String remark) {
            tv_remark.setText(remark);
        }
        private void setDatetime(String datetime) {
            tv_datetime.setText(datetime);
        }
        private void setZone(String zone) {
            tv_zone.setText(zone);
        }
    }
}
