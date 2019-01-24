package com.example.config;

import com.example.security.CustomUserService;
import com.example.security.MyFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by yangyibo on 17/1/18.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
    @Autowired
    private CustomUserService customUserService;//注册UserDetailsService 的bean

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(customUserService).passwordEncoder(new BCryptPasswordEncoder()); //user Details Service验证
//    }

    /**
     * @describe: 配置可以访问的静态资源路径
     * @param web
     * @return: void
     * @auther: king
     * @date: 2018/11/26 9:23
     **/
    @Override
    public void configure(WebSecurity web)throws Exception {
        // 设置不拦截规则
        web.ignoring().antMatchers("/css/**", "/font-awesome/**","/images/**", "/img/**", "/js/**");
    }


    /**
     * @describe: 配置权限认证拦截的url以及拦截后跳转的login页面
     * @param http
     * @return: void
     * @auther: king
     * @date: 2018/11/24 21:02
     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 所有用户均可访问的资源              这里要梳理一下位置   有问题!!!
                .addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class)//在正确的位置添加我们自定义的过滤器
                //认证请求，所有请求都需要认证
                .authorizeRequests()
                //.antMatchers("/css/**", "/font-awesome/**","/images/**", "/img/**", "/js/**").permitAll()
                .anyRequest().authenticated()
//	        .and().formLogin().and()
//	        .httpBasic();
                // 自定义登录页面
                .and()
                //拦截后跳转的登录页面的url
                .formLogin().loginPage("/login")
                //登录失败的url
                .failureUrl("/login?error=1")
                //登录处理的url
                .loginProcessingUrl("/spring_security_check")
                //登录处理的url，也就是form表单action指定的路
                .successForwardUrl("/")
                //设置用户名和密码的属性名
                .usernameParameter("username")
                //成功跳转的默认网址		permitAll（）代表允许这一段url的请求不拦截
                .passwordParameter("password").permitAll()
                .and().exceptionHandling().accessDeniedPage("/Access_Denied");


        //如果开启了CSRF 退出则需要使用POST访问，可以使用以下方式解决，但并不推荐
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                //登出成功后跳转的地址，以及删除的cookie名称
                .logoutSuccessUrl("/login?error=logout")
                //并使session使用无效
                .invalidateHttpSession(true);
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService);
        auth.authenticationProvider(authenticationProvider()); // 加密
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserService); // 去数据库中查询 用户角色权限
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    //得到加密后的密码
   /* public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
        //$2a$10$zD71MyxAGOBjx65Opm5lu.TPV6RbTJENoCxvkvLh14j5A/vG/GDK.
    }*/
}

