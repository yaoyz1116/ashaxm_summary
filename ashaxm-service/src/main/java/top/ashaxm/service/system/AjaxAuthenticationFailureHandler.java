package top.ashaxm.service.system;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import top.ashaxm.common.utils.HttpRequest;

/**
 * 用户登录失败的时候所做的操作
 * @author yaoyz
 * 2018年1月8日
 */
public class AjaxAuthenticationFailureHandler implements
		AuthenticationFailureHandler {
	
	Log log = LogFactory.getLog(getClass());

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
		
		String username = request.getParameter("username");
		log.info("login fail  IP____"+HttpRequest.getClientIP(request) + "   name____"+username);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"login\":\"failure\",\"username\":\"" + username + "\"}");
    }

}
