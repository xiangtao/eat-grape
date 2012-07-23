package com.eatle.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author xt
 */
public class DateUtil {
     private static Log log = LogFactory.getLog(DateUtil.class);
     private static final String TIME_PATTERN = "HH:mm";
     private static Map<Integer, Integer> daysOfEveryMonth = new HashMap<Integer, Integer>();

     static {
          daysOfEveryMonth.put(0, 31);
          daysOfEveryMonth.put(2, 31);
          daysOfEveryMonth.put(3, 30);
          daysOfEveryMonth.put(4, 31);
          daysOfEveryMonth.put(5, 30);
          daysOfEveryMonth.put(6, 31);
          daysOfEveryMonth.put(7, 31);
          daysOfEveryMonth.put(8, 30);
          daysOfEveryMonth.put(9, 31);
          daysOfEveryMonth.put(10, 30);
          daysOfEveryMonth.put(11, 31);
     }

     /**
      * Checkstyle rule: utility classes should not have public constructor
      */
     private DateUtil() {
     }

     /**
      * Return default datePattern (MM/dd/yyyy)
      * 
      * @return a string representing the date pattern on the UI
      */
     public static String getDatePattern() {
          /*
           * Locale locale = LocaleContextHolder.getLocale(); String defaultDatePattern; try { defaultDatePattern = ResourceBundle.getBundle( "ApplicationResources", locale).getString("date.format"); } catch (MissingResourceException mse) { defaultDatePattern = "MM/dd/yyyy"; }
           * 
           * return defaultDatePattern;
           */
          return "yyyy-MM-dd";
     }

     public static String getDateTimePattern() {
          return DateUtil.getDatePattern() + " HH:mm:ss.S";
     }

     /**
      * This method attempts to convert an Oracle-formatted date in the form dd-MMM-yyyy to mm/dd/yyyy.
      * 
      * @param aDate
      *             date from database as a string
      * @return formatted string for the ui
      */
     public static String getDate(Date aDate) {
          SimpleDateFormat df;
          String returnValue = "";

          if (aDate != null) {
               df = new SimpleDateFormat(getDatePattern());
               returnValue = df.format(aDate);
          }

          return (returnValue);
     }

     /**
      * 取下一天
      * 
      * @param aDate
      * @return
      */
     public static String getNextDate(Date aDate) {
          Calendar cal = Calendar.getInstance();
          cal.setTime(aDate);
          cal.add(Calendar.DAY_OF_YEAR, 1);
          SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
          return df.format(cal.getTime());
     }

     /**
      * This method generates a string representation of a date/time in the format you specify on input
      * 
      * @param aMask
      *             the date pattern the string is in
      * @param strDate
      *             a string representation of a date
      * @return a converted Date object
      * @see java.text.SimpleDateFormat
      * @throws ParseException
      *              when String doesn't match the expected format
      */
     public static Date convertStringToDate(String aMask, String strDate) throws ParseException {
          SimpleDateFormat df;
          Date date;
          df = new SimpleDateFormat(aMask);

          if (log.isDebugEnabled()) {
               log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
          }

          try {
               date = df.parse(strDate);
          } catch (ParseException pe) {
               // log.error("ParseException: " + pe);
               throw new ParseException(pe.getMessage(), pe.getErrorOffset());
          }

          return (date);
     }

     /**
      * This method returns the current date time in the format: MM/dd/yyyy HH:MM a
      * 
      * @param theTime
      *             the current time
      * @return the current date/time
      */
     public static String getTimeNow(Date theTime) {
          return getDateTime(TIME_PATTERN, theTime);
     }

     /**
      * This method returns the current date in the format: MM/dd/yyyy
      * 
      * @return the current date
      * @throws ParseException
      *              when String doesn't match the expected format
      */
     public static Calendar getToday() throws ParseException {
          Date today = new Date();
          SimpleDateFormat df = new SimpleDateFormat(getDatePattern());

          // This seems like quite a hack (date -> string -> date),
          // but it works ;-)
          String todayAsString = df.format(today);
          Calendar cal = new GregorianCalendar();
          cal.setTime(convertStringToDate(todayAsString));

          return cal;
     }

     /**
      * This method generates a string representation of a date's date/time in the format you specify on input
      * 
      * @param aMask
      *             the date pattern the string is in
      * @param aDate
      *             a date object
      * @return a formatted string representation of the date
      * 
      * @see java.text.SimpleDateFormat
      */
     public static String getDateTime(String aMask, Date aDate) {
          SimpleDateFormat df = null;
          String returnValue = "";

          if (aDate == null) {
               log.error("aDate is null!");
          } else {
               df = new SimpleDateFormat(aMask);
               returnValue = df.format(aDate);
          }

          return (returnValue);
     }

