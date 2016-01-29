package com.wyz.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

public class Time {
	public final static String DEFAULT_PATTERN = "yyyy-MM-dd";
	public final static String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);

	public Time(String pattern) {
		sdf = new SimpleDateFormat(pattern);
	}

	public static final Timestamp now() {
		return new Timestamp(new Date().getTime());
	}

	public static String toString(Timestamp ts) {
		return ts != null ? toString(new Date(ts.getTime())) : StringUtils.EMPTY;
	}

	public static String toString(Date date) {
		return date != null ? sdf.format(date) : StringUtils.EMPTY;
	}

}
