package cn.edu.nju.kg_qa.service.impl;

import cn.edu.nju.kg_qa.component.DataCache;
import cn.edu.nju.kg_qa.constant.LabelEnum;
import cn.edu.nju.kg_qa.constant.PresetQuestionEnum;
import cn.edu.nju.kg_qa.constant.RedisPrefix;
import cn.edu.nju.kg_qa.domain.dto.NodeNameAndLabelsDto;
import cn.edu.nju.kg_qa.domain.response.PresetQuestionResponse;
import cn.edu.nju.kg_qa.service.qaService.JieBaService;
import cn.edu.nju.kg_qa.service.qaService.PresetQAService;
import cn.edu.nju.kg_qa.util.RedisUtil;
import com.huaban.analysis.jieba.SegToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

/**
 * Description: <br/>
 * date: 2021/2/22 1:12<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class PresetQAImpl implements PresetQAService {
    private Logger logger = LoggerFactory.getLogger(PresetQAImpl.class);

    @Autowired
    JieBaService jieBaService;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<PresetQuestionResponse> getPotentialUsersQuestions(String userInput) {
        List<SegToken> tokens = jieBaService.jieBaCutSentence(userInput);
        List<NodeNameAndLabelsDto> list = new ArrayList<>();
        List<PresetQuestionResponse> res = new ArrayList<>();
        for (int index = 0; index < tokens.size(); index++) {
            for (int i = tokens.size() - 1; i >= 0; i--) {
                String nodePrefix = "";
                for (int j = index; j <= i; j++) {
                    nodePrefix += tokens.get(j).word;
                }
                if (!DataCache.otherNamesMap.containsKey(nodePrefix)) {
                    list = jieBaService.getWordLabelAndNameAndRelation(nodePrefix);
                } else {
                    String nodeId = DataCache.otherNamesMap.get(nodePrefix);
                    list = jieBaService.getWordLabelAndNameAndRelationByNodeId(nodeId);
                }
                Set<Object> nodeTypeSet = redisUtil.revRange(RedisPrefix.E_Type_ZSet.getPrefix(), 0, -1);
                logger.info(nodeTypeSet.toString());
                //不是特定图谱内节点
                if (null == list || list.size() == 0) {
                    continue;
                } else {
                    list.sort(new Comparator<NodeNameAndLabelsDto>() {
                        @Override
                        public int compare(NodeNameAndLabelsDto t0, NodeNameAndLabelsDto t1) {
                            String label1 = t0.getLabel().get(0);
                            String label2 = t1.getLabel().get(0);
                            //类别相同 暂不做排序
                            //todo 根据node热度进行排序
                            if (label1.equals(label2)) {
                                return 0;
                            } else {
                                // 类别不同 以节点类别排序
                                Iterator iterator = nodeTypeSet.iterator();
                                while (iterator.hasNext()) {
                                    String label = String.valueOf(iterator.next());
                                    if (label1.equals(label)) {
                                        return -1;
                                    }
                                    if (label2.equals(label)) {
                                        return 1;
                                    }
                                }
                                return 0;
                            }
                        }
                    });
                    for (NodeNameAndLabelsDto node : list) {
                        String relationType = node.getRelationType();
                        String nodeName = node.getNodeName();
                        String bookNodeName = "《" + nodeName + "》";
                        Integer id = node.getId();
                        if (node.getLabel() == null || node.getLabel().size() == 0) {
                            logger.error("label为空");
                            break;
                        }
                        String label = node.getLabel().get(0);
                        if (nodeName == null || nodeName.equals("") || id == null
                                || label == null || label.equals("")
                                || (null == relationType) || (relationType.equals(""))) {
                            logger.error("字段为空");
                            break;
                        }
                        for (Map.Entry entry : DataCache.presetQuestionEnumMap.entrySet()) {
                            PresetQuestionEnum presetQuestionEnum = (PresetQuestionEnum) entry.getValue();
                            if (presetQuestionEnum.getSrcNodeType().equals(label)) {
                                if (presetQuestionEnum.getQid() > 100 || presetQuestionEnum.getRelationName().equals(relationType)) {
                                    PresetQuestionResponse response = new PresetQuestionResponse();
                                    //图书节点加书名号
                                    if (presetQuestionEnum.getSrcNodeType().equals(LabelEnum.LABEL_BOOK.getPropertyName())) {
                                        response.setQuestionContent(presetQuestionEnum.getQuestionDescription().
                                                replaceAll("\\[]", bookNodeName));
                                    } else {
                                        response.setQuestionContent(presetQuestionEnum.getQuestionDescription().
                                                replaceAll("\\[]", nodeName));
                                    }
                                    response.setNodeName(nodeName);
                                    response.setQuestionId(presetQuestionEnum.getQid());
                                    response.setNodeId(id);
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
        }
        return res;
    }
}
