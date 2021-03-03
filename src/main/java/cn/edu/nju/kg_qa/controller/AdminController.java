package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.common.CommonResult;
import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.domain.dto.RepeatedAuthorNameAndList;
import cn.edu.nju.kg_qa.domain.entity.AuthorNode;
import cn.edu.nju.kg_qa.service.AdminService;
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
 * date: 2021/3/3 22:03<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Api(tags = "管理员接口")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @ApiOperation(value = "固定id删除节点及关系")
    @GetMapping("/deleteNodeAndRelationById/{id}")
    public CommonResult<String> deleteNodeAndRelationById(@PathVariable Integer id){
        String s=adminService.deleteNodeAndRelationById(id);
        return CommonResult.success(s);
    }

    @ApiOperation(value = "获取所有重复作者节点 skip为页数 0开始")
    @GetMapping("/getAllRepeatedAuthorNodes/{skip}")
    public CommonResult<List<RepeatedAuthorNameAndList>> getAllRepeatedAuthorNodes(@PathVariable Integer skip){
        List<RepeatedAuthorNameAndList> list = adminService.getAllRepeatedAuthorNodes(skip * Config.limit);
        return CommonResult.success(list);
    }
}
