package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.common.CommonResult;
import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.domain.dto.NameAndScoreDto;
import cn.edu.nju.kg_qa.domain.dto.RepeatedAuthorNameAndList;
import cn.edu.nju.kg_qa.domain.request.DoubleNodeIdRequest;
import cn.edu.nju.kg_qa.service.AdminService;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    @ApiOperation(value = "判断两点间潜在关系距离 0为4跳内没有 id用")
    @PostMapping("/getRelationPathNumsByNodeId")
    public CommonResult<Integer> getRelationPathNumsByNodeId(@Validated DoubleNodeIdRequest request){
        return CommonResult.success(adminService.getRelationPathNumsByNodeId(request.getNodeId1(),request.getNodeId2()));
    }

    @ApiOperation(value = "获取redis统计数据")
    @PostMapping("/getStatisticsByType/{type}")
    public CommonResult<String> getStatisticsByType(@PathVariable int type){
        ArrayList<NameAndScoreDto> res= adminService.getStatisticsByKeyAndType(type, "");
        return CommonResult.success(new Gson().toJson(res));
    }

}
