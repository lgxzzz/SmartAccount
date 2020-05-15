package com.smart.account.view;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.weiget.CalendarView;
import com.smart.account.R;
import com.smart.account.util.DateUtil;

import java.util.Calendar;


public class DatePickDialog extends Dialog {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable;//控制返回键是否dismiss
    private View view;
    private Context context;
    private Button mSureBtn;
    private Button mCancelBtn;
    private TextView mTitleTv;
    private CalendarView mCalendarView;
    private ImageView mLastMonthBtn;
    private ImageView mNextMonthBtn;
    public void setlistener(IOnSelectListener mlistener) {
        this.mlistener = mlistener;
    }

    IOnSelectListener mlistener;
    public DatePickDialog(Context context, int layoutid, boolean isCancelable, boolean isBackCancelable) {
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
        mLastMonthBtn = view.findViewById(R.id.lastmon_btn);
        mNextMonthBtn = view.findViewById(R.id.nextmon_btn);
        mTitleTv = view.findViewById(R.id.title);

        mCalendarView = findViewById(R.id.calendar);
        mCalendarView.setStartEndDate("2020.1", "2027.12").setInitDate(DateUtil.getCurrentDate()).setSingleDate(DateUtil.getCurrentDay()).init();
        mTitleTv.setText(DateUtil.getCurrentDayStr());
        //月份切换回调
        mCalendarView.setOnPagerChangeListener(new OnPagerChangeListener() {
            @Override
            public void onPagerChanged(int[] date) {
                String str = date[0]+"年"+date[1]+"月"+date[2]+"日";
                mTitleTv.setText(str);
            }
        });


        mSureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                DateBean bean = mCalendarView.getSingleDate();
                mlistener.onSelect(bean.getSolar());
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

            }
        });

        mLastMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.lastMonth();
            }
        });

        mNextMonthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.nextMonth();
            }
        });
    }

    public interface IOnSelectListener{
        public void onSelect(int[] date);
    }


}