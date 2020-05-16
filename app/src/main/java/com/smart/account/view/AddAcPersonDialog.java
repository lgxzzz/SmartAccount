package com.smart.account.view;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.smart.account.R;
import com.smart.account.bean.AccountPerson;
import com.smart.account.bean.User;
import com.smart.account.data.DBManger;


public class AddAcPersonDialog extends Dialog {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable;//控制返回键是否dismiss
    private View view;
    private Context context;
    private Button mSureBtn;
    private Button mCancelBtn;
    private EditText mAddAcPersonEd;
    private AccountPerson person;
    public void setlistener(IOnSureListener mlistener) {
        this.mlistener = mlistener;
    }

    IOnSureListener mlistener;
    public AddAcPersonDialog(Context context, int layoutid, boolean isCancelable, boolean isBackCancelable) {
        super(context, R.style.MyDialog);

        this.context = context;
        this.view = LayoutInflater.from(context).inflate(layoutid, null);
        this.iscancelable = isCancelable;
        this.isBackCancelable = isBackCancelable;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(view);//这行一定要写在前面
        setCancelable(iscancelable);//点击外部不可dismiss
        setCanceledOnTouchOutside(isBackCancelable);

        initView();
    }

    public void initView() {
        mAddAcPersonEd = view.findViewById(R.id.add_person_ed);
        mSureBtn = view.findViewById(R.id.sure_btn);
        mCancelBtn = view.findViewById(R.id.cancel_btn);

        mSureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAddAcPersonEd.getText().toString().length()==0){
                    Toast.makeText(getContext(),"人员名不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                DBManger.getInstance(getContext()).insertAccountPerson(mAddAcPersonEd.getText().toString());
                dismiss();
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    public interface IOnSureListener{
        public void onSuccess();
        public void onFail(String error);
    }
}