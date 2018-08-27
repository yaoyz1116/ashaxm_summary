package com.ashaxm.personal;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * springboot项目启动的第一步，加载APP类，生成很多配置文件类
 * yaoyz    2018.6.23
 */
public class WebXml extends SpringBootServletInitializer {
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PersonalApp.class);
    }
}
