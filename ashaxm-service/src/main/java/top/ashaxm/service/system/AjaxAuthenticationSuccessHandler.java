package top.ashaxm.service.system;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import top.ashaxm.common.utils.HttpRequest;

/**
 * WebSecurityConfig中调用
 * 用户登录成功之后调用的方法
 * @author yaoyz
 * 2018年1月8日
 */
public class AjaxAuthenticationSuccessHandler implements
		AuthenticationSuccessHandler {
	Log log = LogFactory.getLog(getClass());
	
	//如果登录成功，则返回json字符串，为了给ajax请求用
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication auth)
			throws IOException, ServletException {
		log.info("login success    IP____" + HttpRequest.getClientIP(request) + "   name_____"+auth.getName());
		response.setCharacterEncoding("UTF-8");
		LoginUserDetails loginUser = (LoginUserDetails)auth.getPrincipal();
		response.getWriter().write("{\"login\":\"success\",\"username\":\"" + auth.getName() + "\",\"userId\":" + loginUser.getId()+"}");
	}

}
