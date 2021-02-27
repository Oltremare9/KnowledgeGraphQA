package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.common.CommonResult;
import cn.edu.nju.kg_qa.domain.relation.BelongRelation;
import cn.edu.nju.kg_qa.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/2/28 1:02<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Api(tags = "redis测试接口")
@RestController
@RequestMapping("/testRedis")
public class RedisController {
    @Autowired
    RedisUtil redisUtil;

    @ApiOperation("测试redis")
    @GetMapping("/testRedis")
    public CommonResult<Boolean> testRedis() {
        return CommonResult.success(redisUtil.set("whn", "123", 60));
    }
}
