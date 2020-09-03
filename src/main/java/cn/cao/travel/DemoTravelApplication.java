package cn.cao.travel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.cao.travel.mapper")
public class DemoTravelApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoTravelApplication.class, args);
    }

}
