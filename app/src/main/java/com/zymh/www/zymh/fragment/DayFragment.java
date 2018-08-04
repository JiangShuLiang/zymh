package com.zymh.www.zymh.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.adapter.UpdateAdapter;
import com.zymh.www.zymh.bean.UpdatePageData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DayFragment extends Fragment {


    private View layout;
    private TextView tvTest;
    private RecyclerView mRecyclerView;
    private UpdateAdapter updateAdapter;
    ArrayList<UpdatePageData> listUpdate = new ArrayList<>();

    public DayFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_day, container, false);
        initView(layout);
        initData();
        return layout;
    }

    private void initView(View layout) {
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateAdapter = new UpdateAdapter(getActivity(), R.layout.item_update, listUpdate);
        updateAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        updateAdapter.isFirstOnly(false);
        updateAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(getActivity(), "点击了第" + position + "个漫画", Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setAdapter(updateAdapter);
    }

    private void initData() {
        Bundle bundle = getArguments();
        ArrayList<UpdatePageData> data = bundle.getParcelableArrayList("data");
        if (data != null && data.size() != 0) {
            listUpdate.clear();
            listUpdate.addAll(data);
            updateAdapter.notifyDataSetChanged();
        } else {
            updateAdapter.setEmptyView(getActivity().getLayoutInflater().inflate(R.layout.view_empty, null));
        }
    }
}
