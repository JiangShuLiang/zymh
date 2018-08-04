package com.zymh.www.zymh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.Constants;
import com.zymh.www.zymh.Utils.Utils;
import com.zymh.www.zymh.adapter.SearchAdapter;
import com.zymh.www.zymh.bean.HotWord;
import com.zymh.www.zymh.bean.SearchResult;
import com.zymh.www.zymh.bean.SearchResultData;

import java.util.ArrayList;

import cc.cloudist.acplibrary.ACProgressFlower;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etEmail;
    private EditText etAuth;
    private EditText etPwd;
    private Button btnRegister;
    private Button btnRest;
    private TagFlowLayout flowLayout;
    private ArrayList<String> listHotWord = new ArrayList<String>();
    private ArrayList<SearchResultData> listSearchResult = new ArrayList<>();
    private EditText etBookName;
    private TextView tvCancel;
    private LinearLayout layoutHotWord;
    private ACProgressFlower loadingDialog;
    private RecyclerView mRecyclerView;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();
    }

    private void initData() {
        getHotWordData();

    }

    private void getHotWordData() {
        post()
                .url(Constants.URL_GET_HOT_WORD)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("热门搜词json：", response);
                        switch (Utils.getCode(response)) {
                            case 200://成功
                                setHotWord(response);
                                break;
                            case 500://失败
                                Toasty.error(SearchActivity.this, "热门搜词获取失败!", Toast.LENGTH_SHORT, true).show();
                                break;
                        }
                    }
                });
    }

    private void setHotWord(String response) {
        String[] data = new Gson().fromJson(response, HotWord.class).getData();
        for (int i = 0; i < data.length; i++) {
            listHotWord.add(data[i]);
        }
        flowLayout.setAdapter(new TagAdapter<String>(listHotWord) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_hot_word, flowLayout, false);
                tv.setText(s);
                return tv;
            }
        });
    }

    private void initView() {
        layoutHotWord = (LinearLayout) findViewById(R.id.layout_hot_word);
        etBookName = (EditText) findViewById(R.id.et_book_name);
        TextView tvSearch = (TextView) findViewById(R.id.tv_search);
        tvSearch.setOnClickListener(SearchActivity.this);
        etBookName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    layoutHotWord.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        flowLayout = (TagFlowLayout) findViewById(R.id.flowLayout);
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String hotWord = listHotWord.get(position);
                layoutHotWord.setVisibility(View.GONE);
                etBookName.setText(hotWord);
                search(hotWord);
                return true;
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter(this, R.layout.item_search, listSearchResult);
        searchAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        searchAdapter.isFirstOnly(false);
        searchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SearchResultData searchResultData = listSearchResult.get(position);
                int bookId = searchResultData.getId();
                Intent intent = new Intent(SearchActivity.this, ComicDetailActivity.class);
                intent.putExtra("bookId", bookId + "");
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(searchAdapter);
    }

    private void search(String searchText) {
        loadingDialog = Utils.showLoadingDialog(SearchActivity.this, "正在搜索");
        OkHttpUtils
                .post()
                .url(Constants.URL_SEARCH)
                .addParams("bookName", searchText)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        loadingDialog.dismiss();
                        Log.d("搜索json：", response);
                        switch (Utils.getCode(response)) {
                            case 200://成功
                                SearchResult searchResult = new Gson().fromJson(response, SearchResult.class);
                                SearchResultData[] data = searchResult.getData();
                                listSearchResult.clear();
                                if (data.length == 0) {
                                    searchAdapter.notifyDataSetChanged();
                                    searchAdapter.setEmptyView(Utils.getEmptyView(SearchActivity.this, "抱歉,搜索不到相关漫画,换个词试试吧!"));
                                    return;
                                }
                                for (int i = 0; i < data.length; i++) {
                                    listSearchResult.add(data[i]);
                                }
                                searchAdapter.notifyDataSetChanged();
                                break;
                            case 500://失败
                                Toasty.error(SearchActivity.this, Utils.getMsg(response), Toast.LENGTH_SHORT, true).show();
                                break;
                        }
                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:
                if (!TextUtils.isEmpty(etBookName.getText())) {
                    search(etBookName.getText().toString());
                } else {
                    Toasty.success(SearchActivity.this, "请先输入书名", Toast.LENGTH_SHORT, true).show();
                }
                break;
        }
    }
}
