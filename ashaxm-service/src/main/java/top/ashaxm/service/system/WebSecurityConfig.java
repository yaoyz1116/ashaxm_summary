package top.ashaxm.service.system;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import top.ashaxm.common.utils.MD5Encoder;


/**
 * spring的安全机制
 * @author yaoyz
 * 2018年1月8日
 */
@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.authorizeRequests().antMatchers("/index.html").permitAll().anyRequest().authenticated();
    	http.authorizeRequests().antMatchers("/**").permitAll().anyRequest().authenticated();
        http.formLogin().loginPage("/login").permitAll().and().logout().permitAll();
        http.formLogin().successHandler(new AjaxAuthenticationSuccessHandler());
        http.formLogin().failureHandler(new AjaxAuthenticationFailureHandler());
        http.logout().logoutSuccessHandler(new AjaxlogoutSuccessHandler());
        
        http.csrf().disable();
    }

    @Configuration
    protected static class AuthenticationConfiguration extends
            GlobalAuthenticationConfigurerAdapter {

        @Autowired
        DataSource dataSource;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            UserDetailsService userService = new LoginUserService(dataSource);
            PasswordEncoder encoder = new MD5Encoder();
            auth.userDetailsService(userService).passwordEncoder(encoder);
        }
    }
}
