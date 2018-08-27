package com.ashaxm.laixuexi.system;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ashaxm.laixuexi.utils.Tools;




@Configuration
public class HandlerInterceptor extends HandlerInterceptorAdapter{
	
	Log log = LogFactory.getLog(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String info = "";
		info = " ip:" + Tools.getClientIP(request)
				+ " url:" + request.getRequestURI()
				+ " param:[";


		Map<String, String[]> map = request.getParameterMap();
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			String[] values = map.get(key);
			key += ":";
			for (String value : values) {
				// key+=Tools.convertEncode(value, "iso-8859-1", "utf-8")
				// +",";
				key += value + ",";
			}
			info += key;
		}

		info += "]";
		log.info(info);
		return true;
	}
}
