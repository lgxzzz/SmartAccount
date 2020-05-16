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

import com.smart.account.bean.User;
import com.smart.account.data.DBManger;
import com.smart.account.view.DeleteUserConfirmDialog;
import com.smart.account.view.TitleView;


public class DeleteUserActivity extends AppCompatActivity{

    private EditText mNameEd;
    private EditText mTelEd;
    private Button mDeleteSureBtn;
    private Button mDeleteCancelBtn;

    private TitleView mTitleView;

    private User mUser;

    private DeleteUserConfirmDialog mDeleteUserConfirmDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
        init();
    }

    public void init(){

        mUser = new User();


        mTitleView = findViewById(R.id.title_view);
        mTitleView.setTitle("删除用户");
        mTitleView.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mNameEd = findViewById(R.id.reg_name_ed);
        mTelEd = findViewById(R.id.reg_connect_ed);

        mDeleteSureBtn = findViewById(R.id.reg_delte_btn);
        mDeleteCancelBtn = findViewById(R.id.reg_cancle_btn);
        mDeleteSureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser.getUserName()==null){
                    Toast.makeText(DeleteUserActivity.this,"用户名不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                if (mUser.getTelephone()==null){
                    Toast.makeText(DeleteUserActivity.this,"手机号不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                mDeleteUserConfirmDialog.setUser(mUser);
                mDeleteUserConfirmDialog.show();
            }
        });
        mDeleteCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                mUser.setUserName(editable.toString());
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

        mDeleteUserConfirmDialog = new DeleteUserConfirmDialog(DeleteUserActivity.this,R.layout.dialog_delete_user_confirm,true,true);
        mDeleteUserConfirmDialog.setlistener(new DeleteUserConfirmDialog.IOnSureListener() {
            @Override
            public void onSuccess() {
                finish();
            }

            @Override
            public void onFail(String error) {

            }
        });
    }

}
