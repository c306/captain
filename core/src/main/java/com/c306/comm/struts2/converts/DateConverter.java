package com.c306.comm.struts2.converts;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.util.StrutsTypeConverter;

import com.c306.core.CoreConstants;
import com.opensymphony.xwork2.conversion.TypeConversionException;

public class DateConverter extends StrutsTypeConverter  {

	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		
        try {
			if (values == null) {
			    return null;
			} else if (values.length > 1) {
				return super.convertValue(context, values, toClass);
			} else if (toClass == Timestamp.class) {
			    return DateUtils.parseDateStrictly(values[0], CoreConstants.DATETIME_PATTERN_YMDHMS);
			} else if (toClass == Date.class) {
			    return DateUtils.parseDateStrictly(values[0], CoreConstants.DATETIME_DEFAULT_PATTERNS);
			} else if (toClass == String.class) {
			    return (values[0]);
			}
			
		} catch (ParseException e) {
	        throw new TypeConversionException();
		}

//        return super.convertValue(context, values, toClass);
        throw new TypeConversionException();
    }

	@Override
	public String convertToString(Map context, Object value) {

        if (value instanceof Date) {
            DateFormat df = new SimpleDateFormat(CoreConstants.DATETIME_PATTERN_YMD);
            if (value instanceof Timestamp) {
                df = new SimpleDateFormat(CoreConstants.DATETIME_PATTERN_YMDHMS);
            }

            try {
                return df.format(value);
            } catch (final Exception e) {
                throw new TypeConversionException("Error converting Date to String", e);
            }
        } else {
            return value.toString();
        }
    }

}
