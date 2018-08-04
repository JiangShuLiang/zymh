package com.zymh.www.zymh.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.Constants;
import com.zymh.www.zymh.bean.SectionBean;

import java.util.List;


public class HomeAdapter extends BaseSectionQuickAdapter<SectionBean, BaseViewHolder> {
    Context context;

    public HomeAdapter(int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
    }


    public HomeAdapter(Context context, int layoutResId, int sectionHeadResId, List data) {
        super(layoutResId, sectionHeadResId, data);
        this.context = context;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionBean item) {
        helper.setText(R.id.tv_head_title, item.header)
                .addOnClickListener(R.id.btn_more);

        // 此处要老老实实分别设置显示和隐藏
        // 切勿只设一个显示或隐藏，因为这样会导致滑动过程中数据错乱！！！！
        Button btnMore = helper.getView(R.id.btn_more);
        if (!item.showMore) {
            btnMore.setVisibility(View.GONE);
        } else {
            btnMore.setVisibility(View.VISIBLE);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void convert(BaseViewHolder viewHolder, SectionBean item) {
        viewHolder.setText((R.id.tv_title), item.t.getBookName())
                .setText((R.id.tv_subtitle), item.t.getSummary());

        ImageView ivCover = viewHolder.getView(R.id.iv_cover);
        Glide.with(context)
                .load(Constants.HOST_PIC + item.t.getPortraitIcon())
                .centerCrop()
                .placeholder(R.mipmap.ic_app_icon)
                .into(ivCover);
    }
}