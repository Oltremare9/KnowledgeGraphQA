package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.domain.base.BaseRelation;
import cn.edu.nju.kg_qa.domain.relation.BelongRelation;
import cn.edu.nju.kg_qa.service.BelongToService;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Description: <br/>
 * date: 2020/12/24 17:54<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Api(tags = "关系查询接口")
@RestController
@RequestMapping("/relationSearch")
public class RelationSearchController {
    @Autowired
    BelongToService belongToService;

    @GetMapping("/getAllBelongToRelation")
    public String getAllBelongToRelation(){
        List<BelongRelation> list=belongToService.findAllBelongToRelation();
        return JSON.toJSONString(list);
    }
}
