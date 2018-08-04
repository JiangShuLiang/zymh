package com.zymh.www.zymh.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.Constants;
import com.zymh.www.zymh.bean.ComicPagesDataPages;

import java.util.List;

public class ComicPagesAdapter2 extends BaseQuickAdapter<ComicPagesDataPages, BaseViewHolder> {
    BaseViewHolder viewHolder;
    Context context;

    public ComicPagesAdapter2(int resContentView, List<ComicPagesDataPages> data) {
        super(resContentView, data);
    }

    public ComicPagesAdapter2(Context context, int resContentView, List<ComicPagesDataPages> data) {
        super(resContentView, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, ComicPagesDataPages item) {
        this.viewHolder = viewHolder;
        // 封面
        ImageView ivCover = viewHolder.getView(R.id.iv_page);

        String picUrl = Constants.HOST_PIC + item.getPageImage();

        Glide.with(context)
                .load(picUrl)
                .asBitmap()
                .into(ivCover);
    }
}

