package cn.edu.nju.kg_qa.service.qaService;

import cn.edu.nju.kg_qa.domain.response.PresetQuestionResponse;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/2/22 1:11<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */

public interface PresetQAService {
    /***
     * 根据用户输入 分词后返回潜在问题
     * @param userInput
     * @return
     */
    public List<PresetQuestionResponse> getPotentialUsersQuestions(String userInput);
}
