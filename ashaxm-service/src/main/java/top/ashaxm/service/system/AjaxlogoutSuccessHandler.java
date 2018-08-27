package top.ashaxm.service.system;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import top.ashaxm.common.utils.HttpRequest;

/**
 * 用户成功退出系统所做的操作
 * @author yaoyz
 * 2018年1月8日
 */
public class AjaxlogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		Log log = LogFactory.getLog(getClass());	
		String username = request.getParameter("username");
		log.info("logout success    IP____" + HttpRequest.getClientIP(request) + "   name_____"+username);
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write("{\"logout\":\"success\"}");

	}

}
