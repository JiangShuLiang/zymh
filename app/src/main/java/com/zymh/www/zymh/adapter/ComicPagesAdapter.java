package com.zymh.www.zymh.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zymh.www.zymh.BuildConfig;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.Constants;
import com.zymh.www.zymh.bean.ComicPagesDataPages;

import java.util.List;

public class ComicPagesAdapter extends BaseQuickAdapter<ComicPagesDataPages, BaseViewHolder> {
    BaseViewHolder viewHolder;
    Context context;

    public ComicPagesAdapter(int resContentView, List<ComicPagesDataPages> data) {
        super(resContentView, data);
    }

    public ComicPagesAdapter(Context context, int resContentView, List<ComicPagesDataPages> data) {
        super(resContentView, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, ComicPagesDataPages item) {
        this.viewHolder = viewHolder;
        // 封面
        ImageView ivCover = viewHolder.getView(R.id.iv_page);

//        LongImageView ivCover =(LongImageView) viewHolder.getView(R.id.liv_page);
        String picUrl = Constants.HOST_PIC + item.getPageImage();


        Glide.with(context)
                .load(picUrl)
                .asBitmap()
                .into(new LongImageTarget(ivCover));
    }

    private static class LongImageTarget extends SimpleTarget<Bitmap> implements ViewTreeObserver.OnPreDrawListener{

        private final ImageView iv;
        private float width;
        private Bitmap resource;

        public LongImageTarget(ImageView iv) {
            this.iv=iv;
            iv.getViewTreeObserver().addOnPreDrawListener(this);
        }

        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            if (width>0){
                iv.setImageBitmap(handleBitmap(resource,width));
            }else {
                this.resource=resource;
            }
        }

        @Override
        public boolean onPreDraw() {
            if (BuildConfig.DEBUG){
                Log.d(TAG, "onPreDraw: "+this);
            }
            int measuredWidth = iv.getMeasuredWidth();
            if (resource!=null){
                iv.setImageBitmap(handleBitmap(resource,measuredWidth));
                resource=null;
            }else {
                width=measuredWidth;
            }

            iv.getViewTreeObserver().removeOnPreDrawListener(this);

            return false;
        }

        private Bitmap handleBitmap(Bitmap originalBitmap,float desWith){

            float width = originalBitmap.getWidth();
            float height = originalBitmap.getHeight();

            int desHeight= (int) (desWith*height/width);

            // 计算缩放比例
            float scaleWidth =  desWith/ width;
            float scaleHeight = desHeight / height;
            // 取得想要缩放的matrix参数
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);
            // 得到新的图片
            return Bitmap.createBitmap(originalBitmap, 0, 0, (int)width, (int)height, matrix, true);

        }

    }


}

