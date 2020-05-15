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


public class UpdateActivity extends AppCompatActivity {

    private EditText mNameEd;
    private EditText mPassWordEd;
    private EditText mRepeatPassWordEd;
    private RadioGroup mSexRg;
    private EditText mConnectEd;
    private Button mRegBtn;
    private Button mCancelBtn;

    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        init();
    }

    public void init(){
        mUser = DBManger.getInstance(this).mUser;


        mNameEd = findViewById(R.id.reg_name_ed);
        mPassWordEd = findViewById(R.id.reg_password_ed);
        mRepeatPassWordEd = findViewById(R.id.reg_repeat_password_ed);
        mConnectEd = findViewById(R.id.reg_connect_ed);
        mRegBtn = findViewById(R.id.reg_btn);
        mSexRg = findViewById(R.id.reg_sex_rg);
        mCancelBtn = findViewById(R.id.reg_cancle_btn);

        mNameEd.setText(mUser.getUserName());
        mConnectEd.setText(mUser.getTelephone()+"");
//        mSexRg.check(!mUser.getSex().equals("男")?R.id.reg_sex_women:R.id.reg_sex_man);

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

        mSexRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String sex= "男";
                switch(i){
                    case R.id.reg_sex_women:
                        sex = "女";
                    break;
                    default:

                    break;
                }
                mUser.setSex(sex);
            }
        });
        mUser.setSex("男");

        mConnectEd.addTextChangedListener(new TextWatcher() {
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
                    Toast.makeText(UpdateActivity.this,"用户名不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mUser.getPassword()==null){
                    Toast.makeText(UpdateActivity.this,"密码不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mUser.getRepeatPassword()==null){
                    Toast.makeText(UpdateActivity.this,"重复密码不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                if (!mUser.getRepeatPassword().equals(mUser.getPassword())){
                    Toast.makeText(UpdateActivity.this,"两次密码不一致！",Toast.LENGTH_LONG).show();
                    return;
                }
                DBManger.getInstance(UpdateActivity.this).updateUser(mUser, new DBManger.IListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(UpdateActivity.this,"修改成功！",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(UpdateActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(UpdateActivity.this,"修改失败！",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateActivity.this.finish();
            }
        });
    }
}
