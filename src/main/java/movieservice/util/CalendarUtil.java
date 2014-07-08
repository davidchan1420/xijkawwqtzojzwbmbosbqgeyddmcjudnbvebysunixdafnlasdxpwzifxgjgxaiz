package movieservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarUtil {

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd (EEEE)";
	public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String DEFAULT_TIME_FORMAT = "HH:mm";

	public static final String DEFAULT_ANDROID_DATE_FORMAT = "yyyy-M-d";
	public static final String DEFAULT_ANDROID_DATE_TIME_FORMAT = "yyyy-M-d H:m";

	public static void main(String[] args) {
	
		
		Calendar cal = CalendarUtil.getSystemCalendar();

//		If this month is 12 but the input month is 1, then set year to year + 1
		if (cal.get(Calendar.MONTH) == Calendar.DECEMBER && ("Jan".equalsIgnoreCase("Jan") || "01".equalsIgnoreCase("01") )) {
			
			cal.add(Calendar.YEAR, 1);
			System.out.println(cal.getTime());
		}
		
		Date date = getDateByString("11 14", "MM dd");
		System.out.println(date);

	}

	public static Calendar getSystemCalendar() {
		Calendar currCal = Calendar.getInstance(TimeZone.getDefault());
		// System.out.println("CalendarUtil ----- getSystemCalendar(), TimeZone:"+currCal.getTimeZone().getDisplayName()+", rtn Time:"+currCal.getTime()+", timeInMillis:"+currCal.getTimeInMillis());
		return currCal;
	}
	
//	public static Calendar getSystemCalendar(Locale locale){
//		
//		Calendar currCal = Calendar.getInstance(TimeZone.getDefault(), locale);
//		return currCal;
//	}

	public static Date getSystemDate() {
		return getSystemCalendar().getTime();
	}

	public static long getSystemTimestamp() {
		return getSystemCalendar().getTimeInMillis();
	}

	public static Calendar trimDayToMin(Date date) {
		Calendar c = getSystemCalendar();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}

	public static Calendar trimDayToMax(Date date) {
		Calendar c = getSystemCalendar();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c;
	}

	public static Calendar trimMonthToMin(Date date) {
		Calendar c = getSystemCalendar();
		c.setTime(date);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}

	public static Date trimMonthToMax(Date date) {
		Calendar c = getSystemCalendar();
		c.setTime(date);
		int maxDayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.set(Calendar.DATE, maxDayOfMonth);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}

	public static Date getDateByString(String dateString) {
		return getDateByString(dateString, DEFAULT_DATE_FORMAT);
	}

	public static Date getDateByString(String dateString, String dateFormat) {

		Calendar calendar= getCalendarByString(dateString, dateFormat);
		return calendar.getTime();
	}
	
	public static Calendar getCalendarByString(String dateString) {
		return getCalendarByString(dateString, DEFAULT_DATE_FORMAT);
	}
	
	public static Calendar getCalendarByString(String dateString, String dateFormat) {

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		try {
			Date date = sdf.parse(dateString);
			Calendar calendar = getSystemCalendar(); 
			calendar.setTime(date);
			return calendar;
			
		} catch (ParseException e) {
			return getSystemCalendar();
		}
	}

	public static String getFormatDateString(Calendar cal, String dateFormat) {

		return getFormatDateString(cal.getTime(), dateFormat);
	}
	
	public static String getFormatDateString(Calendar cal, String dateFormat, Locale locale) {

		return getFormatDateString(cal.getTime(), dateFormat, locale);		
	}

	public static String getFormatDateString(Date date, String dateFormat) {

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}
	
	public static String getFormatDateString(Date date, String dateFormat, Locale locale) {

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, locale);
		return sdf.format(date);
	}

	// public static String getDateStringNoTime(Calendar ca){
	// String formatStr = "";
	// String rtnDate = "";
	//
	// formatStr = "MMM dd, yyyy";
	//
	// DateFormat dateFormat = new SimpleDateFormat(formatStr);
	// rtnDate = dateFormat.format(ca.getTime());
	//
	// return rtnDate;
	// }

	// public static String getDateString(Calendar ca, boolean isShowSecond){
	// String formatStr = "";
	// String rtnDate = "";
	//
	// formatStr = "MMM dd, yyyy HH:mm";
	//
	// if(isShowSecond){
	// formatStr += ":ss";
	// }
	//
	// DateFormat dateFormat = new SimpleDateFormat(formatStr);
	// rtnDate = dateFormat.format(ca.getTime());
	//
	// return rtnDate;
	// }

	public static String getTimeZome(Calendar ca) {
		String formatStr = "Z";
		String timeZone = "GMT ";

		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		return timeZone + sdf.format(ca.getTime());

	}

}
