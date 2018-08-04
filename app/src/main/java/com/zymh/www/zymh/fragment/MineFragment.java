package com.zymh.www.zymh.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.params.ButtonParams;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.CleanUtils;
import com.zymh.www.zymh.Utils.Constants;
import com.zymh.www.zymh.Utils.PreferenceUtils;
import com.zymh.www.zymh.Utils.Utils;
import com.zymh.www.zymh.activity.ComicDetailActivity;
import com.zymh.www.zymh.activity.LoginActivity;
import com.zymh.www.zymh.activity.WebActivity;
import com.zymh.www.zymh.bean.UserInfo;
import com.zymh.www.zymh.bean.UserInfoData;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements View.OnClickListener {

    private TextView tvUsername;
    private ImageView ivAvatar;
    private Button btnSign;
    private String username;
    private String token;
    private TextView tvBalance;

    public MineFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_mine, container, false);
        initView(layout);
        initData();
        return layout;
    }

    private void initData() {
        username = PreferenceUtils.getPrefString(getActivity(), "username", "");
        token = PreferenceUtils.getPrefString(getActivity(), "token", "");
        if (!token.equals("")) {
            getUserInfo();//获取用户信息：主要是要获取余额，检查登录是否过期
        }
    }

    private void getUserInfo() {
        OkHttpUtils
                .post()
                .url(Constants.URL_GET_USER_INFO)
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("获取用户信息json：", response);
                        switch (Utils.getCode(response)) {
                            case 200://成功
                                UserInfo userInfo = new Gson().fromJson(response, UserInfo.class);
                                UserInfoData data = userInfo.getData();
                                String username = data.getUsername();
                                int svip = data.getSvip();
                                int bookCoin = data.getBookCoin();
                                tvUsername.setText(username);
                                String balance = "余额：" + bookCoin;
                                tvBalance.setText(Utils.generateColorfulText(balance, 3, balance.length(), 0xffff8EB3));

                                tvUsername.setOnClickListener(null);
                                ivAvatar.setOnClickListener(null);
                                break;
                            case 500://失败
                                Toasty.error(getActivity(), "用户信息获取失败!", Toast.LENGTH_SHORT, true).show();
                                break;
                            case -1:
                                Toasty.info(getActivity(), Utils.getMsg(response), Toast.LENGTH_SHORT, true).show();
                                tvUsername.setText("点击登录");
                                break;
                        }
                    }
                });
    }


    private void initView(View layout) {
        tvBalance = (TextView) layout.findViewById(R.id.tv_balance);
        RelativeLayout layoutBalance = (RelativeLayout) layout.findViewById(R.id.layout_balance);
        RelativeLayout layoutClear = (RelativeLayout) layout.findViewById(R.id.layout_clear_cache);
        RelativeLayout layoutLogout = (RelativeLayout) layout.findViewById(R.id.layout_logout);
        layoutLogout.setOnClickListener(this);
        ivAvatar = (ImageView) layout.findViewById(R.id.iv_avatar);
        tvUsername = (TextView) layout.findViewById(R.id.tv_username);
        btnSign = (Button) layout.findViewById(R.id.btn_sign);
        layoutBalance.setOnClickListener(this);
        layoutClear.setOnClickListener(this);
        btnSign.setOnClickListener(this);
        tvUsername.setOnClickListener(this);
        ivAvatar.setOnClickListener(this);
        if (Constants.DEBUG) {
            RelativeLayout laoutToTest = (RelativeLayout) layout.findViewById(R.id.layout_to_test);
            laoutToTest.setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_balance:
                if (!token.equals("")) {
                    Intent intent = new Intent(getActivity(), WebActivity.class);
                    intent.putExtra("title", "掌缘币充值");
                    intent.putExtra("url", Constants.URL_RECHARGE + token);
                    startActivity(intent);
                } else {
                    Toasty.warning(getActivity(), "请先登录!", Toast.LENGTH_SHORT, true).show();
                }
                break;
            case R.id.layout_clear_cache:
                showClearCacheDialog();
                break;
            case R.id.layout_to_test:
                startActivity(new Intent(getActivity(), ComicDetailActivity.class));
                break;
            case R.id.layout_logout:
                PreferenceUtils.setPrefString(getActivity(), "username", "");
                PreferenceUtils.setPrefString(getActivity(), "token", "");
                break;
            case R.id.iv_avatar:
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), Constants.REQUST_CODE_LOGIN);
                break;
            case R.id.tv_username:
                startActivityForResult(new Intent(getActivity(), LoginActivity.class), Constants.REQUST_CODE_LOGIN);
                break;
            case R.id.btn_sign:
                if (!token.equals("")) {
                    sign(token);
                } else {
                    Toasty.warning(getActivity(), "请先登录!", Toast.LENGTH_SHORT, true).show();
                }
                break;
        }
    }

    private void showClearCacheDialog() {
        new CircleDialog.Builder(getActivity())
                .setTitle("清除图片缓存")
                .setTitleColor(0xffff8EB3)
                .setSubTitleColor(0xff000000)
                .setSubTitle("确定清除图片缓存?")
                .setTextColor(0xff000000)
                .setPositive("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CleanUtils.cleanInternalCache(getActivity());
                        Toasty.success(getActivity(), "缓存清除成功!", Toast.LENGTH_SHORT, true).show();
                    }
                })
                .configPositive(new ConfigButton() {
                    @Override
                    public void onConfig(ButtonParams params) {
                        params.textColor = 0xffff8EB3;
                        params.textSize = 50;
                    }
                })
                .setNegative("取消", null)
                .configNegative(new ConfigButton() {
                    @Override
                    public void onConfig(ButtonParams params) {
                        params.textSize = 50;
                    }
                })
                .show();
    }

    private void sign(String token) {
        OkHttpUtils
                .post()
                .url(Constants.URL_SIGN)
                .addParams("token", token)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("获取邮箱验证码json：", response);
                        switch (Utils.getCode(response)) {
                            case 200://成功
                                Toasty.success(getActivity(), Utils.getMsg(response), Toast.LENGTH_SHORT, true).show();
                                break;
                            case 500://失败
                                Toasty.warning(getActivity(), Utils.getMsg(response), Toast.LENGTH_SHORT, true).show();
                                break;
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUST_CODE_LOGIN && resultCode == Constants.RESULT_CODE_LOGIN_SUCCESS) {
            username = PreferenceUtils.getPrefString(getActivity(), "username", "");
            token = PreferenceUtils.getPrefString(getActivity(), "token", "");
            tvUsername.setText(username);
            tvUsername.setOnClickListener(null);
            ivAvatar.setOnClickListener(null);
            getUserInfo();//获取用户信息，主要是余额
        }
    }
}
