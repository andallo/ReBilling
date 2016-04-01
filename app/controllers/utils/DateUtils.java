package controllers.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by andrey on 24.10.15.
 */
public class DateUtils {
    public static String getNowRussianFormat() {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(new Locale("ru")).format(LocalDate.now());
    }

    public static int getCurrentYear(){
        return getYear(new Date());
    }

    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year;
    }
}
