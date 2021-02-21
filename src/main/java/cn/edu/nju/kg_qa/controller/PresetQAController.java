package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.common.CommonResult;
import cn.edu.nju.kg_qa.domain.relation.BelongRelation;
import cn.edu.nju.kg_qa.domain.response.PresetQuestionResponse;
import cn.edu.nju.kg_qa.service.qaService.PresetQAService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Description: <br/>
 * date: 2021/2/21 23:15<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Api(tags = "预置问题QA接口")
@RestController
@RequestMapping("/PresetQAController")
public class PresetQAController {
    @Autowired
    PresetQAService presetQAService;

    @ApiOperation("根据输入 返回潜在用户提问问题 ")
    @GetMapping("/getPotentialUsersQuestions/{userInput}")
    public CommonResult<List<PresetQuestionResponse>> getPotentialUsersQuestions(@PathVariable String userInput) {
        List<PresetQuestionResponse> list = new ArrayList<>();
        list=presetQAService.getPotentialUsersQuestions(userInput);
        return CommonResult.success(list);
    }
}
