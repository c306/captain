package com.c306.utils;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;

public class RequestUtil {

	private final static int ENCODE_XORMASK = 0x5A;

	private final static char ENCODE_DELIMETER = '\002';

	private final static char ENCODE_CHAR_OFFSET1 = 'A';

	private final static char ENCODE_CHAR_OFFSET2 = 'h';

	/**
	 * 获得request的参数
	 * 
	 * @param aRequest
	 * @return
	 */
	public static String getRequestParameters(HttpServletRequest aRequest) {
		Map m = aRequest.getParameterMap();
		return createQueryStringFromMap(m, "&").toString();
	}

	public static StringBuffer createQueryStringFromMap(Map m, String ampersand) {
		StringBuffer aReturn = new StringBuffer("");
		Set aEntryS = m.entrySet();
		Iterator aEntryI = aEntryS.iterator();

		while (aEntryI.hasNext()) {
			Map.Entry aEntry = (Map.Entry) aEntryI.next();
			Object o = aEntry.getValue();

			if (o == null) {
				append(aEntry.getKey(), "", aReturn, ampersand);
			} else if (o instanceof String) {
				append(aEntry.getKey(), o, aReturn, ampersand);
			} else if (o instanceof String[]) {
				String[] aValues = (String[]) o;

				for (int i = 0; i < aValues.length; i++) {
					append(aEntry.getKey(), aValues[i], aReturn, ampersand);
				}
			} else {
				append(aEntry.getKey(), o, aReturn, ampersand);
			}
		}

		return aReturn;
	}

	private static StringBuffer append(Object key, Object value, StringBuffer queryString, String ampersand) {
		if (queryString.length() > 0) {
			queryString.append(ampersand);
		}

		// Use encodeURL from Struts' RequestUtils class - it's JDK 1.3 and 1.4
		// compliant
		queryString.append(encodeURL(key.toString()));
		queryString.append("=");
		queryString.append(encodeURL(value.toString()));

		return queryString;
	}

	/**
	 * 设置cookie值
	 * 
	 * @param response
	 * @param name
	 * @param value
	 * @param path
	 * @return HttpServletResponse
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String path) {

		Cookie cookie = new Cookie(name, value);
		cookie.setSecure(false);
		cookie.setPath(path);
		cookie.setMaxAge(3600 * 24 * 30); // 30 days

		response.addCookie(cookie);
	}

	/**
	 * 获得cookie值
	 * 
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		Cookie returnCookie = null;

		if (cookies == null) {
			return returnCookie;
		}

		for (int i = 0; i < cookies.length; i++) {
			Cookie thisCookie = cookies[i];

			if (thisCookie.getName().equals(name)) {
				// cookies with no value do me no good!
				if (!thisCookie.getValue().equals("")) {
					returnCookie = thisCookie;

					break;
				}
			}
		}

		return returnCookie;
	}

	/**
	 * 删除cookie值
	 * 
	 * @param response
	 * @param cookie
	 * @param path
	 */
	public static void deleteCookie(HttpServletResponse response, Cookie cookie, String path) {
		if (cookie != null) {
			// Delete the cookie by setting its maximum age to zero
			cookie.setMaxAge(0);
			cookie.setPath(path);
			response.addCookie(cookie);
		}
	}

	/**
	 * 对URL进行UTF－8编码
	 * 
	 * @param url
	 * @return
	 */
	public static String encodeURL(String url) {
		return encodeURL(url, "UTF-8");
	}

	/**
	 * 对URL编码
	 * 
	 * @param url
	 * @param enc
	 *            编码类型
	 * @return
	 */
	public static String encodeURL(String url, String enc) {
		try {

			if (enc == null || enc.length() == 0) {
				enc = "UTF-8";
			}
			return URLEncoder.encode(url, enc);
		} catch (Exception e) {
			System.err.print(e);
		}
		return null;
	}

	/**
	 * 创建一个带用户名和密码的cookie
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static String encodePasswordCookie(String username, String password) {
		StringBuffer buf = new StringBuffer();
		if (username != null && password != null) {
			byte[] bytes = (username + ENCODE_DELIMETER + password).getBytes();
			int b;

			for (int n = 0; n < bytes.length; n++) {
				b = bytes[n] ^ (ENCODE_XORMASK + n);
				buf.append((char) (ENCODE_CHAR_OFFSET1 + (b & 0x0F)));
				buf.append((char) (ENCODE_CHAR_OFFSET2 + ((b >> 4) & 0x0F)));
			}
		}
		return buf.toString();
	}

	/**
	 * 获得用户名和密码
	 * 
	 * @param cookieVal
	 * @return
	 */
	public static String[] decodePasswordCookie(String cookieVal) {

		// check that the cookie value isn't null or zero-length
		if (cookieVal == null || cookieVal.length() <= 0) {
			return null;
		}
		// unrafel the cookie value
		char[] chars = cookieVal.toCharArray();
		byte[] bytes = new byte[chars.length / 2];
		int b;
		for (int n = 0, m = 0; n < bytes.length; n++) {
			b = chars[m++] - ENCODE_CHAR_OFFSET1;
			b |= (chars[m++] - ENCODE_CHAR_OFFSET2) << 4;
			bytes[n] = (byte) (b ^ (ENCODE_XORMASK + n));
		}
		cookieVal = new String(bytes);
		int pos = cookieVal.indexOf(ENCODE_DELIMETER);
		String username = (pos < 0) ? "" : cookieVal.substring(0, pos);
		String password = (pos < 0) ? "" : cookieVal.substring(pos + 1);

		return new String[] { username, password };
	}

	/**
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getAttribute(HttpServletRequest request, String key) {
		String str = request.getParameter(key);
		if (GenericValidator.isBlankOrNull(str)) {
			str = (String) request.getAttribute(key);
		}
		return str;
	}
}