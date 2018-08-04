package com.zymh.www.zymh.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zymh.www.zymh.R;
import com.zymh.www.zymh.bean.ComicDetailDataBook;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComicInfoFragment extends Fragment {


    private TextView tvDescription;
    private TextView tvCartoonist;

    public ComicInfoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_comic_info, container, false);
        tvDescription = (TextView) layout.findViewById(R.id.tv_description);
        tvCartoonist = (TextView) layout.findViewById(R.id.tv_cartoonist);

        Bundle bundle = getArguments();
        ComicDetailDataBook info = bundle.getParcelable("data");
        tvCartoonist.setText("作者："+info.getAuthorName());
        tvDescription.setText(info.getSummary());

        return layout;
    }

}
