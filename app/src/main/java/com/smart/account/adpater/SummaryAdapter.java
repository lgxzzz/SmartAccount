package com.smart.account.adpater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.smart.account.R;
import com.smart.account.bean.Budget;
import com.smart.account.bean.DailySummary;
import com.smart.account.data.DBManger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/15.
 */
public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.Holder> {

    public List<DailySummary> mDailySummarys = new ArrayList<>();
    public Context mContext;
    public SummaryAdapter(Context mContext, List<DailySummary> mDailySummarys){
        this.mDailySummarys = mDailySummarys;
        this.mContext = mContext;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_recyclerview, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final DailySummary dailySummary = this.mDailySummarys.get(position);
        holder.mDateTv.setText(dailySummary.getDate());
        holder.mIncomeTv.setText("收入:"+dailySummary.getIncome());
        holder.mExpenseTV.setText("支出:"+dailySummary.getExpense());
        for (int i=0;i<dailySummary.getmBudgets().size();i++){
            Budget budget= dailySummary.getmBudgets().get(i);
            View item = LayoutInflater.from(mContext).inflate(R.layout.budget_item,null);
            TextView typeTv = item.findViewById(R.id.type_tv);
            TextView numTv = item.findViewById(R.id.num_tv);
            String typenote = DBManger.getInstance(mContext).getBudgetTypeByID(budget.getBudegetTypeId());
            typeTv.setText(typenote);
            numTv.setText(budget.getNum());
            holder.mDetailViewGroup.addView(item);
        }
    }


    @Override
    public int getItemCount() {
        return mDailySummarys.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView mDateTv;
        TextView mIncomeTv;
        TextView mExpenseTV;
        public LinearLayout mDetailViewGroup;
        public LinearLayout llLayout;
        public TextView tvDelete;
        public TextView tvTop;

        public Holder(View itemView) {
            super(itemView);

            mDateTv = (TextView) itemView.findViewById(R.id.date_tv);
            mIncomeTv = (TextView) itemView.findViewById(R.id.income_tv);
            mExpenseTV = (TextView) itemView.findViewById(R.id.expense_tv);
            mDetailViewGroup= (LinearLayout) itemView.findViewById(R.id.detail_viewgroup);
            llLayout= (LinearLayout) itemView.findViewById(R.id.llLayout);
            tvDelete = (TextView) itemView.findViewById(R.id.tvDelete);
            tvTop = (TextView) itemView.findViewById(R.id.tvTop);
        }

    }

}