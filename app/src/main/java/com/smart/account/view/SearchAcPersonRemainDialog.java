package com.smart.account.view;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.smart.account.R;
import com.smart.account.adpater.BudgetAdapter;
import com.smart.account.adpater.SearchAcPersonRemainAdapter;
import com.smart.account.bean.AccountPerson;
import com.smart.account.bean.Budget;

import java.util.List;


public class SearchAcPersonRemainDialog extends Dialog {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable;//控制返回键是否dismiss
    private View view;
    private Context context;

    private ListView mListview;
    private Handler mHandler= new Handler();
    private SearchAcPersonRemainAdapter mAdapter;

    private List<AccountPerson> mAccountPersons;
    public void setlistener(IOnSureListener mlistener) {
        this.mlistener = mlistener;
    }

    IOnSureListener mlistener;
    public SearchAcPersonRemainDialog(Context context, int layoutid, boolean isCancelable, boolean isBackCancelable) {
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

    }

    public void setData(List<AccountPerson> mAccountPersons){
        this.mAccountPersons = mAccountPersons;
        mListview = view.findViewById(R.id.budget_listview);
        mAdapter = new SearchAcPersonRemainAdapter(getContext(),mAccountPersons);
        mListview.setAdapter(mAdapter);
    }

    public interface IOnSureListener{
        public void onSure();
    }
}