     public static String getTimeText(long time) {
          return (time / 1000 / 60) + "分," + (time / 1000 % 60) + "秒 ," + (time % 1000) + "毫秒 ";
     }

     /**
      * This method generates a string representation of a date based on the System Property 'dateFormat' in the format you specify on input
      * 
      * @param aDate
      *             A date to convert
      * @return a string representation of the date
      */
     public static String convertDateToString(Date aDate) {
          return getDateTime(getDatePattern(), aDate);
     }

     /**
      * This method converts a String to a date using the datePattern
      * 
      * @param strDate
      *             the date to convert (in format MM/dd/yyyy)
      * @return a date object
      * @throws ParseException
      *              when String doesn't match the expected format
      */
     public static Date convertStringToDate(String strDate) throws ParseException {
          Date aDate = null;

          try {
               if (log.isDebugEnabled()) {
                    log.debug("converting date with pattern: " + getDatePattern());
               }

               aDate = convertStringToDate(getDatePattern(), strDate);
          } catch (ParseException pe) {
               log.error("Could not convert '" + strDate + "' to a date, throwing exception");
               pe.printStackTrace();
               throw new ParseException(pe.getMessage(), pe.getErrorOffset());
          }

          return aDate;
     }

     /**
      * 判断给定日期是否为当天，
      * 
      * 距离当前时间七天之内的日期，和七天之外的日期
      * 
      * @param dt
      * @param type
      *             0--当天 1--7天之内的 2--7天之外的
      * @return
      */
     public static boolean getDayDiffFromToday(Date dt, int type) {
          Calendar today = Calendar.getInstance();
          today.set(Calendar.HOUR_OF_DAY, 23);
          today.set(Calendar.MINUTE, 59);
          today.set(Calendar.SECOND, 59);

          long diff = today.getTimeInMillis() - dt.getTime();
          if (diff < 0)
               diff = 0;
          long days = diff / (1000 * 60 * 60 * 24);

          if (type == 0 && days == 0)
               return true;

          if (type == 1 && days > 0 && days <= 7)
               return true;

          if (type == 2 && days > 7)
               return true;

          return false;
     }

     public static String getBetween(long time) {
          return getBetween(new Date(time));
     }

     public static String getBetween(Date time) {
          Calendar c = Calendar.getInstance();

          long timethis = c.getTimeInMillis();
          c.setTime(time);
          long timestart = c.getTimeInMillis();

          long t = timethis - timestart;

          long day = t / ((long) 1 * 1000 * 60 * 60 * 24);
          long hour = (t % ((long) 1 * 1000 * 60 * 60 * 24)) / (1 * 1000 * 60 * 60);
          long mini = ((t % ((long) 1 * 1000 * 60 * 60 * 24)) % (1 * 1000 * 60 * 60)) / (1 * 1000 * 60);

          StringBuffer str = new StringBuffer();
          if (day != 0) {
               str.append(toTimestamp(time));
          } else if (hour != 0) {
               str.append(hour + "小时前");
          } else if (mini != 0) {
               str.append(mini + "分钟前");
          } else {
               str.append("刚才");
          }
          // System.out.println(day + "天 " + hour + "小时 " + mini + "分钟 前 ");
          return str.toString();
     }

     public static String toTimestamp(Date dt) {
          final SimpleDateFormat dtFormater = new SimpleDateFormat("yyyy-MM-dd");
          if (dt != null) {
               return dtFormater.format(dt);
          }
          return "";
     }

     /**
      * 转换时间成差值的方式<br>
      * 
      * 例如时间是：2008-08-08 11:15:00<br>
      * 当前时间是：2008-08-08 11:16:00<br>
      * 得出的结果是：1分钟之前
      * 
      * 例如时间是：2008-08-08 11:15:00<br>
      * 当前时间是：2008-08-09 11:16:00<br>
      * 得出的结果是：1天之前
      * 
      * 例如时间是：2008-08-08 11:15:00<br>
      * 当前时间是：2008-08-08 21:16:00<br>
      * 得出的结果是：10小时之前
      */
     public static String diffNowTime(Date date) {
          if (date == null)
               return "";
          long now = new Date().getTime();
          long test = date.getTime();
          long span = now - test;
          if (span < 0) {
               return "未来";
          }
          for (int i = 0; i < spans.length; i++) {
               if (span >= spans[i]) {
                    return (span / spans[i]) + units[i];
               }
          }
          return units[units.length - 1];
     }

