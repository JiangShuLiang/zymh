package com.zymh.www.zymh.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.Constants;
import com.zymh.www.zymh.Utils.Utils;
import com.zymh.www.zymh.bean.UpdatePage;
import com.zymh.www.zymh.bean.UpdatePageData;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends Fragment {

    //分别对应周一到周日的bundle和ArrayList
    Bundle bundle1 = new Bundle();
    Bundle bundle2 = new Bundle();
    Bundle bundle3 = new Bundle();
    Bundle bundle4 = new Bundle();
    Bundle bundle5 = new Bundle();
    Bundle bundle6 = new Bundle();
    Bundle bundle7 = new Bundle();
    final ArrayList<UpdatePageData> list1 = new ArrayList<>();
    final ArrayList<UpdatePageData> list2 = new ArrayList<>();
    final ArrayList<UpdatePageData> list3 = new ArrayList<>();
    final ArrayList<UpdatePageData> list4 = new ArrayList<>();
    final ArrayList<UpdatePageData> list5 = new ArrayList<>();
    final ArrayList<UpdatePageData> list6 = new ArrayList<>();
    final ArrayList<UpdatePageData> list7 = new ArrayList<>();
    private View layout;


    public UpdateFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_update, container, false);
        initData();
        initView(layout);
        return layout;
    }

    private void initView(View layout) {
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getActivity())
                .add("周日", DayFragment.class, bundle7)
                .add("周一", DayFragment.class, bundle1)
                .add("周二", DayFragment.class, bundle2)
                .add("周三", DayFragment.class, bundle3)
                .add("周四", DayFragment.class, bundle4)
                .add("周五", DayFragment.class, bundle5)
                .add("周六", DayFragment.class, bundle6)
                .create());
        ViewPager viewPager = (ViewPager) layout.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        // 设置当前tab
        int day = Utils.getCurrentDayOfWeek() - 1;//day为实际周几
        if (day != 7) {
            viewPager.setCurrentItem(Utils.getCurrentDayOfWeek() - 1);//当天周几，当前tab就切换到周几
        }
        SmartTabLayout viewPagerTab = (SmartTabLayout) layout.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
    }

    private void initData() {
        OkHttpUtils
                .post()
                .url(Constants.URL_GET_UPDATE_PAGE_DATA)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("更新页json：", response);
                        if (Utils.getCode(response) == 200) {
                            UpdatePage updatePage = new Gson().fromJson(response, UpdatePage.class);
                            UpdatePageData[] data = updatePage.getData();
                            for (int i = 0; i < data.length; i++) {
                                UpdatePageData item = data[i];
                                String chapterUpdate = item.getChapterUpdate();
                                Bundle bundle = new Bundle();
                                if (chapterUpdate.contains("每天") || chapterUpdate.contains("每周日")) {
                                    list7.add(item);
                                }
                                switch (chapterUpdate) {
                                    case "每周一":
                                        list1.add(item);
                                        break;
                                    case "每周二":
                                        list2.add(item);
                                        break;
                                    case "每周三":
                                        list3.add(item);
                                        break;
                                    case "每周四":
                                        list4.add(item);
                                        break;
                                    case "每周五":
                                        list5.add(item);
                                        break;
                                    case "每周六":
                                        list6.add(item);
                                        break;
                                }
                            }
                            bundle1.putParcelableArrayList("data", list1);
                            bundle2.putParcelableArrayList("data", list2);
                            bundle3.putParcelableArrayList("data", list3);
                            bundle4.putParcelableArrayList("data", list4);
                            bundle5.putParcelableArrayList("data", list5);
                            bundle6.putParcelableArrayList("data", list6);
                            bundle7.putParcelableArrayList("data", list7);
//                            initView(layout);
                        } else {
                            Toasty.error(getActivity(), "更新页数据获取失败!", Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
    }

}
