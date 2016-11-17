package utils;

import com.mi.ten.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import bean.TENDate;

/**
 * Created by 暗示语 on 2016/11/16.
 */
/**
 * 这是一个获取当前的时间和星期几的图片的帮助类
 * */
public class MyGetDate {
    private static int[] dates={R.drawable.date_0,R.drawable.date_1,R.drawable.date_2,R.drawable.date_3,
            R.drawable.date_4,R.drawable.date_5,R.drawable.date_6,R.drawable.date_7,R.drawable.date_8,
            R.drawable.date_9};//月份对应的日期
    private static int[] months={R.drawable.month_1,R.drawable.month_2,R.drawable.month_3,
            R.drawable.month_4,R.drawable.month_5,R.drawable.month_6,R.drawable.month_7,R.drawable.month_8,
            R.drawable.month_9,R.drawable.month_10,R.drawable.month_11,R.drawable.month_12};//获得月份
    private static int[] weeks={R.drawable.week_7,R.drawable.week_1,R.drawable.week_2,R.drawable.week_3,R.drawable.week_4,
            R.drawable.week_5,R.drawable.week_6};

public static TENDate getTenDate(long data)//获取指定的图片设置时间 星期
{
    int WeeksPic=GetWeekPic(getWeek(data));//得到时间对应的星期几的图片
    int MonthsPic=GetMonthPic(GetMonthFrom(getStringDataShort(data)));
    int[] DatesPic=GetDatesPic( GetDateFrom(getStringDataShort(data)));
    return new TENDate(MonthsPic,DatesPic[0],DatesPic[1],WeeksPic);
}


    private static String getStringDataShort(long data)//获取当前的日期年月日 参数为要传入的一个long值时间
    {
        Date date=new Date(data);
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
    private static   int getWeek(long data)//获取当前的日期星期几 参数为要传入的一个long值时间  返回的星期为1 2 3 4 5 6 7 星期日到星期六
    {
        Calendar calendar= Calendar.getInstance();
        Date date=new Date(data);
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
   private static int GetMonthPic(int Month)//获取月份对应的图片
   {
       return  months[Month-1];
   }
    private static int[] GetDatesPic(int date) {//获取日期对应的图片数组
        int[] das=new int[2];
        if (date < 10) {
            das[0]=dates[0];
            das[1]=dates[date];
        } else {
           das[0]=dates[GetSpiltData(date)[0]];
            das[1]=dates[GetSpiltData(date)[1]];
        }
        return das;
    }
    private static int GetWeekPic(int Weeks) {//获取星期几的图片
        return weeks[Weeks-1];
    }
    private static  int GetMonthFrom(String date) {//获取月份
        return   Integer.parseInt(date.split("-")[1]);
    }
    private static int GetDateFrom(String date) {//获取日期
        return Integer.parseInt(date.split("-")[2]);
    }
    private static int[] GetSpiltData(int date) {
        int[] nums=new int[2];
        nums[0]=Integer.parseInt(((date+"").charAt(0))+"");
        nums[1]=Integer.parseInt(((date+"").charAt(1))+"");
        return nums;
    }
}
