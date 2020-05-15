package com.smart.account.bean;

import java.util.ArrayList;
import java.util.List;

public class DailySummary {
  String date;
  List<Budget> mBudgets = new ArrayList<>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Budget> getmBudgets() {
        return mBudgets;
    }

    public void setmBudgets(List<Budget> mBudgets) {
        this.mBudgets = mBudgets;
    }

    public int getIncome(){
        return getCountByBudgetType("收入");
    }

    public int getExpense(){
        return getCountByBudgetType("支出");
    };

    public int getCountByBudgetType(String type){
        int number = 0;
        for (int i=0;i<mBudgets.size();i++){
            Budget budget = mBudgets.get(i);
            String temp = budget.getType();
            if (temp.equals(type)){
                int num = Integer.parseInt(budget.getNum());
                number = number+num;
            }
        }
        return number;
    }
}