     private static long[] spans = {//
     //
               86400000,// 一天的毫秒
               3600000,// 一小时的毫秒
               60000 // 一分钟的毫秒
     };
     private static String[] units = {//
     //
               "天之前",// 天
               "小时之前",// 小时
               "分钟之前",// 分钟
               "刚刚"//
     };
     static String[] normalDays = new String[] {//
     //
               "前天",//
               "昨天",//
               "今天",//
               "明天",//
               "后天"//
     };

     /**
      * 转换时间成生日差值的方式<br>
      * 
      * 例如生日时间是：2008-08-08<br>
      * 当前时间是：2008-08-08<br>
      * 得出的结果是：今天
      * 
      * 例如时间是：2008-08-08<br>
      * 当前时间是：2008-08-09<br>
      * 得出的结果是：昨天
      * 
      * 例如时间是：2008-08-08<br>
      * 当前时间是：2008-08-10<br>
      * 得出的结果是：前天
      * 
      * 例如时间是：2008-08-08<br>
      * 当前时间是：2008-08-07<br>
      * 得出的结果是：明天
      * 
      * 例如时间是：2008-08-08<br>
      * 当前时间是：2008-08-06<br>
      * 得出的结果是：后天
      * 
      * 例如时间是：2008-08-08<br>
      * 当前时间是：2008-08-05<br>
      * 得出的结果是：8月5日
      */
     public static String diffBirthTime(Date date) {
          if (date == null)
               return "";
          Calendar today = Calendar.getInstance();
          today.set(Calendar.HOUR, 0);
          today.set(Calendar.MINUTE, 0);
          today.set(Calendar.SECOND, 0);

          Calendar test = Calendar.getInstance();
          test.setTime(date);
          test.set(Calendar.HOUR, 0);
          test.set(Calendar.MINUTE, 0);
          test.set(Calendar.SECOND, 0);

          long compare = (test.getTimeInMillis() - today.getTimeInMillis()) / 1000;

          int index = (int) compare / 86400;
          index += 2;
          if (index >= 0 && index < normalDays.length) {
               return normalDays[index];
          }

          SimpleDateFormat abnormalDayFmt = new SimpleDateFormat("yyyy-MM-dd");
          return abnormalDayFmt.format(date);
     }

     /**
      * 计算两个时间之间的天数
      * 
      * @param beginDate
      * @param endDate
      * @return
      */
     public static int dateDiff(Date beginDate, Date endDate) {
          if (beginDate == null || endDate == null)
               return 0;
          Calendar begin = Calendar.getInstance();
          begin.setTime(beginDate);

          Calendar end = Calendar.getInstance();
          end.setTime(endDate);

          long compare = (end.getTimeInMillis() - begin.getTimeInMillis()) / 1000;

          int days = (int) Math.ceil((double) compare / 86400);
          return days;
     }

     public static int dateDiff(String beginDate, String endDate) {
          if (beginDate == null || endDate == null)
               return 0;
          Calendar begin = Calendar.getInstance();
          Calendar end = Calendar.getInstance();
          try {
               begin.setTime(convertStringToDate(beginDate));
               end.setTime(convertStringToDate(endDate));
          } catch (ParseException e) {
               e.printStackTrace();
               return 0;
          }
          return dateDiff(begin.getTime(), end.getTime());
     }

     /**
      * @author chenli 计算两个时间中间的分钟数
      * @param beginTime
      * @param endTime
      * @return
      */
     public static int timeDiff(String beginTime, String endTime) {
          SimpleDateFormat df = new SimpleDateFormat("HH:mm");
          Date begin = null;
          Date end = null;
          try {
               begin = df.parse(beginTime);
               end = df.parse(endTime);
          } catch (ParseException e) {
               // TODO Auto-generated catch block
               log.error("时间字符串转换出错！");
               e.printStackTrace();
          }
          long between = (end.getTime() - begin.getTime()) / 1000;
          int hour = (int) (between % (24 * 3600) / 3600);
          int minute = (int) (between % 3600 / 60);
          int compare = hour * 60 + minute;
          return compare;
     }

