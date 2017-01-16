/**
 * Created by Pedrum on 1/15/2017.
 */
import java.util.Calendar;

public class TimeInterval {
    private Calendar startTime;
    private Calendar endTime;
    private  int INTERVAL = 1;
    private WEEKDAY weekday;
    private enum WEEKDAY{
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY

    }
    public TimeInterval(){
        startTime = Calendar.getInstance();
        endTime = Calendar.getInstance();
        startTime.set(Calendar.YEAR, 1998);
        startTime.set(Calendar.MONTH, 6-1);
        startTime.set(Calendar.DAY_OF_MONTH, 1);
        startTime.set(Calendar.HOUR_OF_DAY, 8);
        startTime.set(Calendar.MINUTE, 0);
        startTime.set(Calendar.SECOND, 0);
        startTime.set(Calendar.MILLISECOND, 0);

        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.MINUTE, INTERVAL);

        weekday = WEEKDAY.MONDAY;
    }
    public void increment(){
        int _hour;

        _hour = endTime.get(Calendar.HOUR_OF_DAY);
        //if its the end of the day increment ot the next day
        if (_hour == 20){
            startTime.add(Calendar.DATE, 1);
            startTime.set(Calendar.HOUR_OF_DAY, 8);
            startTime.set(Calendar.MINUTE, 0);

            //if its not the end of the week increment week day
            if (weekday != WEEKDAY.FRIDAY) {
                weekday = WEEKDAY.values()[weekday.ordinal() + 1];
            }
            else{
                //if its the end of the week inccrement to monday
                weekday = WEEKDAY.MONDAY;
                //going from saturday to monday
                startTime.add(Calendar.DATE, 2);
            }
        }
        else{
            startTime.add(Calendar.MINUTE, INTERVAL);

        }

        endTime = (Calendar) startTime.clone();
        endTime.add(Calendar.MINUTE, INTERVAL);
    }

    public String getStartTime(){
        String _output;
        _output = String.valueOf(startTime.get(Calendar.YEAR)) + "-" +
                String.valueOf(startTime.get(Calendar.MONTH)+1) + "-" +
                String.valueOf(startTime.get(Calendar.DAY_OF_MONTH)) + " " +
                String.valueOf(startTime.get(Calendar.HOUR_OF_DAY)) + ":" +
                String.valueOf(startTime.get(Calendar.MINUTE)) + ":" +
                String.valueOf(startTime.get(Calendar.SECOND));

        return _output;

    }


    public String getEndTime(){
        String _output;
        _output = String.valueOf(endTime.get(Calendar.YEAR)) + "-" +
                String.valueOf(endTime.get(Calendar.MONTH)+1) + "-" +
                String.valueOf(endTime.get(Calendar.DAY_OF_MONTH)) + " " +
                String.valueOf(endTime.get(Calendar.HOUR_OF_DAY)) + ":" +
                String.valueOf(endTime.get(Calendar.MINUTE)) + ":" +
                String.valueOf(endTime.get(Calendar.SECOND));

        return _output;

    }
}
