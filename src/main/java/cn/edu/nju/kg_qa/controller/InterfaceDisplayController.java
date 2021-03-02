package cn.edu.nju.kg_qa.controller;

import cn.edu.nju.kg_qa.common.CommonResult;
import cn.edu.nju.kg_qa.domain.response.TopicResponse;
import cn.edu.nju.kg_qa.service.InterfaceDisplayService;
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
 * date: 2021/3/2 17:35<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Api(tags = "界面显示调用接口")
@RestController
@RequestMapping("/display")
public class InterfaceDisplayController {
    @Autowired
    InterfaceDisplayService interfaceDisplayService;

    @ApiOperation("获取主题栏目内容")
    @GetMapping("/displayTopic")
    public CommonResult<List<TopicResponse>> displayTopic() {
        List<TopicResponse> list = interfaceDisplayService.getTopicDisplay();
        return CommonResult.success(list);
    }
}