     public static String[] getSundayList(String beginDate, String endDate) {

          List<Date> weeks = new ArrayList<Date>();
          Date begin, end;
          try {
               begin = convertStringToDate("yyyy-MM-dd", beginDate);
               end = convertStringToDate("yyyy-MM-dd", endDate);
          } catch (ParseException e) {
               log.error("时间字符串转换出错！");
               e.printStackTrace();
               return new String[0];
          }
          Calendar bCal = Calendar.getInstance();
          Calendar eCal = (Calendar) bCal.clone();
          bCal.setTime(begin);
          eCal.setTime(end);
          // 得到begin是星期几
          int bWeek = bCal.get(Calendar.DAY_OF_WEEK);
          weeks.add(begin);
          // 得到begin后第一个星期日
          Date firstSunday = new Date((begin.getTime() + 86400000l * (8 - (bWeek % 7))));

          while (firstSunday.compareTo(end) < 0) {
               weeks.add(firstSunday);
               firstSunday = new Date(firstSunday.getTime() + (86400000 * 7));
          }
          weeks.add(end);

          String[] sundays = new String[weeks.size()];
          for (int i = 0; i < weeks.size(); i++) {
               Date sunday = weeks.get(i);
               sundays[i] = DateUtil.convertDateToString(sunday);
          }
          return sundays;
     }

     public static String[] getMonthEndList(String beginDate, String endDate) {
          List<Date> monthList = new ArrayList<Date>();
          Date begin, end;
          try {
               begin = DateUtil.convertStringToDate("yyyy-MM-dd", beginDate);
               end = DateUtil.convertStringToDate("yyyy-MM-dd", endDate);
          } catch (ParseException e) {
               System.out.println("时间格式出错！");
               e.printStackTrace();
               return new String[0];
          }
          Calendar bCal = Calendar.getInstance();
          bCal.setTime(begin);

          // 得到月底日期
          monthList.add(begin);

          while (bCal.getTime().compareTo(end) < 0) {
               bCal = getLastDayOfMonth(bCal);
               if (bCal.getTime().compareTo(end) < 0) {
                    if (bCal.getTime().compareTo(begin) == 0) {
                         bCal.setTimeInMillis(bCal.getTimeInMillis() + 86400000);
                         continue;
                    } else {
                         monthList.add(bCal.getTime());
                         bCal.setTimeInMillis(bCal.getTimeInMillis() + 86400000);
                    }
               } else {
                    break;
               }
          }
          monthList.add(end);
          String[] months = new String[monthList.size()];
          for (int i = 0; i < monthList.size(); i++) {
               months[i] = DateUtil.convertDateToString(monthList.get(i));
          }
          return months;
     }

     /**
      * 通过一个日期，得到这个日期的月底日期
      * 
      * @param cal
      *             给出的日期
      * @return 这个月月底底的那一天
      */
     private static Calendar getLastDayOfMonth(Calendar cal) {
          int februaryDays = 28;
          int year = cal.get(Calendar.YEAR);
          boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
          if (isLeapYear) {
               februaryDays = 29;
          }
          daysOfEveryMonth.put(1, februaryDays);

          int bmonth = cal.get(Calendar.MONTH);
          int dayCount = daysOfEveryMonth.get(bmonth);
          int mod = cal.get(Calendar.DAY_OF_MONTH);
          // 如果开始就是月末
          if (cal.get(Calendar.DAY_OF_MONTH) % dayCount == 0) {
               return cal;
          } else {
               int needDays = dayCount - (mod % dayCount);
               Long needM = 86400000 * (long) needDays;
               cal.setTimeInMillis(cal.getTimeInMillis() + needM);
               return cal;
          }
     }

     /**
      * 取两个日期间的所有日期，返回字符串数组，返回格式为yyyy-MM-dd
      * 
      * @param beginDate
      * @param endDate
      * @return
      */
     public static String[] getDates(String beginDate, String endDate, String pattern) {
          SimpleDateFormat dtFormater = new SimpleDateFormat(pattern);
          int dayCount = dateDiff(beginDate, endDate) + 1;
          Date begin = new Date();
          try {
               begin = convertStringToDate(beginDate);
          } catch (ParseException e) {
               e.printStackTrace();
          }
          String[] dates = new String[dayCount];
          for (int i = 0; i < dayCount; i++) {
               Calendar cal = Calendar.getInstance();
               cal.setTime(begin);
               cal.add(Calendar.DAY_OF_YEAR, i);
               dates[i] = dtFormater.format(cal.getTime());
          }
          return dates;
     }

     /**
      * 返回给定的日期加上或减去天数后的时间
      * 
      * @param date
      *             指定日期
      * @param dayCount
      *             要添加或减去的天数,>0表示几天后，<0表示几天前
      * @return
      */
     public static String dateAdd(String date, int dayCount) {
          Date date1 = null;
          try {
               date1 = DateUtil.convertStringToDate("yyyy-MM-dd", date);
               return convertDateToString(dateAdd(date1, dayCount));
          } catch (ParseException e) {
               e.printStackTrace();
               return null;
          }
     }

