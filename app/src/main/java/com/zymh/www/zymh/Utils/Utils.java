package com.zymh.www.zymh.Utils;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.zymh.www.zymh.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

public class Utils {
    private static Context context;

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

    public static ACProgressFlower showLoadingDialog(Context context, String loadingText) {
        ACProgressFlower loadingDialog = new ACProgressFlower.Builder(context)
                .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                .themeColor(Color.WHITE)
                .text(loadingText)
                .fadeColor(Color.DKGRAY).build();
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.show();
        return loadingDialog;
    }

    public static int getCode(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            int code = jsonObject.getInt("code");
            return code;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getData(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String data = jsonObject.getString("data");
            return data;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getMsg(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            String msg = jsonObject.getString("msg");
            return msg;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getCurrentDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;// Java月份从0开始算
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day;
    }

    public static String getMailType(String emailAddress) {
        int indexBegin = emailAddress.indexOf("@");
        int indexEnd = emailAddress.indexOf(".");
        // indexBegin -- 起始索引（包括）indexEnd -- 结束索引（不包括）
        String emailType = emailAddress.subSequence(indexBegin + 1, indexEnd).toString();
        return emailType;
    }

    public static SpannableStringBuilder generateColorfulText(String text, int indexBegin, int indexEnd, int color) {
        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(text);
        stringBuilder.setSpan(new ForegroundColorSpan(color), indexBegin, indexEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return stringBuilder;
    }

    public static View getEmptyView(Context context, String text) {
        View emptyView = ((Activity) context).getLayoutInflater().inflate(R.layout.view_empty, null);
        ((TextView) emptyView.findViewById(R.id.tv_content)).setText(text);
        return emptyView;
    }

    public static String timestampToString(String timestamp) {
        Date date = new Date(Long.parseLong(timestamp));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String timestampToString(long timestamp) {
        Date date = new Date(timestamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    // isDownward是否是向下做动画的
    public static TranslateAnimation getShowAnim(boolean isDownward) {
        TranslateAnimation mShowAction = null;
        if (isDownward) {
            mShowAction = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, -1.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f);
        } else {
            mShowAction = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 1.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f);
        }
        mShowAction.setDuration(500);
        return mShowAction;
    }

    public static TranslateAnimation getHideAnim(boolean isDownward) {
        TranslateAnimation mHiddenAction = null;
        if (isDownward) {
            mHiddenAction = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 1.0f);
        } else {
            mHiddenAction = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, -1.0f);
        }
        mHiddenAction.setDuration(500);
        return mHiddenAction;
    }
}
