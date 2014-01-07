package com.c306.core.interceptor;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.I18nInterceptor;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

/**
 * 
 * @author chens
 * 
 * @Deprecated	use RequestI18nFilter instead of.
 */
@Deprecated
public class SimpleInterceptor extends I18nInterceptor {
	private static final long serialVersionUID = 1L;
	private static String KEY_WORDS = "anyshopstyle.com";

	protected static final Logger LOG = LoggerFactory.getLogger(SimpleInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		HttpServletRequest req = (HttpServletRequest) invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
		HttpSession session = req.getSession();
		
		if (LOG.isDebugEnabled()) {
			LOG.debug(invocation.getAction().toString());
			LOG.debug(req.getRequestURL().toString());
            LOG.debug("intercept { ");
        }
		
		
		//处理根据域名解析中英文问题
		/*String url = req.getRequestURL().toString().toLowerCase();
		if(url != null && url.contains(KEY_WORDS)) {
			session.setAttribute("WW_TRANS_I18N_LOCALE", Locale.US);
		} else {
			session.setAttribute("WW_TRANS_I18N_LOCALE", Locale.CHINA);
		}*/
		//处理根据域名解析中英文问题 结束
		

		LOG.info("getServerName : " + req.getServerName());
		LOG.info("getRemoteAddr : " + req.getRemoteAddr());
		LOG.info("user-agent : " + req.getHeader("user-agent"));
		LOG.info("accept-language : " + req.getHeader("accept-language"));
		
		if(session.getAttribute(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE) == null){
//			if(req.getHeader("accept-language") != null)
//				Locale locale = (Locale) LocalizedTextUtil.localeFromString(req.getHeader("accept-language").substring(0, 5).replace('-', '_'), null);
			Locale locale = (Locale) LocalizedTextUtil.localeFromString("zh_CN", null);
			if(locale!=null){
				session.setAttribute(I18nInterceptor.DEFAULT_SESSION_ATTRIBUTE, locale);
				invocation.getInvocationContext().setLocale(locale);
			} else {
				LOG.warn("请求头中无accept-language信息，可能为非法访问！");
			}
		}
		
		if (LOG.isDebugEnabled()) {
            LOG.debug("before Locale=" + invocation.getStack().findValue("locale"));
        }

        final String result = invocation.invoke();
        if (LOG.isDebugEnabled()) {
            LOG.debug("after Locale=" + invocation.getStack().findValue("locale"));
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("intercept } ");
        }

        return result;
	}

}
