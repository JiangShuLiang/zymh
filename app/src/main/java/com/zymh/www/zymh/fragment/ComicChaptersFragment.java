package com.zymh.www.zymh.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.PreferenceUtils;
import com.zymh.www.zymh.activity.ComicReadActivity;
import com.zymh.www.zymh.adapter.ChapterAdapter;
import com.zymh.www.zymh.bean.ComicDetailDataChapters;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComicChaptersFragment extends Fragment implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private ArrayList<ComicDetailDataChapters> listChapters;
    private ChapterAdapter chapterAdapter;
    private TextView tvUpdateInfo;
    private LinearLayout layoutOrder;
    private String lastChapter;
    private TextView tvOrder;
    private ImageView ivOrder;
    private boolean isAsc = true;
    private String bookId;
    //    DESC：倒序/降序(从大到小排序)
//   ACS：正序/升序(从小到大排序)

    public ComicChaptersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_comic_chapters, container, false);
        initView(layout);
        initData();
        return layout;
    }

    private void initView(View layout) {
        tvUpdateInfo = (TextView) layout.findViewById(R.id.tv_update_info);
        layoutOrder = (LinearLayout) layout.findViewById(R.id.layout_order);
        tvOrder = (TextView) layout.findViewById(R.id.tv_order);
        ivOrder = (ImageView) layout.findViewById(R.id.iv_order);
        layoutOrder.setOnClickListener(this);
        initRecyclerView(layout);
    }

    private void initRecyclerView(View layout) {
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        chapterAdapter = new ChapterAdapter(getActivity(), R.layout.item_chapter, listChapters);
        chapterAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        chapterAdapter.isFirstOnly(false);
        chapterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ComicDetailDataChapters item = listChapters.get(position);
                int costCoin = item.getCostCoin();
                if (costCoin == 0 || costCoin == -1 || costCoin == -2) {//0免费，-1限免，-2已购
                    toReadComic(item);// 直接观看漫画
                } else {
                    // token不为空，有登录，提示用户跳转到购买界面
                    if (PreferenceUtils.getPrefString(getActivity(), "token", "").equals("")) {
                        // JSL: 2018/8/3 弹出dialog提示用户去购买 
                    } else {// token为空，没登录，提示用户跳转到登录界面
                        // JSL: 2018/8/3 弹出dialog提示用户去登录 
                    }
                }
            }


        });
        mRecyclerView.setAdapter(chapterAdapter);
    }

    private void toReadComic(ComicDetailDataChapters item) {
        Intent intent = new Intent(getActivity(), ComicReadActivity.class);
        intent.putExtra("bookId", bookId);
        intent.putExtra("chapterIndex", item.getChapterIndex());
        startActivity(intent);
    }

    private void initData() {
        Bundle bundle = getArguments();
        listChapters = bundle.getParcelableArrayList("data");
        lastChapter = bundle.getString("lastChapter");
        bookId = bundle.getString("bookId");
        tvUpdateInfo.setText("更新至第" + lastChapter + "章");
        chapterAdapter.setNewData(listChapters);
//        chapterAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layout_order) {
            //    DESC：倒序/降序(从大到小排序)
            //   ACS：正序/升序(从小到大排序)
            isAsc = !isAsc;
            if (isAsc) {
                tvOrder.setText("正序");
                Glide.with(getActivity()).load(R.mipmap.ic_asc).into(ivOrder);
                Collections.reverse(listChapters);
                chapterAdapter.setNewData(listChapters);
            } else {
                tvOrder.setText("倒序");
                Glide.with(getActivity()).load(R.mipmap.ic_desc).into(ivOrder);
                Collections.reverse(listChapters);
                chapterAdapter.setNewData(listChapters);
            }
        }
    }
}
