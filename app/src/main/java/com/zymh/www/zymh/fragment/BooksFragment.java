package com.zymh.www.zymh.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.zymh.www.zymh.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BooksFragment extends Fragment {


    private FragmentPagerItemAdapter adapter;

    public BooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_books, container, false);
        adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getActivity())
                .add("阅读历史", ReadHistoryFragment.class)
                .add("我的收藏",MyCollectionFragment.class)
                .create());
        ViewPager viewPager = (ViewPager) layout.findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        SmartTabLayout viewPagerTab = (SmartTabLayout) layout.findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
        return layout;
    }
}
