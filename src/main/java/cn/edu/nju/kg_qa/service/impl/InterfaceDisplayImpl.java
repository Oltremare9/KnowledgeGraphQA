package cn.edu.nju.kg_qa.service.impl;

import cn.edu.nju.kg_qa.constant.RedisPrefix;
import cn.edu.nju.kg_qa.domain.dto.NodeNameAndNodeIdDto;
import cn.edu.nju.kg_qa.domain.response.TopicResponse;
import cn.edu.nju.kg_qa.repository.InterfaceDisplayRepository;
import cn.edu.nju.kg_qa.service.InterfaceDisplayService;
import cn.edu.nju.kg_qa.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTMLDocument;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Description: <br/>
 * date: 2021/3/2 17:22<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class InterfaceDisplayImpl implements InterfaceDisplayService {

    @Autowired
    InterfaceDisplayRepository interfaceDisplayRepository;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<TopicResponse> getTopicDisplay() {
        List<TopicResponse> res = new ArrayList<>();
        Set<Object> eTypeSet = redisUtil.revRange(RedisPrefix.E_Type_ZSet.getPrefix(), 0, -1);
        //访问类型数量小于3兜底
        int restSize = 3 - eTypeSet.size();
        Iterator iterator = eTypeSet.iterator();
        while (iterator.hasNext()) {
            String type = String.valueOf(iterator.next());
            TopicResponse topicResponse = new TopicResponse();
            topicResponse.setLabel(type);
            List<NodeNameAndNodeIdDto> nodeList = new ArrayList<>();
            Set<Object> eNodeSet = redisUtil.revRange(RedisPrefix.E_Node_ZSet.getPrefix() + type, 0, -1);
            for (Object nodeId : eNodeSet) {
                NodeNameAndNodeIdDto nodeName = interfaceDisplayRepository.getNodeByNodeId(Integer.parseInt(String.valueOf(nodeId)));
                nodeList.add(nodeName);
                if (nodeList.size() >= 5) {
                    break;
                }
            }
            if(nodeList.size()<5){
                List<NodeNameAndNodeIdDto> extraNodeName=interfaceDisplayRepository.getNodeNameByNodeTypeAnd(type, 5-nodeList.size());
                nodeList.addAll(extraNodeName);
            }
            topicResponse.setNodeNameAndNodeIdDto(nodeList);
            res.add(topicResponse);
        }
        return res;
    }
}
