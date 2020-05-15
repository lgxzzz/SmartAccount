package com.smart.account.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.smart.account.util.SharedPreferenceUtil;

public class SQLiteDbHelper extends SQLiteOpenHelper {

    //数据库名称
    public static final String DB_NAME = "VoiceBook.db";
    //数据库版本号
    public static int DB_VERSION = 24;
    //用户表
    public static final String TAB_USER = "UserInfo";
    //收支表
    public static final String TAB_BUDGET = "Budget";
    //收支类型表
    public static final String TAB_BUDGET_TYPE = "BudgetType";
    //创建all表
    public static final String TAB_BUDGET_ALL = "BudgetAll";
    //创建all表
    public static final String TAB_BUDGET_REMAINT = "BudgetYue";

    Context context;
    public SQLiteDbHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableUser(db);
        createTableBudget(db);
        createTableBudgetType(db);
        createTableAll(db);
        createTableRemain(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        SharedPreferenceUtil.setFirstTimeUse(true,context);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_USER);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_BUDGET);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_BUDGET_TYPE);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_BUDGET_ALL);
        db.execSQL("DROP TABLE IF EXISTS "+TAB_BUDGET_REMAINT);
        onCreate(db);
    }

    //创建用户表
    public void createTableUser(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_USER +
                "(m_phone varchar(60) primary key, " +
                "m_name varchar(60), " +
                "m_password varchar(60))");
    }

    //创建收支表
    public void createTableBudget(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_BUDGET +
                "(BudegetId integer primary key autoincrement, " +
                "UserId varchar(60), " +           //用户id
                "date varchar(60), " +           //日期
                "type varchar(60), " +           // 支出 收入
                "BudegetTypeId varchar(60), " +  //收支类型
                "note varchar(60), " +           //备注
                "num varchar(60))");             //金额
    }

    //创建收支类型表
    public void createTableBudgetType(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_BUDGET_TYPE +
                "(BudegetTypeId varchar(60) primary key, " +
                "UserId varchar(60), " +           //用户id
                "type varchar(60), " +   // 支出 收入
                "note varchar(60))");    // 服饰 购物等
    }

    //创建all表
    public void createTableAll(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_BUDGET_ALL +
                "(a_name varchar(60) primary key, " +
                "a_use varchar(60), " +   // 支出 收入
                "a_cost varchar(60), " +   // 支出 收入
                "a_time varchar(60))");    // 服饰 购物等
    }

    //创建余额表
    public void createTableRemain(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TAB_BUDGET_REMAINT +
                "(y_name varchar(60) primary key, " +
                "y_balance varchar(60))");
    }
}
