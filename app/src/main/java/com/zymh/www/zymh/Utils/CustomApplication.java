package com.zymh.www.zymh.Utils;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zymh.www.zymh.R;

import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import okhttp3.OkHttpClient;


public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initOKHttpUtil();
        configToasty();
    }

    private void initOKHttpUtil() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    private void configToasty() {
        Toasty.Config.getInstance().setErrorColor(getResources().getColor(R.color.red)).
                setSuccessColor(getResources().getColor(R.color.colorPrimary))
                .apply();
//    .setInfoColor(@ColorInt int infoColor) // optional
//    .setWarningColor(@ColorInt int warningColor) // optional
//    .setTextColor(@ColorInt int textColor) // optional
//    .tintIcon(boolean tintIcon) // optional (apply textColor also to the icon)
//    .setToastTypeface(@NonNull Typeface typeface) // optional
//    .setTextSize(int sizeInSp) // optional
    }
}
