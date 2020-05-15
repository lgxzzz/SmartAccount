package com.smart.account.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.smart.account.R;
import com.smart.account.adpater.BudgetAdapter;
import com.smart.account.bean.Budget;
import com.smart.account.data.DBManger;
import com.smart.account.util.DateUtil;
import com.smart.account.view.BudgetUpdateDialog;
import com.smart.account.view.DatePickDialog;
import com.smart.account.view.LeftSwipeMenuRecyclerView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class DetaildFragment extends AppCompatDialogFragment{

    private TextView mYearTv;
    private TextView mMonthTv;
    private TextView mIncomeTv;
    private TextView mExpenseTv;
    private DatePickDialog mDatePickDialog;

    private LeftSwipeMenuRecyclerView mDailyListview;
//    private SummaryAdapter mAdapter;

    private Handler mHandler= new Handler();

//    private List<DailySummary> mAlldailySummaries = new ArrayList<>();
//    private List<DailySummary> mSelectDateSummaries = new ArrayList<>();

    private BudgetAdapter mBudgetAdapter;
    private List<Budget> mAllBudgets = new ArrayList<>();
    private List<Budget> mSelectDateBudgets = new ArrayList<>();

    private BudgetUpdateDialog mDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragement_detail, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initAllData();
    }

    public static DetaildFragment getInstance() {
        return new DetaildFragment();
    }

    public void initView(View view){
        mDailyListview = view.findViewById(R.id.budget_listview);

        mYearTv =view.findViewById(R.id.year_tv);
        mMonthTv =view.findViewById(R.id.month_tv);
        mIncomeTv =view.findViewById(R.id.income_tv);
        mExpenseTv =view.findViewById(R.id.expense_tv);
        mYearTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatePickDialog.show();
            }
        });

        mDatePickDialog = new DatePickDialog(getContext(),R.layout.dialog_date,true,true);
        mDatePickDialog.setlistener(new DatePickDialog.IOnSelectListener() {
            @Override
            public void onSelect(final int[] date) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        String datestr = date[0]+"年"+date[1]+"月";
                        Toast.makeText(getContext(),"选择月份："+datestr,Toast.LENGTH_LONG).show();
                        mYearTv.setText(date[0]+"年");
                        mMonthTv.setText(date[1]+"月");
                        refresListByMonth(datestr);
                        refreshDataByMonth(datestr);
                    }
                });
            }
        });

        mDialog = new BudgetUpdateDialog(getContext(),R.layout.dialog_buget_update,true,true);
        mDialog.setlistener(new BudgetUpdateDialog.IOnSureListener() {
            @Override
            public void onSure() {
                initAllData();
            }
        });
    };


    public void initAllData(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                mAlldailySummaries.clear();
//                mAlldailySummaries = DBManger.getInstance(getContext()).getAllDailyData();
                mAllBudgets.clear();
                mAllBudgets = DBManger.getInstance(getContext()).getAllBudgetData();
                refresListByMonth(DateUtil.getCurrentMonthStr());
                refreshDataByMonth(DateUtil.getCurrentMonthStr());
            }
        });
    };

    //根据月份查询收入支出
    public void refreshDataByMonth(String select_date){
        int income = 0;
        int expense = 0;
//        for (int i=0;i<mAlldailySummaries.size();i++){
//            DailySummary summary = mAlldailySummaries.get(i);
//            List<Budget> budgets = summary.getmBudgets();
//            String date = summary.getDate();
//            if (date.contains(select_date)){
//                int in = summary.getIncome();
//                int ex = summary.getExpense();
//                income = in+income;
//                expense = ex + expense;
//            }
//        }
        for (int i=0;i<mAllBudgets.size();i++){
            Budget budget = mAllBudgets.get(i);
            String date = budget.getDate();
            String type = budget.getType();
            if (date.contains(select_date)){
                if (type.equals("收入")){
                    income = Integer.parseInt(budget.getNum())+income;
                }else{
                    expense = Integer.parseInt(budget.getNum())+expense;
                }
            }
        }
        mIncomeTv.setText(income+"元");
        mExpenseTv.setText(expense+"元");
    }

    //根据月份查询收入支出
    public void refresListByMonth(String select_date){
//        mSelectDateSummaries.clear();
//        for (int i=0;i<mAlldailySummaries.size();i++){
//            DailySummary summary = mAlldailySummaries.get(i);
//            List<Budget> budgets = summary.getmBudgets();
//            String date = summary.getDate();
//            if (date.contains(select_date)){
//                mSelectDateSummaries.add(summary);
//            }
//        }
//        mDailyListview.setLayoutManager(new LinearLayoutManager(getContext()));
//        mAdapter = new SummaryAdapter(getContext(),mSelectDateSummaries);
//        mDailyListview.setAdapter(mAdapter);
//        mDailyListview.setOnItemActionListener(new LeftSwipeMenuRecyclerView.OnItemActionListener() {
//            //点击
//            @Override
//            public void OnItemClick(int position) {
//                Toast.makeText(getContext(),"Click"+position,Toast.LENGTH_SHORT).show();
//            }
//            //置顶
//            @Override
//            public void OnItemTop(int position) {
//
//            }
//            //删除
//            @Override
//            public void OnItemDelete(int position) {
//                DBManger.getInstance(getContext()).deleteBudegetByDialy(mSelectDateSummaries.get(position));
//
//                initAllData();
//            }
//        });

        mSelectDateBudgets.clear();
        for (int i=0;i<mAllBudgets.size();i++){
            Budget budget = mAllBudgets.get(i);
            String date = budget.getDate();
            if (date.contains(select_date)){
                mSelectDateBudgets.add(budget);
            }
        }
        mDailyListview.setLayoutManager(new LinearLayoutManager(getContext()));
        mBudgetAdapter = new BudgetAdapter(getContext(),mSelectDateBudgets);
        mDailyListview.setAdapter(mBudgetAdapter);
        mDailyListview.setOnItemActionListener(new LeftSwipeMenuRecyclerView.OnItemActionListener() {
            //点击
            @Override
            public void OnItemClick(int position) {
                Toast.makeText(getContext(),"Click"+position,Toast.LENGTH_SHORT).show();
            }
            //置顶
            @Override
            public void OnItemTop(int position) {
                mDialog.setBudget(mSelectDateBudgets.get(position));
                mDialog.show();

            }
            //删除
            @Override
            public void OnItemDelete(int position) {
                DBManger.getInstance(getContext()).deleteBudegetByBudget(mSelectDateBudgets.get(position));

                initAllData();
            }
        });
    }


}
