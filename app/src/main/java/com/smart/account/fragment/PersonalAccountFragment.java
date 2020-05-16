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
import android.widget.TextView;
import android.widget.Toast;

import com.smart.account.AddAcountActivity;
import com.smart.account.R;
import com.smart.account.SearchAcountActivity;
import com.smart.account.adpater.BudgetAdapter;
import com.smart.account.bean.AccountPerson;
import com.smart.account.bean.Budget;
import com.smart.account.bean.User;
import com.smart.account.data.DBManger;
import com.smart.account.util.DateUtil;
import com.smart.account.view.BudgetUpdateDialog;
import com.smart.account.view.LeftSwipeMenuRecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class PersonalAccountFragment extends Fragment {

    Button mAddBtn;
    Button mSearchBtn;
    TextView mRemainTv;

    private LeftSwipeMenuRecyclerView mDailyListview;
    private Handler mHandler= new Handler();
    private BudgetAdapter mBudgetAdapter;
    private List<Budget> mAllBudgets = new ArrayList<>();
    private List<Budget> mSelectDateBudgets = new ArrayList<>();

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
        mRemainTv = view.findViewById(R.id.personnal_remain_tv);
    };


    public void initAllData(){
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mAllBudgets.clear();
                User mUser = DBManger.getInstance(getContext()).mUser;
                mAllBudgets = DBManger.getInstance(getContext()).getAllBudgetDataByAccountName(mUser.getUserName());
                AccountPerson accountPerson = DBManger.getInstance(getContext()).getAccountPersonByName(mUser.getUserName());
                if (accountPerson.getBalance()!=null){
                    mRemainTv.setText("余额："+accountPerson.getBalance());
                }
                refresList();
            }
        });
    };


    //根据月份查询收入支出
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
