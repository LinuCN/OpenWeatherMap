package org.openweather.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtils {

    public static String changeDateFormat(String inFormat, String outFormat,
	    String dateString) {

	DateFormat inDateFormat = new SimpleDateFormat(inFormat);
	DateFormat outDateFormat = new SimpleDateFormat(outFormat);
	Date date = null;
	try {
	    date = inDateFormat.parse(dateString);
	} catch (ParseException e) {
	    e.printStackTrace();
	}
	if (date != null) {
	    return outDateFormat.format(date);
	}
	return dateString;

    }
}