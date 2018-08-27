package top.ashaxm.service.system;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

import top.ashaxm.ServiceApp;

/**
 * 利用tomcat启动的时候，默认第一个加载的类
 * @author yaoyz
 * 2018年1月8日
 */
public class WebXml extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    	System.out.println("WebXml    init");
    	ServiceSystemProperty.init();
        return application.sources(ServiceApp.class);
    }
}
