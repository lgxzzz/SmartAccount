package com.smart.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.smart.account.bean.AccountPerson;
import com.smart.account.bean.Budget;
import com.smart.account.bean.User;
import com.smart.account.data.DBManger;
import com.smart.account.util.DateUtil;
import com.smart.account.view.DatePickDialog;
import com.smart.account.view.SearchBudgetResultDialog;
import com.smart.account.view.SpinnerAdapter;

import java.util.ArrayList;
import java.util.List;


public class SearchAcountActivity extends AppCompatActivity{

    Spinner mInExSp;
    Spinner mTypeSp;
    Spinner mPersonSp;

    EditText mDateEd;
    DatePickDialog mDatePickDialog;

    String mType;
    Button mAddBackBtn;
    Button mSearchBtn;

    Budget mBudget;

    boolean isPersonal = true;

    SearchBudgetResultDialog mResultDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_account);
        initView();
        initData();
    }

    public void initView(){
        //是否个人模式
        isPersonal = getIntent().getBooleanExtra("is_person",true);

        mBudget = new Budget();

        mInExSp = this.findViewById(R.id.income_exp_sp);
        mTypeSp = this.findViewById(R.id.type_sp);
        mPersonSp = this.findViewById(R.id.search_person_sp);
        mDateEd = this.findViewById(R.id.reg_date_ed);
        mSearchBtn = this.findViewById(R.id.search_btn);


        final ArrayList<String> mInExpType=new ArrayList<String>();
        mInExpType.add("收入");
        mInExpType.add("支出");
        mType = "收入";

        SpinnerAdapter adapter = new SpinnerAdapter(this,android.R.layout.simple_spinner_item,mInExpType);
        mInExSp.setAdapter(adapter);
        mInExSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mType = mInExpType.get(position);
                refreshBudgetType();
                mBudget.setType(mType);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mDateEd.setFocusableInTouchMode(false);//不可编辑
        mDateEd.setFocusable(false);//不可编辑
        mDateEd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePickDialog.show();
            }
        });
        int[] date = DateUtil.getIntDay();
        mDateEd.setText(date[0]+"年"+date[1]+"月"+date[2]+"日");
        mBudget.setDate(date[0]+"年"+date[1]+"月"+date[2]+"日");

        mDatePickDialog = new DatePickDialog(this,R.layout.dialog_date,true,true);
        mDatePickDialog.setlistener(new DatePickDialog.IOnSelectListener() {
            @Override
            public void onSelect(int[] date) {
                mDateEd.setText(date[0]+"年"+date[1]+"月"+date[2]+"日");
                mBudget.setDate(date[0]+"年"+date[1]+"月"+date[2]+"日");
            }
        });



        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                List<Budget> budgets =   DBManger.getInstance(getBaseContext()).searchBudegets(mBudget);
                if(budgets.size()==0){
                    Toast.makeText(getApplicationContext(),"未查询到结果！",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"查询到"+budgets.size()+"条结果！",Toast.LENGTH_LONG).show();
                    mResultDialog.setBudgets(budgets);
                    mResultDialog.show();
                }

            }
        });

        mAddBackBtn = this.findViewById(R.id.add_back_btn);
        mAddBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mResultDialog = new SearchBudgetResultDialog(SearchAcountActivity.this,R.layout.dialog_search_budget_result,true,true);

        refreshBudgetType();
    };

    public void initData() {
        refreshBudgetType();
        refreshPerson();
    }

    public void refreshBudgetType(){
        final ArrayList<String> mTypes= DBManger.getInstance(SearchAcountActivity.this).getBudgetTypeByKey(mType);

        SpinnerAdapter TypeAdapter = new SpinnerAdapter(SearchAcountActivity.this,android.R.layout.simple_spinner_item,mTypes);
        mTypeSp.setAdapter(TypeAdapter);
        mTypeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String budgetid = DBManger.getInstance(SearchAcountActivity.this).getBudgetTypeIDByNote(mTypes.get(position));
                mBudget.setBudegetTypeId(budgetid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String budgetid = DBManger.getInstance(SearchAcountActivity.this).getBudgetTypeIDByNote(mTypes.get(0));
        mBudget.setBudegetTypeId(budgetid);
    }

    public void refreshPerson(){
        List<AccountPerson> mPersonData = new ArrayList<>();
        final ArrayList<String> mPersonNameData = new ArrayList<>();
        if (isPersonal){
            User mUser = DBManger.getInstance(getApplicationContext()).mUser;
            mPersonNameData.add(mUser.getUserName());
        }else{
            mPersonData= DBManger.getInstance(SearchAcountActivity.this).getAllAccountPerson();
            for (int i =0;i<mPersonData.size();i++){
                AccountPerson accountPerson = mPersonData.get(i);
                mPersonNameData.add(accountPerson.getName());
            }
        }
        mBudget.setAccount_person_name(mPersonNameData.get(0));

        SpinnerAdapter TypeAdapter = new SpinnerAdapter(SearchAcountActivity.this,android.R.layout.simple_spinner_item,mPersonNameData);
        mPersonSp.setAdapter(TypeAdapter);
        mPersonSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mBudget.setAccount_person_name(mPersonNameData.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


}
