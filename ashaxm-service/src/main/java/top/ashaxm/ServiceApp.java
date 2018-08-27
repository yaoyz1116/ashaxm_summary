package top.ashaxm;

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

import top.ashaxm.service.system.HandlerInterceptor;
import top.ashaxm.service.system.ServiceSystemProperty;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 在WebXml类中调用
 * @author yaoyz
 * 2018年1月8日
 */
@Configuration
@ComponentScan
@EnableTransactionManagement
@EnableAutoConfiguration
public class ServiceApp extends WebMvcConfigurerAdapter {
	
    public static void main(String[] args) {
    	System.out.println("ServiceApp       init");
        //ApplicationContext ctx = SpringApplication.run(ServiceApp.class, args);
        ServiceSystemProperty.init();
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
        registry.addViewController("/ws").setViewName("/ws.html");
    }
    
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
    	//对网页请求进行拦截
    	registry.addInterceptor(new HandlerInterceptor());
	}
}
