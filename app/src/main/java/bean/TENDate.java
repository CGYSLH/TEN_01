package bean;

/**
 * Created by 暗示语 on 2016/11/16.
 */

public class TENDate {
    private int Month;
    private int Date_F;//日期的前字母
    private int Date_E;//日期的后字母
    private int Week;

    public TENDate(int month, int date_F, int date_E, int week) {
        Month = month;
        Date_F = date_F;
        Date_E = date_E;
        Week = week;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getDate_F() {
        return Date_F;
    }

    public void setDate_F(int date_F) {
        Date_F = date_F;
    }

    public int getDate_E() {
        return Date_E;
    }

    public void setDate_E(int date_E) {
        Date_E = date_E;
    }

    public int getWeek() {
        return Week;
    }

    public void setWeek(int week) {
        Week = week;
    }
}
