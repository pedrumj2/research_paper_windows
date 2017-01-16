/**
 * Created by Pedrum on 1/14/2017.
 */
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Calendar;

public class Dest {
    public Time time;
    public String attack;
    public String name;
    public Calendar date;

    public Dest(String __line)  {
        int _day;
        java.util.Date _DayZero;
        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        String[] parts = __line.split(",");
        time = Time.valueOf(parts[3]);
        attack = (parts[2]);
        name = (parts[5]);
        _day = Integer.parseInt(parts[0]);
        date = getDate(_day);
    }

    public String getDateTime(){
        String _output;
        _output = String.valueOf(date.get(Calendar.YEAR)) + "-" +
                    String.valueOf(date.get(Calendar.MONTH)+1) + "-" +
                    String.valueOf(date.get(Calendar.DAY_OF_MONTH)) + " " +
                    String.valueOf(time);
        return _output;

    }
    public Calendar getDate(int _day){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 1998);
        cal.set(Calendar.MONTH, 6-1);
        cal.set(Calendar.DAY_OF_MONTH, _day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }



}
