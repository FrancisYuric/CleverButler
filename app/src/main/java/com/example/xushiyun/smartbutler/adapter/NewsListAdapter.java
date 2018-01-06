package com.example.xushiyun.smartbutler.adapter;

import android.content.Context;
import android.media.Image;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.entity.NewsData;
import com.example.xushiyun.smartbutler.utils.PicassoUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by xushiyun on 2017/12/10.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.adapter
 * File Name:    NewsListAdapter
 * Descripetion: Todo
 */

public class NewsListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<NewsData> newsDataList;
    private NewsData newsData;

    public NewsListAdapter(Context context, List<NewsData> newsDataList) {
        this.context = context;
        this.newsDataList = newsDataList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return newsDataList.size();
    }

    @Override
    public Object getItem(int i) {
        return newsDataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(viewHolder == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.news_item, null);
            viewHolder.iv_icon = view.findViewById(R.id.iv_icon);
            viewHolder.tv_title = view.findViewById(R.id.tv_title);
            viewHolder.tv_from = view.findViewById(R.id.tv_from);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }
        newsData = newsDataList.get(i);
        viewHolder.tv_title.setText(newsData.getTitle());
        viewHolder.tv_from.setText(newsData.getFrom());
        //加载图片
        int width = context.getResources().getDisplayMetrics().widthPixels;
//        Picasso.with(context).load(newsData.getPic()).into(viewHolder.iv_icon);
        if(!TextUtils.isEmpty(newsData.getPic())) {
            //加载图片
            PicassoUtils.loadImageViewSize(context, newsData.getPic(), width / 2, 500, viewHolder.iv_icon);
        }
        return view;
    }

    class ViewHolder{
        private ImageView iv_icon;
        private TextView tv_title;
        private TextView tv_from;
    }
}
