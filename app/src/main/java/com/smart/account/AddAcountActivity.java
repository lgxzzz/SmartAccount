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
import android.widget.TextView;
import android.widget.Toast;

import com.smart.account.bean.Budget;
import com.smart.account.data.DBManger;
import com.smart.account.util.DateUtil;
import com.smart.account.view.DatePickDialog;
import com.smart.account.view.SpinnerAdapter;

import java.util.ArrayList;


public class AddAcountActivity extends AppCompatActivity{

    Spinner mInExSp;
    Spinner mTypeSp;

    EditText mDateEd;
    EditText mNumEd;
    EditText mNoteEd;
    DatePickDialog mDatePickDialog;

    String mType;
    Button mAddTypeBtn;
    Button mAddBackBtn;
    Button mAddBtn;

    Budget mBudget;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragement_add);
        initView();
        initData();
    }

    public void initView(){
        mBudget = new Budget();

        mInExSp = this.findViewById(R.id.income_exp_sp);
        mTypeSp = this.findViewById(R.id.type_sp);
        mDateEd = this.findViewById(R.id.reg_date_ed);
        mNumEd = this.findViewById(R.id.add_money_ed);
        mNoteEd = this.findViewById(R.id.add_note_ed);
        mAddBtn = this.findViewById(R.id.add_sure_btn);
        mAddTypeBtn = this.findViewById(R.id.add_type_btn);


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

        mNumEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mBudget.setNum(s.toString());
            }
        });

        mNoteEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mBudget.setNote(s.toString());
            }
        });

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNumEd.getEditableText().toString().length()==0){
                    Toast.makeText(AddAcountActivity.this,"请输入金额！",Toast.LENGTH_LONG).show();
                    return;
                }
                DBManger.getInstance(AddAcountActivity.this).insertBudget(mBudget, new DBManger.IListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(AddAcountActivity.this,"添加成功！",Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
            }
        });

        mAddTypeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddAcountActivity.this, AddTypeActivity.class));
            }
        });

        mAddBackBtn = this.findViewById(R.id.add_back_btn);
        mAddBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        refreshBudgetType();
    };

    public void initData() {
        refreshBudgetType();
    }

    public void refreshBudgetType(){
        final ArrayList<String> mTypes= DBManger.getInstance(AddAcountActivity.this).getBudgetTypeByKey(mType);

        SpinnerAdapter TypeAdapter = new SpinnerAdapter(AddAcountActivity.this,android.R.layout.simple_spinner_item,mTypes);
        mTypeSp.setAdapter(TypeAdapter);
        mTypeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String budgetid = DBManger.getInstance(AddAcountActivity.this).getBudgetTypeIDByNote(mTypes.get(position));
                mBudget.setBudegetTypeId(budgetid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String budgetid = DBManger.getInstance(AddAcountActivity.this).getBudgetTypeIDByNote(mTypes.get(0));
        mBudget.setBudegetTypeId(budgetid);
    }

}
