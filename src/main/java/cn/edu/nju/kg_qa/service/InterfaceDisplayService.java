package cn.edu.nju.kg_qa.service;

import cn.edu.nju.kg_qa.domain.response.TopicResponse;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/3/2 17:15<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public interface InterfaceDisplayService {

    /**
     * 专题界面显示接口
     * @return
     */
    public List<TopicResponse> getTopicDisplay();
}
