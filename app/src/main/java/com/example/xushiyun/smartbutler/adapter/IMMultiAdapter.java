package com.example.xushiyun.smartbutler.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.telephony.CellIdentityCdma;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xushiyun.smartbutler.R;
import com.example.xushiyun.smartbutler.entity.IMEntity;
import com.example.xushiyun.smartbutler.holder.ViewHolder.BaseRecyclerViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by xushiyun on 2018/4/25.
 * Project Name: CleverButler
 * Package Name: com.example.xushiyun.smartbutler.adapter
 * File Name:    IMMultiAdapter
 * Description: Todo 两种布局的RecyclerView
 */

public final class IMMultiAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<IMEntity> entities;

    private final int CONVERSATION_LEFT = 0;//左侧聊天窗口
    private final int CONVERSATION_RIGHT = 1;//右侧聊天窗口


    public IMMultiAdapter(final Context context, List<IMEntity> entities) {
        this.context = context;
        this.entities = entities;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new LeftViewHolder(View.inflate(context, R.layout.pager_view_one, parent));
        if (viewType == CONVERSATION_LEFT) {
            final View view = View.inflate(context, R.layout.im_left, parent);
            final LeftViewHolder leftViewHolder = new LeftViewHolder(view);
            return leftViewHolder;
        } else {
            final View view = View.inflate(context, R.layout.im_right, parent);
            final RightViewHolder rightViewHolder = new RightViewHolder(view);
            return rightViewHolder;
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //判断不同的ViewHolder做不同的处理
        if (holder instanceof LeftViewHolder) {

        } else if (holder instanceof RightViewHolder) {

        } else {

        }
    }

    @Override
    public int getItemCount() {
        return entities.size() + 2;
    }


    @Override
    public int getItemViewType(int position) {
        final IMEntity imEntity = entities.get(position);
        if(imEntity.status == 0) return CONVERSATION_LEFT;
        else if(imEntity.status == 1) return CONVERSATION_RIGHT;
        return position % 2 == 0 ? CONVERSATION_LEFT : CONVERSATION_RIGHT;
    }

    private final class LeftViewHolder extends BaseRecyclerViewHolder {
        private CircleImageView icon = null;
        private TextView content = null;
        private final IMEntity entity = entities.get(getAdapterPosition());

        public LeftViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView() {
            super.initView();
            icon = itemView.findViewById(R.id.icon);
            content = itemView.findViewById(R.id.content);
        }

        @Override
        protected void initData() {
            super.initData();
            if (null != icon) {
                if (!TextUtils.isEmpty(entity.icon_url)) {

                }
            }
            if (content != null) {
                if (!TextUtils.isEmpty(entity.content)) {
                    content.setText(entity.content);
                }
            }
        }

        @Override
        protected void initListener() {
            super.initListener();
        }
    }

    private final class RightViewHolder extends BaseRecyclerViewHolder {
        private CircleImageView icon = null;
        private TextView content = null;
        private final IMEntity entity = entities.get(getAdapterPosition());

        public RightViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void initView() {
            super.initView();
            icon = itemView.findViewById(R.id.icon);
            content = itemView.findViewById(R.id.content);
        }

        @Override
        protected void initData() {
            super.initData();
            if (null != icon) {
                if (!TextUtils.isEmpty(entity.icon_url)) {

                }
            }
            if (content != null) {
                if (!TextUtils.isEmpty(entity.content)) {
                    content.setText(entity.content);
                }
            }
        }

        @Override
        protected void initListener() {
            super.initListener();
        }
    }
}
