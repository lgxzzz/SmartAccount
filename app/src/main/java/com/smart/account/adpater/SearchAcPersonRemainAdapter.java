package com.smart.account.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.smart.account.R;
import com.smart.account.bean.AccountPerson;
import com.smart.account.bean.BudgetType;
import com.smart.account.data.DBManger;

import java.util.ArrayList;
import java.util.List;

public class SearchAcPersonRemainAdapter extends BaseAdapter {

    Context mContext;
    List<AccountPerson> mAccountPersons = new ArrayList<>();

    public SearchAcPersonRemainAdapter(Context mContext, List<AccountPerson> mAccountPersons){
        this.mContext = mContext;
        this.mAccountPersons = mAccountPersons;
    }

    @Override
    public int getCount() {
        return mAccountPersons.size();
    }

    @Override
    public Object getItem(int i) {
        return mAccountPersons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final AccountPerson person = mAccountPersons.get(i);
        ViewHoler holer = null;
        if (view == null){
            holer = new ViewHoler();
            view = LayoutInflater.from(mContext).inflate(R.layout.account_person_item,null);
            holer.mPersonName = (TextView) view.findViewById(R.id.person_tv);
            holer.mRemain = (TextView) view.findViewById(R.id.num_tv);
            view.setTag(holer);
        }else{
            holer = (ViewHoler) view.getTag();
        }
        holer.mPersonName.setText(person.getName());
        holer.mRemain.setText(person.getBalance());

        return view;
    }

    class ViewHoler{
        TextView mPersonName;
        TextView mRemain;
    }
}
