package com.zymh.www.zymh.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zymh.www.zymh.R;

import es.dmoral.toasty.Toasty;
import okhttp3.Call;

public class ForgetActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etUsername;
    private EditText etEmail;
    private EditText etAuth;
    private EditText etPwd;
    private Button btnRegister;
    private Button btnRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        initView();
    }

    private void initView() {
        etUsername = (EditText) findViewById(R.id.et_username);
        etEmail = (EditText) findViewById(R.id.et_email);
        etAuth = (EditText) findViewById(R.id.et_identifying_code);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        btnRest = (Button) findViewById(R.id.btn_reset_pwd);
        btnRest.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reset_pwd:
                if (isAllFilled()) {
                    register();
                } else {
                    Toasty.error(this, "你还有未填写的信息", Toast.LENGTH_SHORT, true).show();
                }
                break;
        }
    }

    private void register() {
        OkHttpUtils
                .post()
                .url("http://api.manga163.com/user/register")
                .addParams("username", etUsername.getText().toString())
                .addParams("password", etPwd.getText().toString())
                .addParams("email", etEmail.getText().toString())
                .addParams("code", etAuth.getText().toString())
                .addParams("platform", "manga_android")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.d("a", response);
                    }
                });
    }

    private boolean isAllFilled() {
        boolean empty0 = TextUtils.isEmpty(etUsername.getText());
        boolean empty1 = TextUtils.isEmpty(etEmail.getText());
        boolean empty2 = TextUtils.isEmpty(etAuth.getText());
        boolean empty3 = TextUtils.isEmpty(etPwd.getText());
        if (!empty0 && !empty1 && !empty2 && !empty3) {
            return true;
        } else {
            return false;
        }
    }
}
