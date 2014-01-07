package com.c306.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.PropertySource;

public class CoreConstants {
	
	/**
	 * javax.servlet.ServletContext.KEY
	 */
	public final static String QUARTZ_CONTEXT_SERVLET_CONTEXT_KEY = "javax.servlet.ServletContext.KEY";
	
	// 数据库记录标识：有效、无效
	public static final int RECORDSTATUS_VALID = 0x1;
	public static final int RECORDSTATUS_INVALID = 0x0;
	
	
	/**
	 * datetime patterns
	 */
	public static final String DATETIME_PATTERN_YMD = "yyyy-MM-dd";
	public static final String DATETIME_PATTERN_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATETIME_PATTERN_YMDHMSS = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DATETIME_PATTERN_HM = "HH:mm";
	public static final String DATETIME_PATTERN_HMS = "HH:mm:ss";
	public static final String DATETIME_PATTERN_HMSS = "HH:mm:ss.SSS";
	
	public static final String [] DATETIME_DEFAULT_PATTERNS = new String[]{ DATETIME_PATTERN_YMD, DATETIME_PATTERN_YMDHMS, DATETIME_PATTERN_YMDHMSS };
	

    /**
     * uploadfiles store path.
     * 
     */
    public static final String UPLOAD_STORE_PATH;
    
    /**
     * ImageMagick home
     * 
     */
    public static final String IM4JAVA_TOOLPATH;
    
    /**
     * File separator from System properties
     */
    public static final String FILE_SEP = System.getProperty("file.separator");

    /**
     * User home from System properties
     */
    public static final String USER_HOME = System.getProperty("user.home") + FILE_SEP;
    
    
    
    
    
    
    
    
    /**
     * initialization block
     */
    static {
    	

		MapPropertySource props = new MapPropertySource("application.properties");
		
		// initialize upload store path
		UPLOAD_STORE_PATH = props.getProperty("UPLOAD_STORE_PATH");
    	
		// ImageMagick
		IM4JAVA_TOOLPATH = props.getProperty("IM4JAVA_TOOLPATH");
    	
    }
    
    static class MapPropertySource extends PropertySource<Map<String, String>> {

    	public MapPropertySource(String name) {
    		super(name, new HashMap<String, String>(0));
    	}

    	@Override
    	public String getProperty(String name) {
    		return this.source.get(name);
    	}

    }

}
