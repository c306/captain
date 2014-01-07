package com.c306.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.regex.Pattern;

import ognl.Ognl;

/**
 * 字符窜操作类
 * 
 */
@Deprecated
public class StringUtil {
	/**
	 * 字符串转换为GBKURLEncoding<br/>
	 * return ex: %ab%a1%66
	 * 
	 * @param arg0
	 *            a <code>String</code>
	 * @return a String
	 */
	public static String GBKURLEncoding(String arg0) {
		if (arg0 == null) {
			return null;
		}
		byte[] str = arg0.getBytes();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length; i++) {
			sb.append("%" + Integer.toHexString(str[i] & 0xFF));
		}
		return sb.toString();
	}

	/**
	 * 字符串转换为HTML代码<br/>
	 * return ex: &amp;id=
	 * 
	 * @param arg0
	 *            a <code>String</code>
	 * @return a String
	 */
	public static String HTMLEncoding(String arg0) {
		String encodeStr = arg0;
		encodeStr = encodeStr.replaceAll("&", "&amp;");
		encodeStr = encodeStr.replaceAll("\"", "&quot;");
		encodeStr = encodeStr.replaceAll("<", "&lt;");
		encodeStr = encodeStr.replaceAll(">", "&gt;");
		return encodeStr;
	}
	
	public static String html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
																										// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
																									// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;// 返回文本字符串
	}

	/**
	 * 判断是否非空，忽略null和“”
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (s == null) {
			return true;
		} else if (s.equals("")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否是数字
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNumber(String s) {

		try {
			float fla = Float.parseFloat(s);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否相等，忽略null和“”
	 * 
	 * @param no1
	 * @param no2
	 * @return
	 */
	public static boolean ifEqualWithNull(String no1, String no2) {
		if (isEmpty(no1) && isEmpty(no2)) {
			return true;
		} else if (no1.equals(no2)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断是否相等，考虑null和“”为不同内容
	 * 
	 * @param no1
	 * @param no2
	 * @return
	 */
	public static boolean ifEqual(String no1, String no2) {
		if (no1 == null && no2 == null) {
			return true;
		} else if (no1 != null && no2 == null) {
			return false;
		} else if (no1 == null && no2 != null) {
			return false;
		} else if (no1.equals(no2)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean contains(String s, String text, String delimiter) {
		if ((s == null) || (text == null) || (delimiter == null)) {
			return false;
		}

		if (!s.endsWith(delimiter)) {
			s += delimiter;
		}

		int pos = s.indexOf(delimiter + text + delimiter);

		if (pos == -1) {
			if (s.startsWith(text + delimiter)) {
				return true;
			}

			return false;
		}

		return true;
	}

	public static int count(String s, String text) {
		if ((s == null) || (text == null)) {
			return 0;
		}

		int count = 0;

		int pos = s.indexOf(text);

		while (pos != -1) {
			pos = s.indexOf(text, pos + text.length());
			count++;
		}

		return count;
	}

	public static String merge(String array[], String delimiter) {
		if (array == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < array.length; i++) {
			sb.append(array[i].trim());

			if ((i + 1) != array.length) {
				sb.append(delimiter);
			}
		}

		return sb.toString();
	}

	public static String read(ClassLoader classLoader, String name) throws IOException {

		return read(classLoader.getResourceAsStream(name));
	}

	public static String read(InputStream is) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		StringBuffer sb = new StringBuffer();
		String line = null;

		while ((line = br.readLine()) != null) {
			sb.append(line).append('\n');
		}

		br.close();

		return sb.toString().trim();
	}

	public static String replace(String s, String oldSub, String newSub) {
		if ((s == null) || (oldSub == null) || (newSub == null)) {
			return null;
		}

		int y = s.indexOf(oldSub);

		if (y >= 0) {
			StringBuffer sb = new StringBuffer();
			int length = oldSub.length();
			int x = 0;

			while (x <= y) {
				sb.append(s.substring(x, y));
				sb.append(newSub);
				x = y + length;
				y = s.indexOf(oldSub, x);
			}

			sb.append(s.substring(x));

			return sb.toString();
		} else {
			return s;
		}
	}

	public static String replace(String s, String[] oldSubs, String[] newSubs) {
		if ((s == null) || (oldSubs == null) || (newSubs == null)) {
			return null;
		}

		if (oldSubs.length != newSubs.length) {
			return s;
		}

		for (int i = 0; i < oldSubs.length; i++) {
			s = replace(s, oldSubs[i], newSubs[i]);
		}

		return s;
	}

	public static String replace(String s, String[] oldSubs, String newSub) {
		if ((s == null) || (oldSubs == null) || (newSub == null)) {
			return null;
		}

		for (int i = 0; i < oldSubs.length; i++) {
			s = replace(s, oldSubs[i], newSub);
		}

		return s;
	}

	public static String reverse(String s) {
		if (s == null) {
			return null;
		}

		char[] c = s.toCharArray();
		char[] reverse = new char[c.length];

		for (int i = 0; i < c.length; i++) {
			reverse[i] = c[c.length - i - 1];
		}

		return new String(reverse);
	}

	public static String shorten(String s) {
		return shorten(s, 20);
	}

	public static String shorten(String s, int length) {
		return shorten(s, length, "..");
	}

	public static String shorten(String s, String suffix) {
		return shorten(s, 20, suffix);
	}

	public static String shorten(String s, int length, String suffix) {
		if (s == null || suffix == null) {
			return null;
		}

		if (s.length() > length) {
			s = s.substring(0, length) + suffix;
		}

		return s;
	}

	/*
	 * 中文链接转码
	 */
	public static String chineseURLToUTF(String sr, char sepChar) {
		String filename = (new File(sr)).getName();
		String path = (new File(sr)).getParent();

		path = path.replace('\\', sepChar);
		filename = java.net.URLEncoder.encode(filename);
		path = path + "/" + filename;
		return path;
	}

	/*
	 * 中文转码
	 */
	public static String toGB2312(String str) {
		if (str != null && str.length() > 0) {
			try {
				byte[] byteTmp = str.getBytes("ISO8859_1");
				str = new String(byteTmp, "GB2312");
			} catch (Exception e) {
				System.out.println("chinese encode error !");
			}
		}
		return str;
	}

	public static String toUTF8(String str) {
		if (str != null && str.length() > 0) {
			try {
				byte[] byteTmp = str.getBytes("ISO8859_1");
				str = new String(byteTmp, "utf-8");
			} catch (Exception e) {
				System.out.println("chinese encode error !");
			}
		}
		return str;
	}

	public static String to8859(String str) {
		if (str != null && str.length() > 0) {
			try {
				byte[] byteTmp = str.getBytes("GB2312");
				str = new String(byteTmp, "8859_1");
			} catch (Exception e) {
				System.out.println("chinese encode error !");
			}
		}
		return str;
	}

	public static String[] split(String s, String delimiter) {
		if (s == null || delimiter == null) {
			return new String[0];
		}

		if (!s.endsWith(delimiter)) {
			s += delimiter;
		}

		s = s.trim();

		if (s.equals(delimiter)) {
			return new String[0];
		}

		List nodeValues = new ArrayList();

		if (delimiter.equals("\n") || delimiter.equals("\r")) {
			try {
				BufferedReader br = new BufferedReader(new StringReader(s));

				String line = null;

				while ((line = br.readLine()) != null) {
					nodeValues.add(line);
				}

				br.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		} else {
			int offset = 0;
			int pos = s.indexOf(delimiter, offset);

			while (pos != -1) {
				nodeValues.add(s.substring(offset, pos));

				offset = pos + delimiter.length();
				pos = s.indexOf(delimiter, offset);
			}
		}

		return (String[]) nodeValues.toArray(new String[0]);
	}

	public static boolean[] split(String s, String delimiter, boolean x) {
		String[] array = split(s, delimiter);
		boolean[] newArray = new boolean[array.length];

		for (int i = 0; i < array.length; i++) {
			boolean value = x;

			try {
				value = Boolean.valueOf(array[i]).booleanValue();
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static double[] split(String s, String delimiter, double x) {
		String[] array = split(s, delimiter);
		double[] newArray = new double[array.length];

		for (int i = 0; i < array.length; i++) {
			double value = x;

			try {
				value = Double.parseDouble(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static float[] split(String s, String delimiter, float x) {
		String[] array = split(s, delimiter);
		float[] newArray = new float[array.length];

		for (int i = 0; i < array.length; i++) {
			float value = x;

			try {
				value = Float.parseFloat(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static int[] split(String s, String delimiter, int x) {
		String[] array = split(s, delimiter);
		int[] newArray = new int[array.length];

		for (int i = 0; i < array.length; i++) {
			int value = x;

			try {
				value = Integer.parseInt(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static long[] split(String s, String delimiter, long x) {
		String[] array = split(s, delimiter);
		long[] newArray = new long[array.length];

		for (int i = 0; i < array.length; i++) {
			long value = x;

			try {
				value = Long.parseLong(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	public static short[] split(String s, String delimiter, short x) {
		String[] array = split(s, delimiter);
		short[] newArray = new short[array.length];

		for (int i = 0; i < array.length; i++) {
			short value = x;

			try {
				value = Short.parseShort(array[i]);
			} catch (Exception e) {
			}

			newArray[i] = value;
		}

		return newArray;
	}

	/**
	 * 转换成list<Long>
	 * 
	 * @param s
	 *            --分格必须是数字
	 * @param delimiter
	 * @return
	 */
	public static List<Long> splitToList(String s, String delimiter) {
		List<Long> list = new ArrayList();
		String[] array = split(s, delimiter);
		if (array != null) {
			for (String id : array)
				list.add(new Long(id));
		}
		return list;
	}

	public static final String stackTrace(Throwable t) {
		String s = null;

		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			t.printStackTrace(new PrintWriter(baos, true));
			s = baos.toString();
		} catch (Exception e) {
		}

		return s;
	}

	public static boolean startsWith(String s, char begin) {
		return startsWith(s, (new Character(begin)).toString());
	}

	public static boolean startsWith(String s, String begin) {
		if ((s == null) || (begin == null)) {
			return false;
		}

		if (begin.length() > s.length()) {
			return false;
		}

		String temp = s.substring(0, begin.length());

		if (temp.equalsIgnoreCase(begin)) {
			return true;
		} else {
			return false;
		}
	}

	public static String wrap(String text) {
		return wrap(text, 80);
	}

	public static String wrap(String text, int width) {
		if (text == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		try {
			BufferedReader br = new BufferedReader(new StringReader(text));

			String s = "";

			while ((s = br.readLine()) != null) {
				if (s.length() == 0) {
					sb.append("\n");
				} else {
					while (true) {
						int pos = s.lastIndexOf(' ', width);

						if ((pos == -1) && (s.length() > width)) {
							sb.append(s.substring(0, width));
							sb.append("\n");

							s = s.substring(width, s.length()).trim();
						} else if ((pos != -1) && (s.length() > width)) {
							sb.append(s.substring(0, pos));
							sb.append("\n");

							s = s.substring(pos, s.length()).trim();
						} else {
							sb.append(s);
							sb.append("\n");

							break;
						}
					}
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return sb.toString();
	}

	public static String getPassword(int length, String key) {

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {
			sb.append(key.charAt((int) (Math.random() * key.length())));
		}

		return sb.toString();
	}

	public static String getPassword(int length) {
		String key = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		return getPassword(length, key);
	}

	/**
	 * Encode a string using algorithm specified in web.xml and return the
	 * resulting encrypted password. If exception, the plain credentials string
	 * is returned
	 * 
	 * @param password
	 *            Password or other credentials to use in authenticating this
	 *            username
	 * @param algorithm
	 *            Algorithm used to do the digest
	 * 
	 * @return encypted password based on the algorithm.
	 */
	public static String encodePassword(String password, String algorithm) {
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			System.err.print("Exception: " + e);

			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; i++) {
			if (((int) encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString((int) encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}

	/**
	 * 替换｛｝中参数的值
	 * 
	 * @param str
	 * @param map
	 * @return
	 */
	public static String parseParam(String str, Map map) {

		if (str == null || "".equals(str)) {
			return str;
		}

		if (map == null || map.size() == 0) {
			return str;
		}
		ArrayList al = new ArrayList();
		String parseStr = str;
		int rightIndex = 0;
		int left = StringUtil.count(parseStr, "{");
		int right = StringUtil.count(parseStr, "}");
		String temp = "";
		// 判断成对的｛｝符号
		if (left != right) {
			return str;
		}
		// 查找｛｝中的参数名
		for (int j = 0; j < left; j++) {
			// 取参数名
			temp = parseStr.substring(parseStr.indexOf("{") + 1, parseStr.indexOf("}"));
			al.add(temp);
			rightIndex = parseStr.indexOf("}") + 1;
			parseStr = parseStr.substring(rightIndex);
		}

		// 替换｛｝中参数的值
		String param = "";
		String oldParam = "";
		String paramValue = "";
		for (int i = 0; i < al.size(); i++) {
			param = (String) al.get(i);
			oldParam = "{" + param + "}";
			paramValue = map.get(param) == null ? "" : map.get(param).toString();
			str = StringUtil.replace(str, oldParam, paramValue);
		}
		return str;
	}

	/**
	 * 替换[]中参数的值
	 * 
	 * @param str
	 * @param object
	 * @return
	 */
	public static String parseParam(String str, Object object) {

		String before = "[";
		String after = "]";
		if (str == null || "".equals(str)) {
			return str;
		}

		if (object == null) {
			return str;
		}
		ArrayList al = new ArrayList();
		String parseStr = str;
		int rightIndex = 0;
		int left = StringUtil.count(parseStr, before);
		int right = StringUtil.count(parseStr, after);
		String temp = "";
		// 判断成对的｛｝符号
		if (left != right) {
			return str;
		}
		// 查找｛｝中的参数名
		for (int j = 0; j < left; j++) {
			// 取参数名
			temp = parseStr.substring(parseStr.indexOf(before) + 1, parseStr.indexOf(after));
			al.add(temp);
			rightIndex = parseStr.indexOf(after) + 1;
			parseStr = parseStr.substring(rightIndex);
		}

		// 替换｛｝中参数的值
		String param = "";
		String oldParam = "";
		String paramValue = "";
		for (int i = 0; i < al.size(); i++) {
			param = (String) al.get(i);
			oldParam = before + param + after;
			try {
				Object tempObj = Ognl.getValue(param, object);
				paramValue = tempObj == null ? "" : tempObj.toString();
				str = StringUtil.replace(str, oldParam, paramValue);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return str;
	}

	/**
	 * 解析字符串,根据键值得到对应参数值 字符串格式: key1=value1&key2=value2...
	 * 
	 * @param str
	 * @param key
	 * @return
	 */
	public static String getParamValue(String str, String key) {
		String symbol1 = "&";
		String symbol2 = "=";
		String strValue = "";
		StringTokenizer token = new StringTokenizer(str, symbol1);
		while (token.hasMoreTokens()) {
			String strNext = token.nextToken();
			String[] arLabValue = strNext.split(symbol2);
			if (arLabValue != null && arLabValue.length > 1) {
				String strLable = arLabValue[0];
				strValue = "";
				if (!isEmpty(strLable) && strLable.equalsIgnoreCase(key)) {
					strValue = arLabValue[1];
					break;
				}
			}
		}

		return strValue;
	}

	/**
	 * @param input
	 * @param forReplace
	 * @param replaceWith
	 * @return
	 */
	public static String replaceAll(String input, String forReplace, String replaceWith) {
		if (input == null)
			return null;
		StringBuffer result = new StringBuffer();
		boolean hasMore = true;
		while (hasMore) {
			int start = input.indexOf(forReplace);
			int end = start + forReplace.length();
			if (start != -1) {
				result.append(input.substring(0, start) + replaceWith);
				input = input.substring(end);
			} else {
				hasMore = false;
				result.append(input);
			}
		}
		if (result.toString().equals(""))
			return input; // nothing is changed
		else
			return result.toString();
	}

	/**
	 * 转换字符串中的字符，空格转成&nbsp;，回车转成<br>
	 * 
	 * @param s
	 *            字符串
	 * @return 转换后的字符串
	 */
	public static String convertString(String s) {
		// 检测
		if (s == null || s.equals("")) {
			return s;
		}
		// StringBuffer rs = new StringBuffer("");
		//		
		// int length = s.length();
		// for (int i = 0; i < length; i++) {
		// //
		// char c = s.charAt(i);
		//			
		// try{
		//			
		// Log.debug("cccccccccccccccc='"+c+"':"+Character.toString(c).getBytes("ISO-8859-1"));
		// }catch(Exception e){
		// e.printStackTrace();
		// }
		// if (c == ' ') {
		// rs.append("&nbsp;");
		// } else if (c == 0x0a) {
		// rs.append("<br>");
		// } else if (c == 0x0d) {
		// // 不处理
		// } else {
		// rs.append(c);
		// }
		//
		// }
		//		
		// return rs.toString();
		String resultStr = StringUtil.replaceAll(s, "\n", "<br>");
		// resultStr = StringUtil.replaceAll(resultStr," ","&nbsp;");
		return resultStr;
	}

	/**
	 * 格式化数字
	 * 
	 * @param num
	 * @param pattern
	 *            可以任意定义位数,如"###.##"表示小数点后面保留两位
	 * @return
	 */
	public static String toFormatString(float num, String pattern) {
		return new java.text.DecimalFormat(pattern).format(num);
	}

	/**
	 * 格式化数字字符位数
	 * 
	 * @param NO
	 * @param formatNum
	 * @return
	 */
	public static String formatNO(String NO, int formatNum) {

		if (StringUtil.isEmpty(NO)) {
			return NO;
		}

		if (formatNum == 0) {
			return "";
		}

		int NOlng = NO.length();
		if (NOlng > formatNum) {
			return NO.substring(NOlng - formatNum);
		}

		String result = "";
		for (int i = 0; i < formatNum - NOlng; i++) {
			result = result + "0";
		}
		result = result + NO;

		return result;

	}

	/**
	 * 根据分隔符号解析字符串 例:解析字符串:"1:变电站;2:巡检中心",先以";"分隔,再以":"分隔,得到map对象,
	 * key为1,2;value:变电站,巡检中心
	 * 
	 * @param deptTypeString
	 * @param symbol_1
	 *            分隔符1
	 * @param symbol_2
	 *            分隔符2
	 * @return
	 */
	public static Map parseString(String deptTypeString, String symbol_1, String symbol_2) {
		Map map = new TreeMap();
		StringTokenizer stTypeString = new StringTokenizer(deptTypeString, symbol_1);
		while (stTypeString.hasMoreElements()) {
			String itemType = stTypeString.nextToken();
			// Log.debug("______itemType:"+itemType);
			StringTokenizer stItemType = new StringTokenizer(itemType, symbol_2);
			String strValue = "", strKey = "";
			int i = 0;
			while (stItemType.hasMoreElements()) {
				if (i == 0)
					strKey = stItemType.nextToken();
				else {
					strValue = stItemType.nextToken();
				}
				i++;
			}
			// Log.debug("______lngKey:"+strKey);
			// Log.debug("______strValue:"+strValue);
			map.put(strKey, strValue);
		}
		return map;
	}

	/**
	 * 根据分隔符号解析字符串 例:解析字符串:变电站,巡检中心 key为变电站,巡检中心;value:变电站,巡检中心
	 * 
	 * @param deptTypeString
	 * @param symbol_1
	 *            分隔符1
	 * @return
	 */
	public static Map parseString(String deptTypeString, String symbol_1) {
		Map map = new TreeMap();
		StringTokenizer stTypeString = new StringTokenizer(deptTypeString, symbol_1);
		while (stTypeString.hasMoreElements()) {
			String itemType = stTypeString.nextToken();
			// Log.debug("______itemType:"+itemType);
			map.put(itemType, itemType);
		}
		return map;
	}

	/**
	 * 根据分隔符号解析字符串
	 * 
	 * @param typeString
	 * @return
	 */
	public static Map parseString(String typeString) {
		return StringUtil.parseString(typeString, ",", ":");
	}

	/**
	 * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名.
	 * 
	 * @param s
	 *            原文件名
	 * @return 重新编码后的文件名
	 */
	public static String toUtf8String(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 增加URL中参数
	 * 
	 * @param surl
	 * @param name
	 * @param value
	 * @return
	 */
	public static String addParam(String surl, String name, String value) {
		StringBuffer sburl = new StringBuffer();
		if (surl != null && name != null) {
			if (surl.indexOf("?") == -1) {
				// 原有的URL没有带参数
				sburl.append(surl).append("?").append(name).append("=").append(value);
			} else {
				// 原有的URL带有参数
				sburl.append(surl).append("&").append(name).append("=").append(value);
			}
		}
		return sburl.toString();
	}
}
