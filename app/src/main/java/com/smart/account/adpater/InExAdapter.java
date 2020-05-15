package com.smart.account.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.smart.account.R;
import com.smart.account.bean.BudgetType;
import com.smart.account.data.DBManger;

import java.util.ArrayList;
import java.util.List;

public class InExAdapter extends BaseAdapter {

    Context mContext;
    List<BudgetType> mBudgetTypes = new ArrayList<>();

    public InExAdapter(Context mContext,List<BudgetType> mBudgetTypes){
        this.mContext = mContext;
        this.mBudgetTypes = mBudgetTypes;
    }

    @Override
    public int getCount() {
        return mBudgetTypes.size();
    }

    @Override
    public Object getItem(int i) {
        return mBudgetTypes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final BudgetType budgetType = mBudgetTypes.get(i);
        ViewHoler holer = null;
        if (view == null){
            holer = new ViewHoler();
            view = LayoutInflater.from(mContext).inflate(R.layout.budget_type_item,null);
            holer.mNote = (TextView) view.findViewById(R.id.type_note_tv);
            holer.mDeleteBtn = (Button) view.findViewById(R.id.delete_btn);
            view.setTag(holer);
        }else{
            holer = (ViewHoler) view.getTag();
        }
        holer.mNote.setText(budgetType.getNote());
        holer.mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除数据库数据
                DBManger.getInstance(mContext).deleteBudegetTypeById(budgetType.getBudegetTypeId());
                mBudgetTypes.remove(budgetType);
                notifyDataSetChanged();
            }
        });
        return view;
    }

    class ViewHoler{
        TextView mNote;
        Button mDeleteBtn;
    }
}
