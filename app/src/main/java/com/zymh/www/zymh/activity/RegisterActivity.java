package com.zymh.www.zymh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zymh.www.zymh.R;
import com.zymh.www.zymh.Utils.Constants;
import com.zymh.www.zymh.Utils.RegexUtils;
import com.zymh.www.zymh.Utils.Utils;

import cc.cloudist.acplibrary.ACProgressFlower;
import es.dmoral.toasty.Toasty;
import okhttp3.Call;

import static com.zhy.http.okhttp.OkHttpUtils.post;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etEmail;
//    private EditText etIdentifyingCode;
//    private TextView tvGetCode;
    private EditText etPwd;
    private Button btnRegister;
    private ACProgressFlower loadingDialog;
    private boolean isUsernameOK = false;
    private String usernameMsg = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        etUsername = (EditText) findViewById(R.id.et_username);
        etEmail = (EditText) findViewById(R.id.et_email);
//        etIdentifyingCode = (EditText) findViewById(R.id.et_identifying_code);
//        tvGetCode = (TextView) findViewById(R.id.tv_get_code);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        btnRegister = (Button) findViewById(R.id.btn_register);
        ImageView ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
//        tvGetCode.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkUsername(s.toString());
            }

        });
    }

    private void checkUsername(String usernameInput) {
        post()
                .url(Constants.URL_CHECK_USERNAME)
                .addParams("username", usernameInput)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("校检用户名json：", response);
                        switch (Utils.getCode(response)) {
                            case 200://成功
                                isUsernameOK = true;
                                break;
                            case 500://失败
                                isUsernameOK = false;
                                usernameMsg = Utils.getMsg(response);
                                Toasty.error(RegisterActivity.this, Utils.getMsg(response), Toast.LENGTH_SHORT, true).show();
                                break;
                        }
                    }
                });
    }

    private void getEmailCode() {
        loadingDialog = Utils.showLoadingDialog(this, "正在发送验证码");
        final String email = etEmail.getText().toString();
        OkHttpUtils
                .post()
                .url(Constants.URL_GET_EMAIL_CODE)
                .addParams("email", email)
                .addParams("type", "0")//注册账号type=0，重置密码type=1
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("获取邮箱验证码json：", response);
                        loadingDialog.dismiss();
                        switch (Utils.getCode(response)) {
                            case 200://成功
                                String emailType = Utils.getMailType(email);
                                String emailWebsite = "http://www.mail" + emailType + ".com";
                                Intent intent = new Intent(RegisterActivity.this, WebActivity.class);
                                intent.putExtra("url", emailWebsite);
                                intent.putExtra("title", "获取邮箱验证码");
                                startActivity(intent);
                                break;
                            case 500://失败
                                Toasty.error(RegisterActivity.this, Utils.getMsg(response), Toast.LENGTH_SHORT, true).show();
                                break;
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_get_code:
                if (RegexUtils.isEmail(etEmail.getText().toString())) {
                    getEmailCode();
                } else {
                    Toasty.error(this, "请填写正确的邮箱地址", Toast.LENGTH_SHORT, true).show();
                }
                break;
            case R.id.btn_register:
                if (isAllFilled()) {
                    if (isUsernameOK) {
                        register();
                    } else {
                        Toasty.error(RegisterActivity.this, "用户名有误 " + usernameMsg, Toast.LENGTH_SHORT, true).show();
                    }
                } else {
                    Toasty.info(RegisterActivity.this, "你还有未填写的信息", Toast.LENGTH_SHORT, true).show();
                }
                break;
        }
    }

    private void register() {
        loadingDialog = Utils.showLoadingDialog(this, "正在注册");
        post()
                .url(Constants.URL_REGISTER)
                .addParams("username", etUsername.getText().toString())
                .addParams("password", etPwd.getText().toString())
                .addParams("email", etEmail.getText().toString())
//                .addParams("code", etIdentifyingCode.getText().toString())
                .addParams("platform", "manga_android")
                .addParams("deviceId", "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        loadingDialog.dismiss();
                        Log.d("注册json：", response);
                        switch (Utils.getCode(response)) {
                            case 200://成功
                                Toasty.success(RegisterActivity.this, "注册成功!", Toast.LENGTH_SHORT, true).show();
                                finish();
                                break;
                            case 500://失败
                                Toasty.error(RegisterActivity.this, Utils.getMsg(response), Toast.LENGTH_SHORT, true).show();
                                break;
                        }
                    }
                });
    }

    private boolean isAllFilled() {
        boolean empty0 = TextUtils.isEmpty(etUsername.getText());
        boolean empty1 = TextUtils.isEmpty(etEmail.getText());
//        boolean empty2 = TextUtils.isEmpty(etIdentifyingCode.getText());
        boolean empty3 = TextUtils.isEmpty(etPwd.getText());
        if (!empty0 && !empty1  && !empty3) {
            return true;
        } else {
            return false;
        }
    }
}
