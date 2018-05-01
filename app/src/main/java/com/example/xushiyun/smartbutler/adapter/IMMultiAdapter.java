package com.example.xushiyun.smartbutler.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.entity.IMEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xushiyun on 2018/4/25.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler.adapter
 * File Name:    IMMultiAdapter
 * Description: Todo 两种布局的RecyclerView
 */

public class IMMultiAdapter extends RecyclerView.Adapter {
    private static final int EMPTY_VIEW = 0;
    private static final int LEFT_VIEW = 1;
    private static final int RIGHT_VIEW = 2;

    private List<IMEntity> list;

    public IMMultiAdapter(List<IMEntity> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if(list.size() == 0) {
            return EMPTY_VIEW;
        }else if(list.get(position).status == 0) {
            return LEFT_VIEW;
        }else if(list.get(position).status == 1) {
            return RIGHT_VIEW;
        }else {return super.getItemViewType(position);}
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(viewType == LEFT_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.im_left,parent, false);
            return new LeftViewHolder(view);
        }else if(viewType == RIGHT_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.im_right, parent, false);
            return new RightViewHolder(view);
        }else  {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        IMEntity entity = list.get(position);
        if(holder instanceof LeftViewHolder) {
            if(!TextUtils.isEmpty(entity.content))
            ((LeftViewHolder) holder).content_left.setText(entity.content);
        }else if(holder instanceof RightViewHolder) {
            if(!TextUtils.isEmpty(entity.content))
                ((RightViewHolder) holder).content_right.setText(entity.content);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class LeftViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon_left)
        CircleImageView icon_left;
        @BindView(R.id.content_left)
        TextView content_left;
        public LeftViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class RightViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.icon_right)
        CircleImageView icon_right;
        @BindView(R.id.content_right)
        TextView content_right;
        public RightViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}

