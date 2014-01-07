package com.c306.comm.sysargs;

import java.util.HashMap;
import java.util.Map;

public class SysArgsUtil {
	private static Map<String, String> args = new HashMap<String, String>();
	
	public synchronized static void init(Map<String, String> args) {
		SysArgsUtil.args = args;
	}
	public synchronized static void update(String key, String value) {
		SysArgsUtil.args.put(key, value);
	}
	public synchronized static void update(Map<String, String> params) {
		SysArgsUtil.args.putAll(params);
	}
	
	public static String getVal(String key) {
		// no synchronized
		return args.get(key);
	}
}