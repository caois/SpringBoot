package cn.cao.travel.config;

import cn.cao.travel.interceptors.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author cao
 * @create 2020/9/10 - 21:05
 */
@Configuration
public class InterceptorsConfig implements WebMvcConfigurer {

    //将配置好的拦截器添加到SpringBoot中
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new JWTInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/**")    //放行 user下的资源
                .excludePathPatterns("/","/txt/**","/files/**","/js/**","/image/**");//放行静态资源
                 //静态资源会进入拦截器，

    }
}
