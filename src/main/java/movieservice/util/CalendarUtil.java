package movieservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class CalendarUtil {

	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
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

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		try {
			Date date = sdf.parse(dateString);
			return date;
		} catch (ParseException e) {
			return getSystemCalendar().getTime();
		}
	}

	public static String getFormatDateString(Calendar cal, String dateFormat) {

		return getFormatDateString(cal.getTime(), dateFormat);
	}

	public static String getFormatDateString(Date date, String dateFormat) {

		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
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
