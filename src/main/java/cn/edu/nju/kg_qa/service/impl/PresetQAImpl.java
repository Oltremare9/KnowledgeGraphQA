package cn.edu.nju.kg_qa.service.impl;

import cn.edu.nju.kg_qa.component.DataCache;
import cn.edu.nju.kg_qa.constant.PresetQuestionEnum;
import cn.edu.nju.kg_qa.domain.dto.NodeNameAndLabelsDto;
import cn.edu.nju.kg_qa.domain.response.PresetQuestionResponse;
import cn.edu.nju.kg_qa.service.qaService.JieBaService;
import cn.edu.nju.kg_qa.service.qaService.PresetQAService;
import com.huaban.analysis.jieba.SegToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description: <br/>
 * date: 2021/2/22 1:12<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class PresetQAImpl implements PresetQAService {
    private Logger logger= LoggerFactory.getLogger(PresetQAImpl.class);

    @Autowired
    JieBaService jieBaService;

    @Override
    public List<PresetQuestionResponse> getPotentialUsersQuestions(String userInput) {
        List<SegToken> tokens=jieBaService.jieBaCutSentence(userInput);
        List<NodeNameAndLabelsDto> list=new ArrayList<>();
        List<PresetQuestionResponse> res=new ArrayList<>();
        for(int i=tokens.size()-1;i>=0;i--){
            String nodePrefix="";
            for(int j=0;j<=i;j++){
                nodePrefix+=tokens.get(j).word;
            }
            list=jieBaService.getWordLabelAndName(nodePrefix);
            //不是特定图谱内节点
            if(null==list || list.size()==0){
                continue;
            }else {
                //todo 根据node热度进行排序
                for(NodeNameAndLabelsDto node:list){
                    String relationType=node.getRelationType();
                    String nodeName=node.getNodeName();
                    Integer id=node.getId();
                    if(node.getLabel()==null || node.getLabel().size()==0){
                        logger.error("label为空");
                        break;
                    }
                    String label=node.getLabel().get(0);
                    if(nodeName==null || nodeName.equals("") || id==null
                            || label==null || label.equals("")
                            ||(null==relationType) || (relationType.equals(""))){
                        logger.error("字段为空");
                        break;
                    }
                    for(Map.Entry entry: DataCache.presetQuestionEnumMap.entrySet()){
                        PresetQuestionEnum presetQuestionEnum= (PresetQuestionEnum) entry.getValue();
                        if(presetQuestionEnum.getSrcNodeType().equals(label)) {
                            if (presetQuestionEnum.getQid()>100 || presetQuestionEnum.getRelationName().equals(relationType)) {
                                PresetQuestionResponse response = new PresetQuestionResponse();
                                response.setNodeName(nodeName);
                                response.setQuestionId(presetQuestionEnum.getQid());
                                response.setNodeId(id);
                                response.setQuestionContent(presetQuestionEnum.getQuestionDescription().
                                        replaceAll("\\[]", nodeName));
                                res.add(response);
                                if (res.size() >= 10) {
                                    return res;
                                }
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
