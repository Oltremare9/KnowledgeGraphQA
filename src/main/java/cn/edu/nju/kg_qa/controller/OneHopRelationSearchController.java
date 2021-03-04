package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.common.CommonResult;
import cn.edu.nju.kg_qa.component.DataCache;
import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.domain.base.BaseRelation;
import cn.edu.nju.kg_qa.domain.request.OneHopRequest;
import cn.edu.nju.kg_qa.domain.response.OneHopResponse;
import cn.edu.nju.kg_qa.service.qaService.OneHopRelationSearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.util.ArrayList;
import java.util.List;

/**
 * Description: <br/>
 * date: 2021/2/23 2:43<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Api(tags = "单跳问题查询接口")
@RestController
@RequestMapping("/oneHopSearch")
public class OneHopRelationSearchController {
    private Logger logger = LoggerFactory.getLogger(OneHopRelationSearchController.class);

    @Autowired
    OneHopRelationSearchService oneHopRelationSearchService;

    @ApiOperation(value = "根据qid，nodeId返回相关单跳关系")
    @PostMapping("/oneHopSearch")
    public CommonResult<Object> oneHopSearch(@Validated OneHopRequest oneHopRequest, BindingResult bindingResult) {
        List<BaseRelation> res = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            return CommonResult.failed(bindingResult.getFieldError().getDefaultMessage());
        }
        String nodeName = oneHopRequest.getNodeName();
        Integer qid = oneHopRequest.getQid();
        Integer nodeId = oneHopRequest.getNodeId();
        if (!DataCache.presetQuestionEnumMap.containsKey(qid)) {
            return CommonResult.failed("qid不存在");
        }
        Integer skip = 0;
        if (oneHopRequest.getSkip() != null) {
            skip = oneHopRequest.getSkip() * Config.limit;
        }
        res = oneHopRelationSearchService.oneHopRelationSearch(nodeName, nodeId, qid, skip);
        return CommonResult.success(res);

    }

    @ApiOperation(value ="单跳问题解析 输入问题返回答案")
    @GetMapping("/oneHopQuestion/{question}")
    public CommonResult<List<OneHopResponse>> oneHopQuestion(@PathVariable String question){
        return CommonResult.success(oneHopRelationSearchService.getQuestionType(question));
    }
}
