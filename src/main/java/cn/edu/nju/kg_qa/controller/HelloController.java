package cn.edu.nju.kg_qa.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanghn
 */
@RestController
public class HelloController {
    @RequestMapping("/hello")
    public String sayHello() {
        return "Hello the World!";
    }
}
