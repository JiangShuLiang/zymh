package com.zymh.www.zymh.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.Constants;
import com.zymh.www.zymh.Utils.PreferenceUtils;
import com.zymh.www.zymh.Utils.Utils;
import com.zymh.www.zymh.adapter.CollectionAdapter;
import com.zymh.www.zymh.bean.UpdatePageData;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCollectionFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private CollectionAdapter collectionAdapter;
    private String token;
    ArrayList<UpdatePageData> listData = new ArrayList<>();

    public MyCollectionFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_my_collection, container, false);
        initView(layout);
        initData();
        return layout;
    }


    private void initData() {
        token = PreferenceUtils.getPrefString(getActivity(), "token", "");
        if (!token.equals("")){
            getCollectionData();
        }
    }

    private void getCollectionData() {
        OkHttpUtils
                .post()
                .url(Constants.URL_GET_COLLECTION_DATA)
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("收藏json：", response);
                        switch (Utils.getCode(response)) {
                            case 200://成功
                                break;
                            case 500://失败
                                collectionAdapter.setEmptyView(getActivity().getLayoutInflater().inflate(R.layout.view_empty, null));
                                break;
                        }
                    }
                });
    }

    private void initView(View layout) {
        initRecyclerView(layout);
    }

    private void initRecyclerView(View layout) {
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        collectionAdapter = new CollectionAdapter(getActivity(), R.layout.item_home_body, listData);
        collectionAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        collectionAdapter.isFirstOnly(false);
        collectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                if (view.getId() == R.id.iv_delete) {
//                }
            }
        });
        mRecyclerView.setAdapter(collectionAdapter);
    }


}
