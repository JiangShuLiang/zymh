package com.zymh.www.zymh.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.Constants;
import com.zymh.www.zymh.bean.SearchResultData;

import java.util.List;

public class SearchAdapter extends BaseQuickAdapter<SearchResultData, BaseViewHolder> {
    BaseViewHolder viewHolder;
    Context context;

    public SearchAdapter(int resContentView, List<SearchResultData> data) {
        super(resContentView, data);
    }

    public SearchAdapter(Context context, int resContentView, List<SearchResultData> data) {
        super(resContentView, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, SearchResultData item) {
        this.viewHolder = viewHolder;
        viewHolder.setText(R.id.tv_title, item.getBookName())
                .setText(R.id.tv_hit, "点击:" + item.getHits())
                .setText(R.id.tv_description, item.getSummary());
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

