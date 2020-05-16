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
import com.smart.account.bean.AccountPerson;
import com.smart.account.bean.Budget;
import com.smart.account.data.DBManger;
import com.smart.account.util.DateUtil;
import com.smart.account.view.AddAcPersonDialog;
import com.smart.account.view.BudgetUpdateDialog;
import com.smart.account.view.LeftSwipeMenuRecyclerView;
import com.smart.account.view.SearchAcPersonRemainDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AllPersonAccountFragment extends Fragment {

    Button mAddBtn;
    Button mAddAcPersonBtn;
    Button mSearchBtn;
    Button mSearchBudgetBtn;
    Button mSearchRemainBtn;

    private LeftSwipeMenuRecyclerView mDailyListview;
    private Handler mHandler= new Handler();
    private BudgetAdapter mBudgetAdapter;
    private List<Budget> mAllBudgets = new ArrayList<>();
    private List<Budget> mSelectDateBudgets = new ArrayList<>();

    private AddAcPersonDialog mAcPersonDialog;
    private SearchAcPersonRemainDialog mSearchAcPersonRemainDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_allperson_account, container, false);
        initView(view);

        return view;
    }

    public static AllPersonAccountFragment getInstance() {
        return new AllPersonAccountFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        initAllData();
    }

    public void initView(View view){
        mAddBtn = view.findViewById(R.id.add_budget_btn);
        mAddAcPersonBtn = view.findViewById(R.id.add_person_btn);
        mSearchBtn = view.findViewById(R.id.search_budget_btn);
        mSearchRemainBtn = view.findViewById(R.id.search_remain_btn);

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getContext(), AddAcountActivity.class);
                intent.putExtra("is_person",false);
                getContext().startActivity(intent);
            }
        });

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getContext(), SearchAcountActivity.class);
                intent.putExtra("is_person",false);
                getContext().startActivity(intent);
            }
        });

        mAddAcPersonBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAcPersonDialog.show();
            }
        });

        mSearchRemainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<AccountPerson> accountPeople = DBManger.getInstance(getContext()).getAllAccountPerson();
                if(accountPeople.size()==0){
                    Toast.makeText(getContext(),"未查询到结果！",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"查询到"+accountPeople.size()+"条结果！",Toast.LENGTH_LONG).show();
                    mSearchAcPersonRemainDialog.setData(accountPeople);
                    mSearchAcPersonRemainDialog.show();
                }

            }
        });

        mDailyListview = view.findViewById(R.id.budget_listview);

        mAcPersonDialog = new AddAcPersonDialog(getContext(),R.layout.dialog_add_ac_person,true,true);
        mSearchAcPersonRemainDialog = new SearchAcPersonRemainDialog(getContext(),R.layout.dialog_search_acperon_remain_result,true,true);
    };


    public void initAllData(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
//                mAlldailySummaries.clear();
//                mAlldailySummaries = DBManger.getInstance(getContext()).getAllDailyData();
                mAllBudgets.clear();
                mAllBudgets = DBManger.getInstance(getContext()).getAllBudgetData();
                Collections.sort(mAllBudgets, new Comparator<Budget>() {
                    @Override
                    public int compare(Budget budget, Budget t1) {
                        long time1 = DateUtil.dateToStamp(budget.getDate());
                        long time2 = DateUtil.dateToStamp(t1.getDate());
                        if (time1>time2){
                            return -1;
                        }
                        return 1;
                    }
                });
                refresList();
            }
        });
    };

    public void refresList(){

        mDailyListview.setLayoutManager(new LinearLayoutManager(getContext()));
        mBudgetAdapter = new BudgetAdapter(getContext(),mAllBudgets);
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
