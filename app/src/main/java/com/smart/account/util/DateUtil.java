package com.smart.account.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = year+"."+month;
        return date;
    }

    public static String getCurrentDay(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = year+"."+month+"."+day;
        return date;
    }

    public static String getCurrentDayStr(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = year+"年"+month+"月"+day+"日";
        return date;
    }

    public static String getCurrentMonthStr(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date = year+"年"+month+"月";
        return date;
    }

    public static int[] getIntDay(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int[] date = new int[]{year,month,day};
        return date;
    }

    public static int getMonthOfDay(String date){
        String time = date.replace("年","-").replace("月","");
        String[] temp = time.split("-");
        int year = Integer.parseInt(temp[0]);
        int month = Integer.parseInt(temp[1]);
        int day = 0;
        if(year%4==0&&year%100!=0||year%400==0){
            day = 29;
        }else{
            day = 28;
        }
        switch (month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                return day;

        }

        return 0;
    }

    public static long dateToStamp(String str){
        try{
            String time = str.replace("年","-").replace("月","-").replace("日","-");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = simpleDateFormat.parse(time);
            long ts = date.getTime();
            return ts;
        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }

}
