package cn.cao.travel.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author cao
 * @create 2020/8/29 - 19:39
 */
@Component
public class ApplicationContextUtils implements ApplicationContextAware {

    //保留下来工厂
    private static ApplicationContext app;

    //将创建好工厂以参数形式传递给这个类
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.app = applicationContext;
    }


    //提供在工厂中获取对象的方法 //RedisTemplate  redisTemplate
    public static Object getBean(String beanName){

        return app.getBean(beanName);
    }
}
