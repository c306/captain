package com.c306.core.support;



import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.cache.ehcache.internal.nonstop.NonstopAwareNaturalIdRegionAccessStrategy;

import com.c306.core.CoreConstants;

/**
 * <p>
 * This is an utility calss used to parse the properties.
 * </p>
 * 
 * @author James House
 */
public class QueryParameters extends HashMap<String, String[]> {
	
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Data members.
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 在Action中声明的成员变量名
	 */
	private static final String DOMAIN_NAME = "query.";
	
//	HashMap<String, String> props = new HashMap<String, String>(7);
	
    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Constructors.
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    public QueryParameters() {
    	super(7);
    }

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * 
     * Interface.
     * 
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    public Map<String, String[]> getUnderlyingProperties() {
        return this;
    }
    
    public String get(String key) {
    	if(key.startsWith(DOMAIN_NAME))
    		return this.getStringProperty(key.substring(DOMAIN_NAME.length()), null);
    	return this.getStringProperty(key, null);
    }

    /**
     * Get the trimmed String value of the property with the given 
     * <code>name</code>.  If the value the empty String (after
     * trimming), then it returns null.
     */
    public String getStringProperty(String name) {
        return getStringProperty(name, null);
    }

    /**
     * Get the trimmed String value of the property with the given 
     * <code>name</code> or the given default value if the value is 
     * null or empty after trimming.
     */
    public String getStringProperty(String name, String def) {
        String[] vals = super.get(name);
        if (vals == null || vals.length == 0 || vals[0] == null) {
            return def;
        }
        
        String val = vals[0].trim();
        
        return (val.length() == 0) ? def : val;
    }

    public String[] getStringArrayProperty(String name) {
        return getStringArrayProperty(name, null);
    }

    public String[] getStringArrayProperty(String name, String[] def) {
        String vals = getStringProperty(name);
        if (vals == null) {
            return def;
        }

        StringTokenizer stok = new StringTokenizer(vals, ",");
        ArrayList<String> strs = new ArrayList<String>();
        try {
            while (stok.hasMoreTokens()) {
                strs.add(stok.nextToken().trim());
            }
            
            return (String[])strs.toArray(new String[strs.size()]);
        } catch (Exception e) {
            return def;
        }
    }

    public boolean getBooleanProperty(String name) {
        return getBooleanProperty(name, false);
    }

    public boolean getBooleanProperty(String name, boolean def) {
        String val = getStringProperty(name);
        
        return (val == null) ? def : Boolean.valueOf(val).booleanValue();
    }

