package com.huanxin;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.huanxin.system.HandlerInterceptor;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@ComponentScan
@EnableTransactionManagement
@EnableAutoConfiguration
public class LaixuexiApp extends WebMvcConfigurerAdapter{

		public static void main(String[] args) {

	    }

	    @Bean
	    DataSource dataSource() {
	        return new ComboPooledDataSource();
	    }

	    @Bean
	    JdbcTemplate jdbcTemplate(DataSource dataSource) {
	        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
	        return jdbcTemplate;
	    }

	    @Override
	    public void addViewControllers(ViewControllerRegistry registry) {
	        registry.addViewController("/home").setViewName("/index.html");
	        registry.addViewController("/").setViewName("/index.html");
	        registry.addViewController("/login").setViewName("/login.html");
	    }
	    
	    @Override
		public void addInterceptors(InterceptorRegistry registry) {
	    	//瀵圭綉椤佃姹傝繘琛屾嫤鎴�
	    	registry.addInterceptor(new HandlerInterceptor());
		}
}
