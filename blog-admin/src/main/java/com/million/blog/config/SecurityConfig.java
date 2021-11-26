package com.million.blog.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //密码加密
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        //加密策略 MD5 不安全 彩虹表  MD5 加盐
        String mszlu = new BCryptPasswordEncoder().encode("mszlu");
        System.out.println(mszlu);
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests() //开启登录认证
//                .antMatchers("/user/findAll").hasRole("admin") //访问接口需要admin的角色
                .antMatchers("/css/**").permitAll() //permitAll是放行
                .antMatchers("/img/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/plugins/**").permitAll()
                //使用access登录认证的时候，只有加了admin的路径才要认证，这样才不会影响到自定义的相关配置
                .antMatchers("/admin/**").access("@authService.auth(request,authentication)") //自定义service 来去实现实时的权限认证
                .antMatchers("/pages/**").authenticated() //登录认证
                .and().formLogin() //表单登录
                .loginPage("/login.html") //自定义的登录页面
                .loginProcessingUrl("/login") //登录处理接口
                .usernameParameter("username") //定义登录时的用户名的key 默认为username
                .passwordParameter("password") //定义登录时的密码key，默认是password
                .defaultSuccessUrl("/pages/main.html")
                .failureUrl("/login.html")
                .permitAll() //通过 不拦截，更加前面配的路径决定，这是指和登录表单相关的接口 都通过
                .and().logout() //退出登录配置
                .logoutUrl("/logout") //退出登录接口
                .logoutSuccessUrl("/login.html")
                .permitAll() //退出登录的接口放行
                .and()
                .httpBasic() //如果使用postMain请求的话我们也拦截
                .and()
                //csrf关闭 如果自定义登录 需要关闭
                // 可以检查是否是自己发起的请求，防止伪造跨域请求
                //默认使用post请求
                .csrf().disable()
                .headers().frameOptions().sameOrigin(); //支持iframe页面嵌套
    }
}
