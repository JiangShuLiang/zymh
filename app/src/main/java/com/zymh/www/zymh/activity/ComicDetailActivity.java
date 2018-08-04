package com.zymh.www.zymh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.Constants;
import com.zymh.www.zymh.Utils.PreferenceUtils;
import com.zymh.www.zymh.Utils.Utils;
import com.zymh.www.zymh.bean.ComicDetail;
import com.zymh.www.zymh.bean.ComicDetailDataBook;
import com.zymh.www.zymh.bean.ComicDetailDataChapters;
import com.zymh.www.zymh.fragment.ComicChaptersFragment;
import com.zymh.www.zymh.fragment.ComicInfoFragment;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class ComicDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private CoordinatorLayout parentView;
    private Toolbar mToolbar;
    private FragmentPagerItemAdapter adapter;
    private String bookId;
    private TextView tvBookName;
    private TextView tvHit;
    private ImageView ivCover;
    private TextView tvRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);
        ImmersionBar.with(this).init();
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        bookId = intent.getStringExtra("bookId");
        String token = PreferenceUtils.getPrefString(this, "token", "");
        OkHttpUtils
                .post()
                .url(Constants.URL_GET_COMIC_DETAIL)
                .addParams("bookId", bookId)
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("漫画书详情json：", response);
                        switch (Utils.getCode(response)) {
                            case 200://成功
                                ComicDetail comicDetail = new Gson().fromJson(response, ComicDetail.class);
                                ComicDetailDataBook comicInfo = comicDetail.getData().getBook();
                                ComicDetailDataChapters[] comicChapters = comicDetail.getData().getChapters();

                                tvBookName.setText(comicInfo.getBookName());
                                tvHit.setText("点击:" + comicInfo.getHits());
                                Glide.with(ComicDetailActivity.this)
                                        .load(Constants.HOST_PIC + comicInfo.getBookIcon())
                                        .centerCrop()
                                        .placeholder(R.mipmap.ic_app_icon)
                                        .into(ivCover);
                                mCollapsingToolbarLayout.setTitle(comicInfo.getBookName());


                                initViewPager(comicDetail);
                                break;
                            case 500://失败
                                Toasty.error(ComicDetailActivity.this, Utils.getMsg(response), Toast.LENGTH_SHORT, true).show();
                                break;
                        }
                    }
                });
    }

    private void initView() {
        tvBookName = (TextView) findViewById(R.id.tv_book_name);
        tvHit = (TextView) findViewById(R.id.tv_hit);
        tvRead = (TextView) findViewById(R.id.tv_read);
        ivCover = (ImageView) findViewById(R.id.iv_cover);
        tvRead.setOnClickListener(this);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mCollapsingToolbarLayout.setTitle(getString(R.string.app_name));
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        initViewPager();
    }

    private void initViewPager(ComicDetail data) {
        // 详情bundle
        Bundle bundleInfo = new Bundle();
        bundleInfo.putParcelable("data", data.getData().getBook());
        // 选集bundle
        Bundle bundleChapters = new Bundle();
        ComicDetailDataChapters[] chapters = data.getData().getChapters();
        ArrayList<ComicDetailDataChapters> listChapters = new ArrayList<>();
        for (int i = 0; i < chapters.length; i++) {
            listChapters.add(chapters[i]);
        }
        bundleChapters.putParcelableArrayList("data", listChapters);
        bundleChapters.putString("lastChapter", data.getData().getBook().getLastChapter() + "");
        bundleChapters.putString("bookId", data.getData().getBook().getId() + "");

        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("详情", ComicInfoFragment.class, bundleInfo)
                .add("选集", ComicChaptersFragment.class, bundleChapters)
                .create());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_collection,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.menu_collection:
                Toast.makeText(this, "添加收藏啦", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.tv_read){
            Intent intent = new Intent(this, ComicReadActivity.class);
            intent.putExtra("bookId", bookId);
            intent.putExtra("chapterIndex", 1);
            startActivity(intent);
        }
    }
}
