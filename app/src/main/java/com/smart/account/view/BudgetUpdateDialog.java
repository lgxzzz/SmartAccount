package com.smart.account.view;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.smart.account.R;
import com.smart.account.bean.Budget;
import com.smart.account.data.DBManger;

import java.util.ArrayList;


public class BudgetUpdateDialog extends Dialog {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable;//控制返回键是否dismiss
    private View view;
    private Context context;
    private Button mSureBtn;
    private Button mCancelBtn;
    private Spinner mTypeSp;
    private EditText mNoteEd;
    private EditText mNumEd;
    private Budget mBudget;
    public void setlistener(IOnSureListener mlistener) {
        this.mlistener = mlistener;
    }

    IOnSureListener mlistener;
    public BudgetUpdateDialog(Context context, int layoutid, boolean isCancelable, boolean isBackCancelable) {
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
        mSureBtn = view.findViewById(R.id.sure_btn);
        mCancelBtn = view.findViewById(R.id.cancel_btn);

        mTypeSp = view.findViewById(R.id.budget_type_sp);
        mNoteEd = view.findViewById(R.id.budget_note_ed);
        mNumEd = view.findViewById(R.id.budget_num_ed);



        mSureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBudget.setNum(mNumEd.getEditableText().toString());
                mBudget.setNote(mNoteEd.getEditableText().toString());
                DBManger.getInstance(getContext()).updateBudget(mBudget);
                if (mlistener!=null){
                    mlistener.onSure();
                }
                dismiss();
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        final ArrayList<String> mTypes= DBManger.getInstance(getContext()).getBudgetTypeByKey(mBudget.getType());

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
        String budgetNote = DBManger.getInstance(getContext()).getBudgetTypeByID(mBudget.getBudegetTypeId());
        int index = mTypes.indexOf(budgetNote);
        mTypeSp.setSelection(index);

        mNumEd.setText(mBudget.getNum());
        mNoteEd.setText(mBudget.getNote());
    }

    public interface IOnSureListener{
        public void onSure();
    }

    public void setBudget(Budget budget){
        this.mBudget = budget;
    }
}