package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.domain.base.BaseRelation;
import cn.edu.nju.kg_qa.service.ComplexService;
import com.alibaba.fastjson.JSON;
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
 * date: 2020/12/25 1:33<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@RestController
@Api(tags = "复杂关系查询接口 （通过任意节点名 查询相关关系，不在意节点类型）")
@RequestMapping("/complexRelationSearch")
public class ComplexRelationSearchController {
    @Autowired
    ComplexService complexService;

    @ApiOperation("根据节点名返回所有符合条件的关系")
    @GetMapping("/findAnyProperRelationByName/{nodeName}")
    public String findAnyProperRelationsByName(@PathVariable String nodeName) {
        List<BaseRelation> list = complexService.findAnyProperRelationByName(nodeName);
        return JSON.toJSONString(list);
    }
}
