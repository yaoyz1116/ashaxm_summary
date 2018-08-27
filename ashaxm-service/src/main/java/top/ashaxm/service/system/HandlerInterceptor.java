package top.ashaxm.service.system;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import top.ashaxm.common.utils.HttpRequest;

/**
 * 过路器
 * 对所有请求进行过滤
 * @author yaoyz
 * 2018年1月8日
 */
@Configuration
public class HandlerInterceptor extends HandlerInterceptorAdapter {
	Log log = LogFactory.getLog(getClass());
	
	@Override  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		//记录请求信息
		recordRequestParam(request);
		
		//业务逻辑处理，可以在这里对请求的一些信息做处理，
		//如果return false, 则不允许请求继续进行
		
        return true;
	}
	
	/**
	 * 对请求进行记录
	 * @author yaoyz
	 * 2018年1月8日
	 */
	private void recordRequestParam(HttpServletRequest request) {
		StringBuffer info = new StringBuffer("");

		info.append("ip:" + HttpRequest.getClientIP(request)+ " url:" + request.getRequestURI()+ " param:[");

		// 暂时不记录具体的请求内容
		Map<String, String[]> map = request.getParameterMap();
		Set<String> keySet = map.keySet();

		for (String key : keySet) {
			String[] values = map.get(key);
			key += ":";
			for (String value : values) {
				key += value + ",";
			}
			info.append(key);
		}
		info.append("]");

		log.info(info);
	}
	
}
