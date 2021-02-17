package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.common.CommonResult;
import cn.edu.nju.kg_qa.domain.entity.BookNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.flogger.Flogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanghn
 */
@Api(tags = "hello 测试系统联通性 以及jenkins使用")
@RestController
public class HelloController {
    private static Logger log = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/hello")
    @ApiOperation(value = "无参数")
    public CommonResult<Boolean> sayHello(BookNode node) {
        log.info("hello the nice world!");
        return CommonResult.success(null, "hello world");
    }
}
