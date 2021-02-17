package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.common.CommonResult;
import cn.edu.nju.kg_qa.domain.entity.BookNode;
import cn.edu.nju.kg_qa.domain.entity.ConceptNode;
import cn.edu.nju.kg_qa.service.ConceptService;
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
 * date: 2020/12/23 17:37<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Api(tags = "概念搜索结果接口")
@RestController
@RequestMapping("/conceptSearch")
public class ConceptSearchController {
    @Autowired
    ConceptService conceptService;

    @ApiOperation(value = "查询所有概念")
    @GetMapping("/findAllConcepts")
    public CommonResult<List<ConceptNode>> findAllConcepts(){
        List<ConceptNode> list=conceptService.findAllConcept();
        return CommonResult.success(list);
    }

    @ApiOperation(value = "概念名 模糊 查询概念")
    @GetMapping(value = {"/findConceptsByConceptName/{conceptName}","/findConceptsByConceptName"})
    public CommonResult<List<ConceptNode>> findConceptsByConceptName(@PathVariable(required = false) String conceptName){
        List<ConceptNode> list=conceptService.findConceptsByConceptName(conceptName);
        return CommonResult.success(list);
    }

    @ApiOperation(value = "书名 模糊 查询概念")
    @GetMapping(value = {"/findConceptsByBookName/{bookName}"})
    public CommonResult<List<ConceptNode>> findBooksByBookName(@PathVariable String bookName){
        List<ConceptNode> list=conceptService.findConceptsByBookName(bookName);
        return CommonResult.success(list);
    }





}
