package com.c306.code.page.utils;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

public class ActionUtils {

	public static String EN = "en";
	public static String ZH = "zh";
	public static String WW_TRANS_I18N_LOCALE = "WW_TRANS_I18N_LOCALE";
	public static String REQUEST_LOCALE = "request_locale";
	public static String ZH_CN = "zh_CN";
	public static String EN_US = "en_US";

	/**
	 * 获取当前语言
	 * 
	 * @return
	 */
	public static String getCurrGlobal(HttpSession session) {
		Object object = session.getAttribute(WW_TRANS_I18N_LOCALE);
		return object == null ? ZH_CN : object.toString();
	}

	/**
	 * 中英文转换
	 * 
	 * @param cnStr
	 * @param usStr
	 * @param global
	 * @return
	 */
	public static String getLanStr(String cnStr, String usStr, String global) {
		if (ZH_CN.equals(global)) {
			return StringUtils.isNotBlank(cnStr) ? cnStr : usStr;
		} else if (EN_US.equals(global)) {
			return StringUtils.isNotBlank(usStr) ? usStr : cnStr;
		}
		return "";
	}
	
	public static Date getDate() {
		return (new GregorianCalendar()).getTime();
	}

}
