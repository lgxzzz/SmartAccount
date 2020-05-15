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
import com.smart.account.data.DBManger;

import java.util.ArrayList;
import java.util.Calendar;


public class AddTypeDialog extends Dialog {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable;//控制返回键是否dismiss
    private View view;
    private Context context;
    private Button mSureBtn;
    private Button mCancelBtn;
    private Spinner mTypeSp;
    private EditText mNoteEd;
    public void setlistener(IOnSureListener mlistener) {
        this.mlistener = mlistener;
    }

    IOnSureListener mlistener;
    public AddTypeDialog(Context context, int layoutid, boolean isCancelable, boolean isBackCancelable) {
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

        mTypeSp = view.findViewById(R.id.spinner_type);
        mNoteEd = view.findViewById(R.id.add_type_note_ed);



        mSureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note = mNoteEd.getEditableText().toString();
                if (note.length() == 0){
                    Toast.makeText(getContext(),"请输入说明！",Toast.LENGTH_LONG).show();
                    return;
                }
                DBManger.getInstance(getContext()).insertBudgetType(mTypeSp.getSelectedItem().toString(),note);
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

        final ArrayList<String> mInExpType=new ArrayList<String>();
        mInExpType.add("收入");
        mInExpType.add("支出");
        SpinnerAdapter adapter = new SpinnerAdapter(getContext(),android.R.layout.simple_spinner_item,mInExpType);
        mTypeSp.setAdapter(adapter);
    }

    public interface IOnSureListener{
        public void onSure();
    }
}