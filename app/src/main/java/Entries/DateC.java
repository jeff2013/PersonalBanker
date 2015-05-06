package Entries;

/**
 * Created by jeff on 2014-12-25.
 */
public class DateC {private int day;
    private int month;
    private int year;
    private String date;

    public DateC(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String dateString(DateC d){
        date = Integer.toString(d.getDay())+ "/" + Integer.toString(d.getMonth()) + "/" + Integer.toString(d.getYear());
        return date;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String toStringProper(){
        return String.format(day+"/"+month+"/"+year);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return String.format(day+","+month+","+year);
    }


}
