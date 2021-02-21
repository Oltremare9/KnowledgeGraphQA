package cn.edu.nju.kg_qa.service.impl;

import cn.edu.nju.kg_qa.constant.PresetQuestionEnum;
import cn.edu.nju.kg_qa.domain.response.NodeNameAndLabelsResponse;
import cn.edu.nju.kg_qa.domain.response.PresetQuestionResponse;
import cn.edu.nju.kg_qa.service.extractService.HandleBookSeriesService;
import cn.edu.nju.kg_qa.service.qaService.JieBaService;
import cn.edu.nju.kg_qa.service.qaService.PresetQAService;
import com.huaban.analysis.jieba.SegToken;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description: <br/>
 * date: 2021/2/22 1:12<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class PresetQAServiceImpl implements PresetQAService {
    private Logger logger= LoggerFactory.getLogger(PresetQAServiceImpl.class);

    @Autowired
    JieBaService jieBaService;

    @Override
    public List<PresetQuestionResponse> getPotentialUsersQuestions(String userInput) {
        List<SegToken> tokens=jieBaService.jieBaCutSentence(userInput);
        List<NodeNameAndLabelsResponse> list=new ArrayList<>();
        List<PresetQuestionResponse> res=new ArrayList<>();
        for(SegToken segToken:tokens){
            String nodePrefix=segToken.word;
            list=jieBaService.getWordLabelAndName(nodePrefix);
            //不是特定图谱内节点
            if(list.size()==0){
                continue;
            }else {
                //todo 根据node热度进行排序
                for(NodeNameAndLabelsResponse node:list){
                    String nodeName=node.getNodeName();
                    Integer id=node.getId();
                    if(node.getLabel()==null || node.getLabel().size()==0){
                        logger.error("label为空");
                        break;
                    }
                    String label=node.getLabel().get(0);
                    if(nodeName==null || nodeName.equals("") || id==null || label==null || label.equals("")){
                        logger.error("字段为空");
                        break;
                    }
                    for(PresetQuestionEnum presetQuestionEnum:PresetQuestionEnum.values()){
                        if(presetQuestionEnum.getStartLabel().equals(label)){
                            PresetQuestionResponse response=new PresetQuestionResponse();
                            response.setNodeName(nodeName);
                            response.setQuestionId(presetQuestionEnum.getId());
                            response.setNodeId(id);
                            response.setQuestionContent(presetQuestionEnum.getQuestionDescription().
                                    replaceAll("\\[]", nodeName));
                            res.add(response);
                            if(res.size()>=10){
                                return res;
                            }
                        }
                    }
                }
                break;
            }
        }
        return res;
    }
}
