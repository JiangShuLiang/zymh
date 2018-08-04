package com.zymh.www.zymh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.uuch.adlibrary.AdConstant;
import com.uuch.adlibrary.AdManager;
import com.uuch.adlibrary.bean.AdInfo;
import com.uuch.adlibrary.transformer.DepthPageTransformer;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.Constants;
import com.zymh.www.zymh.Utils.Utils;
import com.zymh.www.zymh.fragment.BooksFragment;
import com.zymh.www.zymh.fragment.HomeFragment;
import com.zymh.www.zymh.fragment.MineFragment;
import com.zymh.www.zymh.fragment.UpdateFragment;
import com.zymh.www.zymh.views.AlphaIndicator;
import com.zymh.www.zymh.views.AlphaView;

import java.util.ArrayList;

import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyAdapter myAdapter;
    private AlphaView itemHome;
    private AlphaView itemUpdate;
    private AlphaView itemBooks;
    private AlphaView itemMine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        getAppMsg();
        showAppMsg("测试系统消息！");
    }

    private void getAppMsg() {
        OkHttpUtils
                .post()
                .url(Constants.URL_GET_APP_MSG)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("app消息json：", response);
                        if (Utils.getCode(response) == 200) {
                            String msg = Utils.getMsg(response);
                            showAppMsg(msg);
                        }
                    }
                });
    }

    private void showAppMsg(String msg) {
        ArrayList<AdInfo> msgList = new ArrayList<>();
        AdInfo adInfo = new AdInfo();
//                            adInfo.setActivityImgId(getResources().getd);
        adInfo.setTitle(msg);
        msgList.add(adInfo);

        AdManager adManager = new AdManager(MainActivity.this, msgList);
        adManager.setOverScreen(true).setPageTransformer(new DepthPageTransformer());
        adManager.showAdDialog(AdConstant.ANIM_DOWN_TO_UP);
    }

    private void initView() {
        itemHome = (AlphaView) findViewById(R.id.item_home);
        itemUpdate = (AlphaView) findViewById(R.id.item_update);
        itemBooks = (AlphaView) findViewById(R.id.item_books);
        itemMine = (AlphaView) findViewById(R.id.item_mine);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(3);
        FragmentManager fm = getSupportFragmentManager();
        myAdapter = new MyAdapter(fm);
        viewPager.setAdapter(myAdapter);
        AlphaIndicator alphaIndicator = (AlphaIndicator) findViewById(R.id.alphaIndicator);
        alphaIndicator.setViewPager(viewPager);
    }

    class MyAdapter extends FragmentPagerAdapter {
        private Fragment mCurrentFragment;

        public MyAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            switch (arg0) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new UpdateFragment();
                case 2:
                    return new BooksFragment();
                case 3:
                    return new MineFragment();
            }
            return new HomeFragment();
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            mCurrentFragment = (Fragment) object;
            super.setPrimaryItem(container, position, object);
        }

        public Fragment getCurrentFragment() {
            return mCurrentFragment;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
