package org.openmore.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil extends DateUtils {

    public static final String FULL_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_SPRIT_PATTERN = "yyyy/MM/dd";
    public static final String DAY_MONTH_YEAR = "dd/MM/yyyy";
    public static final String DATE_HOUR_PATTERN = "yyyy-MM-dd HH";
    public static final String DATE_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String NOT_SEPARATOR_DATE_PATTERN = "yyyyMMdd";
    public static final String DATE_MONTH_PATTERN = "yyyy-MM";

    /**
     * cron表达式格式
     */
    public static final String DATE_CRON_PATTERN = "ss mm HH dd MM ? yyyy";

    public static String dateToString(String pattern, Date date) {
        if (null == date) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static Date stringToDate(String pattern, String dateStr) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        Date date = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            date = formatter.parse(dateStr);
        } catch (Exception e) {
        }
        return date;
    }

    public static String currenDateToString() {
        return dateToString(DateUtil.FULL_TIME_PATTERN, new Date());
    }

    public static String currenDateToString(String pattern) {
        return dateToString(pattern, new Date());
    }

    public static Date formatDate(String pattern, String sDate) {
        Date date = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

        try {
            date = dateFormat.parse(sDate);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return date;
    }

    public static int betweenDay(Date start, Date end) {
        long temEnd = end.getTime();
        long temStart = start.getTime();
        long betweenDays = (temEnd - temStart) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(betweenDays));
    }

    /**
     * 获得当前日期的0点整
     *
     * @param date
     * @return
     */
    public static Date getZeroClockOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取一周第一天的时间（以星期一为一周第一天）
     */
    public static Date getWeekFirstTime() {
        Calendar cal = Calendar.getInstance();
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;
        }
        cal.add(Calendar.DATE, -dayOfWeek + 1);
        return cal.getTime();

    }

    /**
     * 获取给定日期的前月第一天（isDayBreak:是否返回凌晨时间凌晨）
     */
    public static Date getLastMonthFirstDay(Date time, boolean isDayBreak) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1); //设置为1号,当前日期既为本月第一天
        if (isDayBreak) {
            return getZeroClockOfDate(c.getTime());
        }
        return c.getTime();
    }

    /**
     * 获取给定日期的当前月第一天（isDayBreak:是否返回凌晨时间凌晨）
     */
    public static Date getMonthFirstDay(Date time, boolean isDayBreak) {
        Calendar c = Calendar.getInstance();
        c.setTime(time);
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1); //设置为1号,当前日期既为本月第一天
        if (isDayBreak) {
            return getZeroClockOfDate(c.getTime());
        }
        return c.getTime();
    }

    public static String getCron(Date date) {
        return dateToString(DATE_CRON_PATTERN, date);
    }

    /*

    获取当前时间之前或之后几小时 hour

   */

    public static Date getTimeByHour(Date date, int hour) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);

        return calendar.getTime();

    }

    /*

    获取当前时间之前或之后几分钟 minute

    */

    public static Date getTimeByMinute(Date date, int minute) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);

        return calendar.getTime();

    }
}
