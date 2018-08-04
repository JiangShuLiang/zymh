package com.zymh.www.zymh.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.Utils;
import com.zymh.www.zymh.bean.ComicDetailDataChapters;

import java.util.List;

public class ChapterAdapter extends BaseQuickAdapter<ComicDetailDataChapters, BaseViewHolder> {
    BaseViewHolder viewHolder;
    Context context;

    public ChapterAdapter(int resContentView, List<ComicDetailDataChapters> data) {
        super(resContentView, data);
    }

    public ChapterAdapter(Context context, int resContentView, List<ComicDetailDataChapters> data) {
        super(resContentView, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, ComicDetailDataChapters item) {
        this.viewHolder = viewHolder;
        viewHolder.setText(R.id.tv_chapter, "第"+item.getChapterIndex()+"章")
                .setText(R.id.tv_update_time, "更新时间："+Utils.timestampToString((long)item.getUpdateDate()));

        ImageView ivLock = viewHolder.getView(R.id.iv_lock);
        TextView tvLock = viewHolder.getView(R.id.tv_lock);
        int costCoin = item.getCostCoin();
        switch (costCoin){
            case -2://已购买,用unlock图标
                Glide.with(context).load(R.mipmap.ic_unlock).into(ivLock);
                break;
            case -1://限时免费,用红色字体“限免”
                ivLock.setVisibility(View.GONE);
                tvLock.setVisibility(View.VISIBLE);
                tvLock.setTextColor(Color.RED);
                tvLock.setText("限免");
                break;
            case 0://免费
                ivLock.setVisibility(View.GONE);
                tvLock.setVisibility(View.VISIBLE);
                break;
            //其他就是收费的,用lock图标
        }
    }
}

