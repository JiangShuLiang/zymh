package com.zymh.www.zymh.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.Constants;
import com.zymh.www.zymh.bean.UpdatePageData;

import java.util.List;

public class CollectionAdapter extends BaseQuickAdapter<UpdatePageData, BaseViewHolder> {
    BaseViewHolder viewHolder;
    Context context;

    public CollectionAdapter(int resContentView, List<UpdatePageData> data) {
        super(resContentView, data);
    }

    public CollectionAdapter(Context context, int resContentView, List<UpdatePageData> data) {
        super(resContentView, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, UpdatePageData item) {
        this.viewHolder = viewHolder;
        viewHolder.setText(R.id.tv_title, item.getBookName())
                .setText(R.id.tv_hit, item.getHits()+"");
        // 封面
        ImageView ivCover = viewHolder.getView(R.id.iv_cover);
        String picUrl = Constants.HOST_PIC + item.getPortraitIcon();
        Glide.with(context)
                .load(picUrl)
                .centerCrop()
                .placeholder(R.mipmap.ic_app_icon)
                .into(ivCover);
    }
}

