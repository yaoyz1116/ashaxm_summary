package com.ashaxm.personal;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

/**
 * springboot��Ŀ�����ĵ�һ��������APP�࣬���ɺܶ������ļ���
 * yaoyz    2018.6.23
 */
public class WebXml extends SpringBootServletInitializer {
	
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PersonalApp.class);
    }
}
