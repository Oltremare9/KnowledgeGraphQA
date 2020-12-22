package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.domain.entity.BookNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanghn
 */
@Api(tags = "hello 测试系统联通性 以及jenkins使用")
@RestController
public class HelloController {
    @RequestMapping("/hello")
    @ApiOperation(value="无参数")
    public String sayHello(BookNode node) {
        return "Hello the World! 12：22"+node.toString();
    }
}
