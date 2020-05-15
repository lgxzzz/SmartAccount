package com.smart.account.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smart.account.R;
import com.smart.account.bean.Budget;
import com.smart.account.bean.DailySummary;
import com.smart.account.data.DBManger;


import java.util.ArrayList;
import java.util.List;

public class DailySummaryAdapter extends BaseAdapter{
    public List<DailySummary> mDailySummarys = new ArrayList<>();
    public Context mContext;
    public DailySummaryAdapter(Context mContext, List<DailySummary> mDailySummarys){
        this.mDailySummarys = mDailySummarys;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mDailySummarys.size();
    }

    @Override
    public Object getItem(int position) {
        return mDailySummarys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final DailySummary dailySummary = this.mDailySummarys.get(position);
        ViewHoler holer = null;
        if (view == null){
            holer = new ViewHoler();
            view = LayoutInflater.from(mContext).inflate(R.layout.daily_item,null);
            holer.mDateTv = (TextView) view.findViewById(R.id.date_tv);
            holer.mIncomeTv = (TextView) view.findViewById(R.id.income_tv);
            holer.mExpenseTV = (TextView) view.findViewById(R.id.expense_tv);
            holer.mDetailViewGroup = (LinearLayout) view.findViewById(R.id.detail_viewgroup);
            view.setTag(holer);
        }else{
            holer = (ViewHoler) view.getTag();
        }
        holer.mDateTv.setText(dailySummary.getDate());
        holer.mIncomeTv.setText("收入:"+dailySummary.getIncome());
        holer.mExpenseTV.setText("支出:"+dailySummary.getExpense());
        for (int i=0;i<dailySummary.getmBudgets().size();i++){
            Budget budget= dailySummary.getmBudgets().get(i);
            View item = LayoutInflater.from(mContext).inflate(R.layout.budget_item,null);
            TextView typeTv = item.findViewById(R.id.type_tv);
            TextView numTv = item.findViewById(R.id.num_tv);
            String typenote = DBManger.getInstance(mContext).getBudgetTypeByID(budget.getBudegetTypeId());
            typeTv.setText(typenote);
            numTv.setText(budget.getNum());
            holer.mDetailViewGroup.addView(item);
        }
        return view;
    }

    class ViewHoler{
        TextView mDateTv;
        TextView mIncomeTv;
        TextView mExpenseTV;
        LinearLayout mDetailViewGroup;
    }

}
