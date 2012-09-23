package com.sertice.plugins.jenkins.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public final class DateUtil {

	private static final SimpleDateFormat formatter = new SimpleDateFormat(
			"GGGGy年MM月dd日", new Locale("ja", "JP", "JP"));

	public static String today() {
		Calendar calendar = Calendar.getInstance();
		return formatter.format(calendar.getTime());
	}

}
