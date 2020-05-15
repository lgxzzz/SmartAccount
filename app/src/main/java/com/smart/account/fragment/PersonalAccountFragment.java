package com.smart.account.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.smart.account.AddAcountActivity;
import com.smart.account.R;
import com.smart.account.SearchAcountActivity;
import com.smart.account.adpater.BudgetAdapter;
import com.smart.account.bean.Budget;
import com.smart.account.data.DBManger;
import com.smart.account.util.DateUtil;
import com.smart.account.view.BudgetUpdateDialog;
import com.smart.account.view.LeftSwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;


public class PersonalAccountFragment extends Fragment {

    Button mAddBtn;
    Button mSearchBtn;

    private LeftSwipeMenuRecyclerView mDailyListview;
    private Handler mHandler= new Handler();
    private BudgetAdapter mBudgetAdapter;
    private List<Budget> mAllBudgets = new ArrayList<>();
    private List<Budget> mSelectDateBudgets = new ArrayList<>();

    private BudgetUpdateDialog mDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_personal_account, container, false);
        initView(view);

        return view;
    }

    public static PersonalAccountFragment getInstance() {
        return new PersonalAccountFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        initAllData();
    }

    public void initView(View view){
        mAddBtn = view.findViewById(R.id.add_budget_btn);
        mSearchBtn = view.findViewById(R.id.search_budget_btn);

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), AddAcountActivity.class));
            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getContext().startActivity(new Intent(getContext(), SearchAcountActivity.class));
            }
        });

        mDailyListview = view.findViewById(R.id.budget_listview);
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