    public byte getByteProperty(String name) throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            throw new NumberFormatException(" null string");
        }

        try {
            return Byte.parseByte(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public byte getByteProperty(String name, byte def)
        throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            return def;
        }

        try {
            return Byte.parseByte(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public char getCharProperty(String name) {
        return getCharProperty(name, '\0');
    }

    public char getCharProperty(String name, char def) {
        String param = getStringProperty(name);
        return  (param == null) ? def : param.charAt(0);
    }

    public Double getDoubleProperty(String name) throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
//            throw new NumberFormatException(" null string");
        	return null;
        }

        try {
            return Double.parseDouble(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public Double getDoubleProperty(String name, double def)
        throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            return def;
        }

        try {
            return Double.parseDouble(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public Float getFloatProperty(String name) throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
//            throw new NumberFormatException(" null string");
        	return null;
        }

        try {
            return Float.parseFloat(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public Float getFloatProperty(String name, float def)
        throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            return def;
        }

        try {
            return Float.parseFloat(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public Integer getIntProperty(String name) throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
//            throw new NumberFormatException(" null string");
        	return null;
        }

        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public Integer getIntProperty(String name, int def)
        throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            return def;
        }

        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public int[] getIntArrayProperty(String name) throws NumberFormatException {
        return getIntArrayProperty(name, null);
    }

    public int[] getIntArrayProperty(String name, int[] def)
        throws NumberFormatException {
        String vals = getStringProperty(name);
        if (vals == null) {
            return def;
        }

        StringTokenizer stok = new StringTokenizer(vals, ",");
        ArrayList<Integer> ints = new ArrayList<Integer>();
        try {
            while (stok.hasMoreTokens()) {
                try {
                    ints.add(new Integer(stok.nextToken().trim()));
                } catch (NumberFormatException nfe) {
                    throw new NumberFormatException(" '" + vals + "'");
                }
            }
                        
            int[] outInts = new int[ints.size()];
            for (int i = 0; i < ints.size(); i++) {
                outInts[i] = ((Integer)ints.get(i)).intValue();
            }
            return outInts;
        } catch (Exception e) {
            return def;
        }
    }

    public Long getLongProperty(String name) throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
//            throw new NumberFormatException(" null string");
            return null;
        }

        try {
            return Long.parseLong(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public Long getLongProperty(String name, long def)
        throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            return def;
        }

        try {
            return Long.parseLong(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public Short getShortProperty(String name) throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
//            throw new NumberFormatException(" null string");
            return null;
        }

        try {
            return Short.parseShort(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public Short getShortProperty(String name, short def)
        throws NumberFormatException {
        String val = getStringProperty(name);
        if (val == null) {
            return def;
        }

        try {
            return Short.parseShort(val);
        } catch (NumberFormatException nfe) {
            throw new NumberFormatException(" '" + val + "'");
        }
    }

    public Date getDateProperty(String name) {
        String val = getStringProperty(name);
        if (val == null) {
//            throw new ParseException(" null string", 0);
        	return null;
        }

        try {
            return DateUtils.truncate(DateUtils.parseDateStrictly(val, CoreConstants.DATETIME_DEFAULT_PATTERNS), Calendar.DATE);
        } catch (ParseException e) {
            throw new RuntimeException(" '" + val + "' " + e.getMessage(), e);
		}
    }
    
    public Date getDateToNextDayProperty(String name) {
    	String val = getStringProperty(name);
    	if (val == null) {
//            throw new ParseException(" null string", 0);
    		return null;
    	}
    	
    	try {
    		Date date = DateUtils.parseDateStrictly(val, CoreConstants.DATETIME_DEFAULT_PATTERNS);
    		return DateUtils.truncate(DateUtils.addDays(date, 1), Calendar.DATE);
    	} catch (ParseException e) {
    		throw new RuntimeException(" '" + val + "' " + e.getMessage(), e);
    	}
    }

    public Date getDateProperty(String name, Date def) {
        String val = getStringProperty(name);
        if (val == null) {
            return def;
        }

        try {
            return DateUtils.truncate(DateUtils.parseDateStrictly(val, CoreConstants.DATETIME_DEFAULT_PATTERNS), Calendar.DATE);
        } catch (ParseException e) {
            throw new RuntimeException(" '" + val + "' " + e.getMessage(), e);
    		}
    }
    
    public Timestamp getTimestampProperty(String name)  {
    	String val = getStringProperty(name);
    	if (val == null) {
//          throw new ParseException(" null string", 0);
    		return null;
    	}
    	
    	try {
    		return new Timestamp(DateUtils.parseDateStrictly(val, CoreConstants.DATETIME_DEFAULT_PATTERNS).getTime());
    	} catch (ParseException e) {
            throw new RuntimeException(" '" + val + "' " + e.getMessage(), e);
    		}
    }
    
    public Timestamp getTimestampProperty(String name, Timestamp def) {
    	String val = getStringProperty(name);
    	if (val == null) {
    		return def;
    	}
    	
    	try {
    		return new Timestamp(DateUtils.parseDateStrictly(val, CoreConstants.DATETIME_DEFAULT_PATTERNS).getTime());
    	} catch (ParseException e) {
            throw new RuntimeException(" '" + val + "' " + e.getMessage(), e);
    		}
    }

    public String[] getPropertyGroups(String prefix) {
        Set keys = this.keySet();
        HashSet<String> groups = new HashSet<String>(10);

        if (!prefix.endsWith(".")) {
            prefix += ".";
        }
        for (Iterator iterator = this.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();

            if (key.startsWith(prefix)) {
                String groupName = key.substring(prefix.length(), key.indexOf('.', prefix.length()));
                groups.add(groupName);
            }
		}
        return (String[]) groups.toArray(new String[groups.size()]);
    }

    public Properties getPropertyGroup(String prefix) {
        return getPropertyGroup(prefix, false, null);
    }

    public Properties getPropertyGroup(String prefix, boolean stripPrefix) {
        return getPropertyGroup(prefix, stripPrefix, null);
    }

    /**
     * Get all properties that start with the given prefix.  
     * 
     * @param prefix The prefix for which to search.  If it does not end in 
     *      a "." then one will be added to it for search purposes.
     * @param stripPrefix Whether to strip off the given <code>prefix</code>
     *      in the result's keys.
     * @param excludedPrefixes Optional array of fully qualified prefixes to
     *      exclude.  For example if <code>prefix</code> is "a.b.c", then 
     *      <code>excludedPrefixes</code> might be "a.b.c.ignore".
     *      
     * @return Group of <code>Properties</code> that start with the given prefix, 
     *      optionally have that prefix removed, and do not include properties 
     *      that start with one of the given excluded prefixes.
     */
    public Properties getPropertyGroup(String prefix, boolean stripPrefix, String[] excludedPrefixes) {
        Properties group = new Properties();

        if (!prefix.endsWith(".")) {
            prefix += ".";
        }

        for (Iterator iterator = this.keySet().iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();

            if (key.startsWith(prefix)) {
                
                boolean exclude = false;
                if (excludedPrefixes != null) {
                    for (int i = 0; (i < excludedPrefixes.length) && (exclude == false); i++) {
                        exclude = key.startsWith(excludedPrefixes[i]);
                    }
                }

                if (exclude == false) {
                    String value = getStringProperty(key, "");
                    
                    if (stripPrefix) { 
                        group.put(key.substring(prefix.length()), value);
                    } else {
                        group.put(key, value);
                    }
                }
            }
		}

        return group;
    }

	public int getPageNo() {
		try {
			return this.getIntProperty("pageNo", 0);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int getPageSize() {
		try {
			return this.getIntProperty("pageSize", 0);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void setPageNo(int pageNo) {
		this.put("pageNo", new String[]{String.valueOf(pageNo)});
	}

	public void setPageSize(int pageSize) {
		this.put("pageSize", new String[]{String.valueOf(pageSize)});
	}

}
