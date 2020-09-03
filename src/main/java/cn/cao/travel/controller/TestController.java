package cn.cao.travel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cao
 * @create 2020/8/31 - 16:08
 */
@RestController
public class TestController {

    @GetMapping("test")
    public String test(){

        return "spring start ok";
    }
}
