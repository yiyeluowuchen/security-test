package com.yiyezhiqiu.security.config;


import com.yiyezhiqiu.security.service.impl.CustomUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@EnableGlobalMethodSecurity(prePostEnabled = true) // 启用方法安全设置
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

    @Autowired
    CustomUserServiceImpl customUserService;


    @Bean
    UserDetailsService customUserService(){ //注册UserDetailsService 的bean
        return new CustomUserServiceImpl();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        log.info("是否真的进来了这里来验证密码等东西");
        auth.userDetailsService(customUserService())
                .passwordEncoder(new BCryptPasswordEncoder()); //user Details Service验证 ,也就是这里进行，验证。主要应该就是验证密码

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
             //   .antMatchers(new String[]{"/plugins/**"}).permitAll()//  在这个路径下都可以访问
                .antMatchers("/aa").permitAll()
                .anyRequest().authenticated() //这里有个.authentiacted，代表要登陆后，才能访问
                .and()
                .formLogin()
                .loginPage("/login")  //基于表单，登陆页面,一定要和login中的action保持一致，/login与数据库中url不匹配，其他登陆的没有权限，如/会跳回到/login。当这里设置为/会访问不到，感觉因为验证了它不是表单
                .defaultSuccessUrl("/").permitAll()
                .failureUrl("/login?error")
                .permitAll() //登录页面用户任意访问
                .and()
                .logout().permitAll()
                .and()
                .headers()
                .frameOptions().sameOrigin(); ; //注销行为任意访问
        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }

    @Override
    public void configure(WebSecurity web) {
        //解决静态资源被拦截的问题（plugins目录在工程resources/static/下）
        web.ignoring().antMatchers("/plugins/**","/login.html");
    }
}