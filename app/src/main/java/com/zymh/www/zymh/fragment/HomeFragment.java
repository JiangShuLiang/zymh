package com.zymh.www.zymh.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.Constants;
import com.zymh.www.zymh.Utils.Utils;
import com.zymh.www.zymh.activity.ComicDetailActivity;
import com.zymh.www.zymh.activity.ComicGroupActivity;
import com.zymh.www.zymh.activity.SearchActivity;
import com.zymh.www.zymh.adapter.HomeAdapter;
import com.zymh.www.zymh.bean.HomePage;
import com.zymh.www.zymh.bean.HomePageData;
import com.zymh.www.zymh.bean.HomePageDataBody;
import com.zymh.www.zymh.bean.HomePageDataBodyBooks;
import com.zymh.www.zymh.bean.HomePageDataHeader;
import com.zymh.www.zymh.bean.SectionBean;
import com.zymh.www.zymh.views.image_loader.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

import cc.cloudist.acplibrary.ACProgressFlower;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeAdapter homeAdapter;
    ArrayList<SectionBean> listData = new ArrayList<>();
    private RefreshLayout refreshLayout;
    private RecyclerView mRecyclerView;
    private ACProgressFlower loadingDialog;
    private HomePageDataHeader[] header;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home, container, false);
        initView(layout);
        initData();
        return layout;
    }

    private void initData() {
        loadingDialog = Utils.showLoadingDialog(getActivity(), "正在获取数据");
        getHomePageData();
    }

    private void getHomePageData() {
        Log.e("首页URL == ", Constants.URL_GET_HOME_PAGE_DATA);
        OkHttpUtils
                .post()
                .url(Constants.URL_GET_HOME_PAGE_DATA)
                .addParams("size", "6")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("首页json：", response);
                        switch (Utils.getCode(response)) {
                            case 200://成功
                                HomePage homePage = new Gson().fromJson(response, HomePage.class);
                                HomePageData data = homePage.getData();
                                header = data.getHeader();
                                // 处理轮播图数据
                                setBannerData(header);
                                // 处理列表数据
                                setListData(data);
                                loadingDialog.dismiss();
                                break;
                            case 500://失败
                                loadingDialog.dismiss();
                                Toasty.error(getActivity(), "首页数据获取失败!", Toast.LENGTH_SHORT, true).show();
                                break;
                        }
                    }
                });
    }

    private void setBannerData(final HomePageDataHeader[] header) {
        final View viewBanner = getActivity().getLayoutInflater().inflate(R.layout.view_banner, null, false);
        ImageView ivSearch = (ImageView) viewBanner.findViewById(R.id.iv_search);
        ivSearch.setOnClickListener(this);
        Banner banner = (Banner) viewBanner.findViewById(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置banner样式
        banner.setImageLoader(new GlideImageLoader()); //设置图片加载器
        banner.setBannerAnimation(Transformer.Default);//设置banner动画效果
        banner.isAutoPlay(true);//设置自动轮播，默认为true
        banner.setDelayTime(1500);//设置轮播时间
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置（当banner模式中有指示器时）
        List<String> imagesUrl = new ArrayList<>(); //图片集合
        List<String> titles = new ArrayList<>();//标题集合（当banner样式有显示title时）
        for (int i = 0; i < header.length; i++) {
            String title = header[i].getTitle();
            String picUrl = Constants.HOST_PIC + header[i].getImg();
            imagesUrl.add(picUrl);
            titles.add(title);
        }
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                HomePageDataHeader item = header[position];
                if (item.getType() > 0) {//如果type>0则这个type就是bookId
                    int bookId = item.getType();
                    Intent intent = new Intent(getActivity(), ComicDetailActivity.class);
                    intent.putExtra("bookId", bookId + "");
                    startActivity(intent);
                }
            }
        });
        banner.setImages(imagesUrl);
        banner.setBannerTitles(titles);
        banner.start();//banner设置方法全部调用完毕时最后调用
        homeAdapter.addHeaderView(viewBanner);
    }

    private void setListData(HomePageData data) {
        HomePageDataBody[] body = data.getBody();
        for (int i = 0; i < body.length; i++) {
            HomePageDataBody group = body[i];
            String title = group.getTitle();
            boolean more = group.getMore();
            String sectionId = group.getSectionId() + "";
            HomePageDataBodyBooks[] books = group.getBooks();
            listData.add(new SectionBean(true, title, sectionId, more));
            for (int j = 0; j < books.length; j++) {
                listData.add(new SectionBean(books[j]));
            }
        }
        homeAdapter.notifyDataSetChanged();
    }

    private void initView(View layout) {
        initRecyclerView(layout);
//        initRefreshLayout(layout);
    }

    private void initRefreshLayout(View layout) {
//        refreshLayout = (RefreshLayout) layout.findViewById(R.id.refreshLayout);
//        refreshLayout.setEnableRefresh(false);
//        refreshLayout.setEnableLoadmore(false);
//        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//            }
//        });
    }

    private void initRecyclerView(View layout) {
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        homeAdapter = new HomeAdapter(getActivity(), R.layout.item_home_body, R.layout.item_home_head, listData);
        homeAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        homeAdapter.isFirstOnly(false);
        homeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_more) {
                    SectionBean data = listData.get(position);
                    String title = data.getHeader();
                    String sectionId = data.getSectionId();
                    Intent intent = new Intent(getActivity(), ComicGroupActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("sectionId", sectionId);
                    startActivity(intent);
                }
            }
        });
        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SectionBean sectionBean = listData.get(position);
                int bookId = sectionBean.t.getId();
                Intent intent = new Intent(getActivity(), ComicDetailActivity.class);
                intent.putExtra("bookId", bookId + "");
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(homeAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_search) {
            startActivity(new Intent(getActivity(), SearchActivity.class));
        }
    }
}
