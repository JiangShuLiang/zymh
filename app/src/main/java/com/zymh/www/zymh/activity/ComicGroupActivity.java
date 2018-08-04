package com.zymh.www.zymh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.Constants;
import com.zymh.www.zymh.Utils.Utils;
import com.zymh.www.zymh.adapter.ComicGroupAdapter;
import com.zymh.www.zymh.bean.ComicGroup;
import com.zymh.www.zymh.bean.ComicGroupData;
import com.zymh.www.zymh.bean.ComicGroupDataBooks;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressFlower;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class ComicGroupActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<ComicGroupDataBooks> listComicGroup = new ArrayList<>();
    private ACProgressFlower loadingDialog;
    private RecyclerView mRecyclerView;
    private TextView tvTitle;
    private int currentPage = 1;
    private String title;
    private String sectionId;
    private RefreshLayout refreshLayout;
    private ComicGroupAdapter comicGroupAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_group);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        sectionId = intent.getStringExtra("sectionId");
        tvTitle.setText(title);
        getComicGroupData(currentPage);
    }

    private void getComicGroupData(int pageNo) {
        OkHttpUtils.
                post()
                .url(Constants.URL_GET_COMIC_GROUP)
                .addParams("sectionId", sectionId)
                .addParams("size", "10")
                .addParams("index", pageNo + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("漫画组json：", response);
                        refreshLayout.finishLoadmore();
                        switch (Utils.getCode(response)) {
                            case 200://成功
                                ComicGroup comicGroup = new Gson().fromJson(response, ComicGroup.class);
                                ComicGroupData data = comicGroup.getData();
                                ComicGroupDataBooks[] books = data.getBooks();
                                if (books.length==0){
                                    Toasty.success(ComicGroupActivity.this, "暂无更多漫画", Toast.LENGTH_SHORT, true).show();
                                    return;
                                }
                                for (int i = 0; i < books.length; i++) {
                                    listComicGroup.add(books[i]);
                                }
                                currentPage++;
                                comicGroupAdapter.notifyDataSetChanged();
                                break;
                            case 500://失败
                                Toasty.error(ComicGroupActivity.this, "漫画数据获取失败!", Toast.LENGTH_SHORT, true).show();
                                break;
                        }
                    }
                });
    }

    private void initView() {
        tvTitle = (TextView) findViewById(R.id.tv_title);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(ComicGroupActivity.this);
        initRecyclerView();
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                getComicGroupData(currentPage);
            }
        });
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        comicGroupAdapter = new ComicGroupAdapter(this, R.layout.item_update, listComicGroup);
        comicGroupAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        comicGroupAdapter.isFirstOnly(false);
        comicGroupAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ComicGroupDataBooks comicGroupDataBooks = listComicGroup.get(position);
                int bookId = comicGroupDataBooks.getId();
                Intent intent = new Intent(ComicGroupActivity.this, ComicDetailActivity.class);
                intent.putExtra("bookId", bookId + "");
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(comicGroupAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
