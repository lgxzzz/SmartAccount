package com.smart.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.smart.account.adpater.InExAdapter;
import com.smart.account.bean.BudgetType;
import com.smart.account.data.DBManger;
import com.smart.account.view.AddTypeDialog;

import java.util.List;


public class AddTypeActivity extends AppCompatActivity {

    ListView mIncomeExpListView;

    InExAdapter mIncomeExpAdapter;

    Button mAddBtn;

    AddTypeDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_type);

        initView();
        initData();
    }

    public void initView(){
        mIncomeExpListView = findViewById(R.id.income_ex_type_listview);
        mAddBtn = findViewById(R.id.add_type_btn);
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.show();
            }
        });
        mDialog = new AddTypeDialog(this,R.layout.dialog_type,true,true);
        mDialog.setlistener(new AddTypeDialog.IOnSureListener() {
            @Override
            public void onSure() {
                initData();
            }
        });
    }

    public void initData(){
        List<BudgetType> mIncomeExpTypes= DBManger.getInstance(this).getAllBudgetType();
        mIncomeExpAdapter = new InExAdapter(AddTypeActivity.this,mIncomeExpTypes);
        mIncomeExpListView.setAdapter(mIncomeExpAdapter);
    }
}
