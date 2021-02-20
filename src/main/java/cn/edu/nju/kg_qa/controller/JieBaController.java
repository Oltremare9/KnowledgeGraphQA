package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.common.CommonResult;
import cn.edu.nju.kg_qa.domain.relation.BelongRelation;
import cn.edu.nju.kg_qa.service.JieBaService;
import cn.edu.nju.kg_qa.util.Jieba;
import com.huaban.analysis.jieba.SegToken;
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
 * date: 2021/2/21 1:53<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Api(tags = "jieba分词接口")
@RestController
@RequestMapping("/jieba")
public class JieBaController {
    @Autowired
    JieBaService jieBaService;

    @ApiOperation("根据输入句子 进行分词")
    @GetMapping("/findBelongToRelationByConceptName/{sentence}")
    public CommonResult<List<SegToken>> jieBaCutSentence(@PathVariable String sentence) {
        List<SegToken> list = jieBaService.jieBaCutSentence(sentence);
        return CommonResult.success(list);
    }
}
