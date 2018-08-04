package com.zymh.www.zymh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.Constants;
import com.zymh.www.zymh.Utils.PreferenceUtils;
import com.zymh.www.zymh.Utils.Utils;
import com.zymh.www.zymh.adapter.ComicPagesAdapter;
import com.zymh.www.zymh.bean.ComicPages;
import com.zymh.www.zymh.bean.ComicPagesDataPages;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressFlower;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class ComicReadActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<ComicPagesDataPages> listPages = new ArrayList<>();
    private ACProgressFlower loadingDialog;
    private RecyclerView mRecyclerView;
    private TextView tvTitle;
    private String title;
    private String sectionId;
    private int chapterIndex;
    private String bookId;
    private String token;
    private RelativeLayout layoutBottom;
    private RelativeLayout layoutTop;
    private ImageView ivNextChapter;
    private ImageView ivPreChapter;
    private ComicPagesAdapter comicPagesAdapter;
    private int chapterIndexOld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_read);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
//        title = intent.getStringExtra("title");
        token = PreferenceUtils.getPrefString(this, "token", "");
        bookId = intent.getStringExtra("bookId");
        chapterIndex = intent.getIntExtra("chapterIndex", 1);
        tvTitle.setText("第" + chapterIndex + "章");
        getComicPagesData();
    }

    private void getComicPagesData() {
        OkHttpUtils.
                post()
                .url(Constants.URL_GET_COMIC_PAGES)
                .addParams("token", token)
                .addParams("bookId", bookId)
                .addParams("chapterIndex", chapterIndex + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("漫画页json：", response);
                        switch (Utils.getCode(response)) {
                            case 200://成功
                                ComicPages comicPages = new Gson().fromJson(response, ComicPages.class);
                                ComicPagesDataPages[] pages = comicPages.getData().getPages();
                                listPages.clear();
                                tvTitle.setText("第"+chapterIndex+"章");
                                for (int i = 0; i < pages.length; i++) {
                                    listPages.add(pages[i]);
                                }
                                comicPagesAdapter.notifyDataSetChanged();
                                break;
                            case 500://失败
                                chapterIndex = chapterIndexOld;//恢复chapterIndex
                                Toasty.error(ComicReadActivity.this, "漫画数据获取失败!", Toast.LENGTH_SHORT, true).show();
                                break;
                            case -1:
                                chapterIndex = chapterIndexOld;//恢复chapterIndex
                                Toasty.error(ComicReadActivity.this, Utils.getMsg(response), Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        ivPreChapter = (ImageView) findViewById(R.id.iv_pre_chapter);
        ivNextChapter = (ImageView) findViewById(R.id.iv_next_chapter);
        layoutTop = (RelativeLayout) findViewById(R.id.layout_top);
        layoutBottom = (RelativeLayout) findViewById(R.id.layout_botttom);
        ivPreChapter.setOnClickListener(ComicReadActivity.this);
        ivNextChapter.setOnClickListener(ComicReadActivity.this);
        ivBack.setOnClickListener(ComicReadActivity.this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // RecyclerView滑动时，上下两个bar隐藏
                if (newState == 1) {//newState=1表示此时有在滑动
                    if (layoutTop.getVisibility() == View.VISIBLE) {
                        setBarVisibility(false);
                    }
                }


                // 一章漫画看完（即滚动到最底部）就把上下两个bar显示出来
                LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = recyclerView.getAdapter().getItemCount();
                int lastVisibleItemPosition = lm.findLastVisibleItemPosition();
                int visibleItemCount = recyclerView.getChildCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition == totalItemCount - 1
                        && visibleItemCount > 0) {
                    setBarVisibility(true);
                }
            }
        });

        comicPagesAdapter = new ComicPagesAdapter(this, R.layout.item_page, listPages);
        comicPagesAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        comicPagesAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (layoutTop.getVisibility() == View.VISIBLE) {
                    setBarVisibility(false);
                } else {
                    setBarVisibility(true);
                }
            }
        });
        mRecyclerView.setAdapter(comicPagesAdapter);
    }

    // 设置上下两条bar的显示和隐藏
    private void setBarVisibility(boolean toShow) {
        if (toShow) {
            layoutTop.startAnimation(Utils.getShowAnim(true));
            layoutTop.setVisibility(View.VISIBLE);
            layoutBottom.startAnimation(Utils.getShowAnim(false));
            layoutBottom.setVisibility(View.VISIBLE);
        } else {
            layoutTop.startAnimation(Utils.getHideAnim(false));
            layoutTop.setVisibility(View.GONE);
            layoutBottom.startAnimation(Utils.getHideAnim(true));
            layoutBottom.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_pre_chapter:
                if (chapterIndex == 1) {
                    Toasty.warning(ComicReadActivity.this, "当前已经是第1章了", Toast.LENGTH_SHORT, true).show();
                } else {
                    chapterIndexOld = chapterIndex;
                    chapterIndex = chapterIndex - 1;
                    getComicPagesData();
                }
                break;
            case R.id.iv_next_chapter:
                chapterIndexOld = chapterIndex;
                chapterIndex = chapterIndex + 1;
                getComicPagesData();
                break;
        }
    }
}