     public static Date dateAdd(Date date, int dayCount) {
          Calendar cal = Calendar.getInstance();
          cal.setTime(date);
          cal.add(Calendar.DAY_OF_YEAR, dayCount);
          return cal.getTime();
     }

     /**
      * 判断传入的时间是否是day天之内
      */
     public static Boolean isBetweenDay(Date date, Integer days) {
          Date now = new Date();
          Date oldDate = dateAdd(now, days);
          return date.getTime() >= oldDate.getTime();
     }

     public static final Date getNextMonth(Date date) {
          Calendar cal = Calendar.getInstance();
          cal.setTime(date);
          cal.add(Calendar.MONTH, 1);
          return cal.getTime();
     }

     public static final Date getPreviewMonth(Date date) {
          Calendar cal = Calendar.getInstance();
          cal.setTime(date);
          cal.add(Calendar.MONTH, -1);
          return cal.getTime();
     }

     /**
      * 清空小时以下的单位为0
      */
     public static final Date clearTime(Date date) {
          Calendar cal = Calendar.getInstance();
          cal.setTime(date);
          cal.set(Calendar.HOUR_OF_DAY, 0);
          cal.set(Calendar.MINUTE, 0);
          cal.set(Calendar.SECOND, 0);
          cal.set(Calendar.MILLISECOND, 0);
          return cal.getTime();
     }

     public static Long DatetoLong(String strDate) {

          if (StringUtils.isEmpty(strDate)) {
               return null;
          }

          if (strDate.equals("0")) {
               return new Long(0);
          }

          try {
               java.text.SimpleDateFormat sfarmat = new java.text.SimpleDateFormat("yyyy-MM-dd");
               java.util.Date date = sfarmat.parse(strDate);

               return date.getTime() / 1000;
          } catch (Exception e) {
               return null;
          }
     }

     public static Long DatetoLong(String strDate, String format) {

          if (StringUtils.isEmpty(strDate)) {
               return null;
          }

          try {
               java.text.SimpleDateFormat sfarmat = new java.text.SimpleDateFormat(format);
               java.util.Date date = sfarmat.parse(strDate);

               return date.getTime() / 1000;
          } catch (Exception e) {
               return null;
          }
     }

     public static Long DatetoLong(Date ndate, String format) {

          if (ndate == null) {
               return null;
          }

          try {
               java.text.SimpleDateFormat sfarmat = new java.text.SimpleDateFormat(format);
               java.util.Date date = sfarmat.parse(sfarmat.format(ndate));

               return date.getTime() / 1000;
          } catch (Exception e) {
               return null;
          }
     }

     public static String LongtoDate(String longString, String format) {
          try {
               if (longString == null || "".equals(longString.trim()) || "0".equals(longString.trim()))
                    return "";
               long llong = Long.parseLong(longString);
               java.util.Date dlong = new java.util.Date();
               dlong.setTime(llong * 1000);
               java.text.SimpleDateFormat sfarmat = new java.text.SimpleDateFormat(format);
               String strDateTime = sfarmat.format(dlong);
               return strDateTime;
          } catch (Exception e) {
               System.out.println(e);
               return "";
          }
     }

     public static String LongtoDateOrign(String longString, String format) {
          try {
               if (longString == null || "".equals(longString.trim()))
                    return "";
               long llong = Long.parseLong(longString);
               java.util.Date dlong = new java.util.Date();
               dlong.setTime(llong * 1000);
               java.text.SimpleDateFormat sfarmat = new java.text.SimpleDateFormat(format);
               String strDateTime = sfarmat.format(dlong);
               return strDateTime;
          } catch (Exception e) {
               System.out.println(e);
               return "";
          }
     }

     /**
      * 取得当天是星期几
      */
     public static String getWeek() {
          String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
          Calendar calendar = Calendar.getInstance();
          Date date = new Date();
          calendar.setTime(date);
          int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
          if (dayOfWeek < 0) {
               dayOfWeek = 0;
          }
          return dayNames[dayOfWeek];
     }

     public static void main(String[] args) {
          // System.out.println(LongtoDate("1313078400","yyyy-MM-dd"));
          Calendar c = Calendar.getInstance();

          // c.set(2036,1,1);
          c.set(2012, 2, 1, 0, 0, 0);

          // System.out.println(DateUtil.DatetoLong(c.getTime(),"HH:mm"));
          System.out.println(getDate(new Date()));
          // System.out.println(LongtoDate("61341419624","yyyy-MM-dd"));
     }
}
