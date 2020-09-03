package cn.cao.travel.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author cao
 * @create 2020/8/31 - 16:46
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha defaultKaptcha(){

        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();

        // 验证码的属性   创建容器，该容器继承与 HashTable，之所以是 Properties，是因为 com.google.code.kaptcha.util.Config 类需要 这个类型的容器
        Properties properties = new Properties();
        //图片边框
        properties.put("kaptcha.border", "no");
        //边框颜色
        properties.put("kaptcha.textproducer.font.color", "black");
        //图片宽度
        properties.put("kaptcha.image.width", "160");
        properties.put("kaptcha.image.height", "50");
        //字体大小
        properties.put("kaptcha.textproducer.font.size", "40");
        //session key 会话密钥,经测试，这个写不写都可以正常获取验证码
        properties.put("kaptcha.session.key", "verifyCode");
        //文字间隔
        properties.put("kaptcha.textproducer.char.space", "5");
        //验证码长度
        properties.put("kaptcha.textproducer.char.length", "4");

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;

    }
}
