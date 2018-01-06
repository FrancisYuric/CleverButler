package com.example.xushiyun.smartbutler.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.entity.GirlInfo;
import com.example.xushiyun.smartbutler.utils.L;
import com.example.xushiyun.smartbutler.utils.PicassoUtils;
import com.example.xushiyun.smartbutler.view.MyAlertDialog;

import java.util.List;

/**
 * Created by xushiyun on 2017/12/11.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.adapter
 * File Name:    GirlGridAdapter
 * Descripetion: Todo
 */

public class GirlGridAdapter extends BaseAdapter {

    //定义日常的相关组件,感觉都差不多,所以这里就不多解释了
    private Context context;
    private List<GirlInfo> girlInfoList;
    private LayoutInflater layoutInflater;
    private GirlInfo girlInfo;
    private WindowManager wm;
    private int width;
    private int height;

    public GirlGridAdapter(Context context, List<GirlInfo> girlInfoList) {
        this.context = context;
        this.girlInfoList = girlInfoList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
    }

    @Override
    public int getCount() {
        return girlInfoList.size();
    }

    @Override
    public Object getItem(int i) {
        return girlInfoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.girl_item, null);
            viewHolder.imageView = view.findViewById(R.id.iv_photo);
            view.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) view.getTag();
        girlInfo = girlInfoList.get(i);
        //显示对应的屏幕的尺寸
//        L.i("width:" + width + " " + "height:" + height);
        if(!TextUtils.isEmpty(girlInfo.getUrl())) {
            //加载图片
            PicassoUtils.loadImageViewSize(context, girlInfo.getUrl(), width / 2, 500, viewHolder.imageView);
        }
        return view;
    }

    class ViewHolder {
        private ImageView imageView;
    }
}
