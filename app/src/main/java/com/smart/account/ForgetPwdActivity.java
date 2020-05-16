package com.smart.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.smart.account.bean.User;
import com.smart.account.data.DBManger;
import com.smart.account.view.TitleView;


public class ForgetPwdActivity extends AppCompatActivity {

    private EditText mNameEd;
    private EditText mPassWordEd;
    private EditText mRepeatPassWordEd;
    private EditText mTelEd;
    private Button mRegBtn;
    private Button mCancelBtn;
    private TitleView mTitleView;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);

        init();
    }

    public void init(){
        mUser = new User();
        String title = getIntent().getStringExtra("title");
        mTitleView = findViewById(R.id.title_view);
        mTitleView.setTitle(title);
        mTitleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mNameEd = findViewById(R.id.reg_name_ed);
        mPassWordEd = findViewById(R.id.reg_password_ed);
        mRepeatPassWordEd = findViewById(R.id.reg_repeat_password_ed);
        mTelEd = findViewById(R.id.reg_connect_ed);
        mRegBtn = findViewById(R.id.reg_btn);
        mCancelBtn = findViewById(R.id.reg_cancle_btn);

        mNameEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mUser.setUserName(editable.toString());
            }
        });

        mPassWordEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mUser.setPassword(editable.toString());
            }
        });

        mRepeatPassWordEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mUser.setRepeatPassword(editable.toString());
            }
        });

        mTelEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mUser.setTelephone(editable.toString());
            }
        });

        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUser.getUserName()==null){
                    Toast.makeText(ForgetPwdActivity.this,"用户名不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mUser.getPassword()==null){
                    Toast.makeText(ForgetPwdActivity.this,"密码不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mUser.getRepeatPassword()==null){
                    Toast.makeText(ForgetPwdActivity.this,"重复密码不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                if (!mUser.getRepeatPassword().equals(mUser.getPassword())){
                    Toast.makeText(ForgetPwdActivity.this,"两次密码不一致！",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mUser.getTelephone()==null){
                    Toast.makeText(ForgetPwdActivity.this,"手机号不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                DBManger.getInstance(ForgetPwdActivity.this).updateUser(mUser, new DBManger.IListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(ForgetPwdActivity.this,"修改成功！",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(ForgetPwdActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(ForgetPwdActivity.this,"修改失败！",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPwdActivity.this.finish();
            }
        });

    }
}
