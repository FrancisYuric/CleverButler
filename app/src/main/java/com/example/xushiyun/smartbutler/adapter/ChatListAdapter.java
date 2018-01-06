package com.example.xushiyun.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.entity.ChatListData;

import java.util.List;

/**
 * Created by xushiyun on 2017/12/9.
 * Project Name: SmartButler
 * Package Name: com.example.xushiyun.smartbutler.adapter
 * File Name:    ChatListAdapter
 * Descripetion: Todo
 */

public class ChatListAdapter extends BaseAdapter {
    public static final Boolean STATUS_LEFT = false;
    public static final Boolean STATUS_RIGHT = true;

    private Context context;
    private List<ChatListData> chatListDatas;
    private ChatListData chatListData;
    private LayoutInflater layoutInflater;

    public ChatListAdapter(Context context, List<ChatListData> chatListDatas) {
        this.chatListDatas = chatListDatas;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return chatListDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return chatListDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolderLeft viewHolderLeft = null;
        ViewHolderRight viewHolderRight = null;
        if(chatListDatas.get(i).getType() == STATUS_LEFT) {
            if(viewHolderLeft == null) {
                viewHolderLeft = new ViewHolderLeft();
                view = layoutInflater.inflate(R.layout.chat_left, null);
                viewHolderLeft.textView = view.findViewById(R.id.tv_chat);
                view.setTag(viewHolderLeft);
            }
            else{
                viewHolderLeft = (ViewHolderLeft) view.getTag();
            }
            viewHolderLeft.textView.setText(chatListDatas.get(i).getData());
        }
        else {
            if(viewHolderRight == null) {
                viewHolderRight = new ViewHolderRight();
                view = layoutInflater.inflate(R.layout.chat_right, null);
                viewHolderRight.textView = view.findViewById(R.id.tv_chat);
                view.setTag(viewHolderRight);
            }
            else {
                viewHolderRight = (ViewHolderRight) view.getTag();
            }
            viewHolderRight.textView.setText(chatListDatas.get(i).getData());
        }

        return view;

    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return chatListDatas.get(position).getType() == STATUS_LEFT? 0:1;
    }

    class ViewHolderLeft{
        private TextView textView;
    }

    class ViewHolderRight{
        private TextView textView;
    }
}
