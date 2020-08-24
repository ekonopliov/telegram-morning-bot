package lt.kono.tmb.utils;

import java.util.Calendar;
import java.util.Date;


/**
 * Utility date providing class
 *
 * @author ekonopliov
 */
public class DateProvider {

    /**
     * Returns current date
     *
     * @return Date
     */
    public static Date getCurrentDate(){
        return new Date();
    }

    /**
     * Returns given date with added hours
     *
     * @return Date
     */
    public static Date addHours(Date date, int hours){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    /**
     * Returns given date with added seconds
     *
     * @return Date
     */
    public static Date addSeconds(Date date, int seconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * Returns given date with subtracted seconds
     *
     * @return Date
     */
    public static Date substractSeconds(Date date, int seconds){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds * - 1);
        return calendar.getTime();
    }
}
