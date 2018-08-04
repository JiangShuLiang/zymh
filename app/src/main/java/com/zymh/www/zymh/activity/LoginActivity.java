package com.zymh.www.zymh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.Constants;
import com.zymh.www.zymh.Utils.PreferenceUtils;
import com.zymh.www.zymh.Utils.Utils;

import cc.cloudist.acplibrary.ACProgressFlower;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etPwd;
    private ACProgressFlower loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        etUsername = (EditText) findViewById(R.id.et_username);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        TextView tvForget = (TextView) findViewById(R.id.tv_forget);
        TextView tvRegister = (TextView) findViewById(R.id.tv_register);
        Button btnLogin = (Button) findViewById(R.id.btn_login);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        tvForget.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_login:
                if (!TextUtils.isEmpty(etUsername.getText()) && !TextUtils.isEmpty(etPwd.getText())) {
                    login();
                } else {
                    Toasty.error(this, "你还有未填的信息!", Toast.LENGTH_SHORT, true).show();
                }
                break;
            case R.id.tv_forget:
                startActivity(new Intent(this, ForgetActivity.class));
                break;
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }

    }

    private void login() {
        loadingDialog = Utils.showLoadingDialog(this, "正在登录");
        OkHttpUtils.
                post()
                .url(Constants.URL_LOGIN)
                .addParams("username", etUsername.getText().toString())
                .addParams("password", etPwd.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        loadingDialog.dismiss();
                        Log.d("登录json：", response);
                        switch (Utils.getCode(response)) {
                            case 200://成功
                                Toasty.success(LoginActivity.this, "登录成功!", Toast.LENGTH_SHORT, true).show();
                                String token = Utils.getData(response);
                                String username = etUsername.getText().toString();
                                PreferenceUtils.setPrefString(LoginActivity.this, "token", token);
                                PreferenceUtils.setPrefString(LoginActivity.this, "username", username);
                                setResult(Constants.RESULT_CODE_LOGIN_SUCCESS);
                                finish();
                                break;
                            case 500://失败
                                Toasty.error(LoginActivity.this, Utils.getMsg(response), Toast.LENGTH_SHORT, true).show();
                                break;
                        }
                    }
                });
    }
}
