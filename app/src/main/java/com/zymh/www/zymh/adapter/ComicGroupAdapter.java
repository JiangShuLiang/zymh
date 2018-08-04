package com.zymh.www.zymh.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.Constants;
import com.zymh.www.zymh.bean.ComicGroupDataBooks;

import java.util.List;

public class ComicGroupAdapter extends BaseQuickAdapter<ComicGroupDataBooks, BaseViewHolder> {
    BaseViewHolder viewHolder;
    Context context;

    public ComicGroupAdapter(int resContentView, List<ComicGroupDataBooks> data) {
        super(resContentView, data);
    }

    public ComicGroupAdapter(Context context, int resContentView, List<ComicGroupDataBooks> data) {
        super(resContentView, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, ComicGroupDataBooks item) {
        this.viewHolder = viewHolder;
        viewHolder.setText(R.id.tv_title, item.getBookName())
                .setText(R.id.tv_hit, "点击:" + item.getHits()+"");
        // 封面
        ImageView ivCover = viewHolder.getView(R.id.iv_cover);
        String picUrl = Constants.HOST_PIC + item.getBookIcon();
        Glide.with(context)
                .load(picUrl)
                .centerCrop()
                .placeholder(R.mipmap.ic_app_icon)
                .into(ivCover);
    }
}

