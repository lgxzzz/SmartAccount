package com.smart.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.account.data.DBManger;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mRegisterView;
    private EditText mNameEd;
    private EditText mPassWordEd;
    private String mName;
    private String mPassWord;
    private Button mLoginBtn;
    private Button mForgetPwdBtn;
    private Button mDeleteUserBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init(){

        mRegisterView = findViewById(R.id.login_to_register_btn);
        mRegisterView.setOnClickListener(this);

        mNameEd = findViewById(R.id.reg_name_ed);
        mPassWordEd = findViewById(R.id.reg_password_ed);

        mLoginBtn = findViewById(R.id.reg_login_btn);
        mLoginBtn.setOnClickListener(this);

        mForgetPwdBtn = findViewById(R.id.login_to_forgetpwd_btn);
        mDeleteUserBtn = findViewById(R.id.login_to_deleteuser_btn);
        //忘记密码
        mForgetPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPwdActivity.class);
                intent.putExtra("title","忘记密码");
                startActivity(intent);
            }
        });
        //删除用户
        mDeleteUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, DeleteUserActivity.class);
                intent.putExtra("title","删除用户");
                startActivity(intent);
            }
        });

        mNameEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mName = editable.toString();
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
               mPassWord = editable.toString();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_to_register_btn:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                break;
            case R.id.reg_login_btn:
                DBManger.getInstance(LoginActivity.this).login(mName, mPassWord, new DBManger.IListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(LoginActivity.this,"登陆成功",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        LoginActivity.this.finish();
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(LoginActivity.this,error,Toast.LENGTH_LONG).show();
                    }
                });
                break;
        }
    }
}
