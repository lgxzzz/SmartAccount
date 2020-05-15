package com.smart.account.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.smart.account.AddTypeActivity;
import com.smart.account.R;
import com.smart.account.bean.Budget;
import com.smart.account.data.DBManger;
import com.smart.account.util.DateUtil;
import com.smart.account.view.DatePickDialog;
import com.smart.account.view.SpinnerAdapter;

import java.util.ArrayList;


public class AddFragment extends Fragment{

    Spinner mInExSp;
    Spinner mTypeSp;

    EditText mDateEd;
    EditText mNumEd;
    EditText mNoteEd;
    DatePickDialog mDatePickDialog;

    String mType;
    Button mAddTypeBtn;
    Button mAddVoiceBtn;
    Button mAddBtn;

    Budget mBudget;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragement_add, container, false);
        initView(view);

        return view;
    }

    public static AddFragment getInstance() {
        return new AddFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    public void initView(View view){
        mBudget = new Budget();

        mInExSp = view.findViewById(R.id.income_exp_sp);
        mTypeSp = view.findViewById(R.id.type_sp);
        mDateEd = view.findViewById(R.id.reg_date_ed);
        mNumEd = view.findViewById(R.id.add_money_ed);
        mNoteEd = view.findViewById(R.id.add_note_ed);
        mAddBtn = view.findViewById(R.id.add_sure_btn);
        mAddTypeBtn = view.findViewById(R.id.add_type_btn);


        final ArrayList<String> mInExpType=new ArrayList<String>();
        mInExpType.add("收入");
        mInExpType.add("支出");
        mType = "收入";

        SpinnerAdapter adapter = new SpinnerAdapter(getContext(),android.R.layout.simple_spinner_item,mInExpType);
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

        mDatePickDialog = new DatePickDialog(getContext(),R.layout.dialog_date,true,true);
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
                    Toast.makeText(getContext(),"请输入金额！",Toast.LENGTH_LONG).show();
                    return;
                }
                DBManger.getInstance(getContext()).insertBudget(mBudget, new DBManger.IListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(getContext(),"添加成功！",Toast.LENGTH_LONG).show();
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
                startActivity(new Intent(getContext(), AddTypeActivity.class));
            }
        });


        refreshBudgetType();
    };

    public void initData() {
        refreshBudgetType();
    }

    public void refreshBudgetType(){
        final ArrayList<String> mTypes= DBManger.getInstance(getContext()).getBudgetTypeByKey(mType);

        SpinnerAdapter TypeAdapter = new SpinnerAdapter(getContext(),android.R.layout.simple_spinner_item,mTypes);
        mTypeSp.setAdapter(TypeAdapter);
        mTypeSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String budgetid = DBManger.getInstance(getContext()).getBudgetTypeIDByNote(mTypes.get(position));
                mBudget.setBudegetTypeId(budgetid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        String budgetid = DBManger.getInstance(getContext()).getBudgetTypeIDByNote(mTypes.get(0));
        mBudget.setBudegetTypeId(budgetid);
    }


}
