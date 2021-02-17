package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.common.CommonResult;
import cn.edu.nju.kg_qa.domain.entity.InstituteNode;
import cn.edu.nju.kg_qa.service.InstituteService;
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
 * date: 2020/12/23 17:36<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Api(tags = "机构搜索结果接口")
@RestController
@RequestMapping("/instituteSearch")
public class InstituteSearchController {
    @Autowired
    InstituteService instituteService;

    @ApiOperation(value = "机构名 模糊 查询机构")
    @GetMapping(value = "/findInstitutesByInstituteName/{instituteName}")
    public CommonResult<List<InstituteNode>> findInstituteByInstituteName(@PathVariable String instituteName) {
        List<InstituteNode> list=instituteService.findInstituteByInstituteName(instituteName);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "书名 精准 查询机构")
    @GetMapping(value = "/findInstitutesByBookName/{bookName}")
    public CommonResult<List<InstituteNode>> findInstitutesByBookName(@PathVariable String bookName) {
        List<InstituteNode> list=instituteService.findInstituteNodesByBookName(bookName);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "地名城市 精准 查询机构")
    @GetMapping(value = "/findInstitutesByPosition/{position}")
    public CommonResult<List<InstituteNode>> findInstitutesByPosition(@PathVariable String position) {
        List<InstituteNode> list=instituteService.findInstituteByPosition(position);
        return CommonResult.success(list);
    }
}
