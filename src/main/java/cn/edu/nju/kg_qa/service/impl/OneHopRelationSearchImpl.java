package cn.edu.nju.kg_qa.service.impl;

import cn.edu.nju.kg_qa.component.DataCache;
import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.constant.PresetQuestionEnum;
import cn.edu.nju.kg_qa.constant.RedisPrefix;
import cn.edu.nju.kg_qa.domain.base.BaseNode;
import cn.edu.nju.kg_qa.domain.base.BaseRelation;
import cn.edu.nju.kg_qa.domain.dto.NodeNameAndLabelsDto;
import cn.edu.nju.kg_qa.domain.entity.AuthorNode;
import cn.edu.nju.kg_qa.domain.request.BertRequest;
import cn.edu.nju.kg_qa.domain.response.BertResponse;
import cn.edu.nju.kg_qa.domain.response.OneHopResponse;
import cn.edu.nju.kg_qa.repository.ComplexNodeRepository;
import cn.edu.nju.kg_qa.repository.OneHopRelationSearchRepository;
import cn.edu.nju.kg_qa.service.ComplexService;
import cn.edu.nju.kg_qa.service.qaService.JieBaService;
import cn.edu.nju.kg_qa.service.qaService.OneHopRelationSearchService;
import cn.edu.nju.kg_qa.util.RedisUtil;
import com.huaban.analysis.jieba.SegToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Description: <br/>
 * date: 2021/2/23 2:41<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Service
public class OneHopRelationSearchImpl implements OneHopRelationSearchService {

    private Logger logger = LoggerFactory.getLogger(OneHopRelationSearchImpl.class);
    @Autowired
    OneHopRelationSearchRepository oneHopRelationSearchRepository;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    JieBaService service;

    @Autowired
    ComplexNodeRepository complexNodeRepository;

    @Override
    public List<BaseRelation> oneHopRelationSearch(String srcNodeName, Integer nodeId, Integer qid, Integer skip) {
        List<BaseRelation> res = new ArrayList<>();
        PresetQuestionEnum questionEnum = DataCache.presetQuestionEnumMap.get(qid);
        //问题类型统计
        redisUtil.zIncrScore(RedisPrefix.Q_Type_ZSet.getPrefix(), qid.toString());
        redisUtil.expireHalfMonth(RedisPrefix.Q_Type_ZSet.getPrefix());
        String nodeType = DataCache.presetQuestionEnumMap.get(qid).getSrcNodeType();
        //节点类型统计
        if (nodeType != null && !nodeType.equals("")) {
            redisUtil.zIncrScore(RedisPrefix.E_Type_ZSet.getPrefix(), nodeType);
            redisUtil.expireHalfMonth(RedisPrefix.E_Type_ZSet.getPrefix());
            redisUtil.zIncrScore(RedisPrefix.E_Node_ZSet.getPrefix() + nodeType, nodeId.toString());
            redisUtil.expireHalfMonth(RedisPrefix.E_Node_ZSet.getPrefix() + nodeType);
        }

        String relationType = questionEnum.getRelationName();
        if (nodeId == null) {
            res = oneHopRelationSearchRepository.oneHopRelationSearch(srcNodeName, relationType, skip, Config.limit);
        } else {
            res = oneHopRelationSearchRepository.oneHopRelationSearch(nodeId, srcNodeName, relationType, skip, Config.limit);
        }

        return res;
    }

    @Override
    public List<OneHopResponse> getQuestionType(String question) {
        List<OneHopResponse> returnValue = new ArrayList<>();

        List<SegToken> tokens = service.jieBaCutSentence(question);
        List<NodeNameAndLabelsDto> res;
        for (int index = 0; index < tokens.size(); index++) {
            for (int i = tokens.size() - 1; i >= 0; i--) {
                String nodePrefix = "";
                for (int j = index; j <= i; j++) {
                    nodePrefix += tokens.get(j).word;
                }
                if (!DataCache.otherNamesMap.containsKey(nodePrefix)) {
                    res = service.getWordLabelAndNameAndRelation(nodePrefix);
                } else {
                    String nodeId = DataCache.otherNamesMap.get(nodePrefix);
                    res = service.getWordLabelAndNameAndRelationByNodeId(nodeId);
                }
                if (null == res || res.size() == 0) {
                    continue;
                } else {
                    for (NodeNameAndLabelsDto nodes : res) {
                        String searchResultType = nodes.getLabel().get(0);

                        String searchQuestion = question.replace(nodes.getNodeName(), "<" + searchResultType + ">");

                        RestTemplate restTemplate = new RestTemplate();
                        BertRequest request = new BertRequest();
                        ArrayList<String> list = new ArrayList<>();
                        list.add(searchQuestion);
                        request.setData(list);
                        ResponseEntity<BertResponse> responseEntity = restTemplate.postForEntity("http://47.101.190.134:5000/qa", request, BertResponse.class);
                        BertResponse response = responseEntity.getBody();
                        String questionType;
                        if (response != null && response.getData().size() != 0) {
                            questionType = response.getData().get(0);
                            logger.error(questionType);
                        } else {
                            continue;
                        }
                        String s[] = questionType.split("\\.");
                        String srcNodeType = s[0];
                        String relationName = s[1];
                        String dstNodeType = s[2];
                        //节点类型判断
                        if (srcNodeType.equals(nodes.getLabel().get(0))) {
                            //属性查询
                            if (relationName.equals(dstNodeType)) {
                                List<BaseNode> node = complexNodeRepository.findNodeByIdAnd(nodes.getId().longValue());
                                if (node != null && node.size() != 0) {
                                    OneHopResponse response1 = new OneHopResponse();
                                    response1.setBase(node);
                                    response1.setValueType(relationName);
                                    returnValue.add(response1);
                                }
                                //关系查询
                            } else {
                                List<BaseNode> node = complexNodeRepository.findNodeByIdAndRelation(nodes.getId().longValue(), relationName);
                                if(node!=null && node.size()!=0) {
                                    OneHopResponse response1 = new OneHopResponse();
                                    response1.setBase(node);
                                    response1.setValueType("relation");
                                    returnValue.add(response1);
                                }
                            }
                        } else {
                            continue;
                        }
                        if(returnValue.size()>0){
                            break;
                        }
                    }
                }
                if(returnValue.size()>0) {
                    break;
                }
            }
            if(returnValue.size()>0) {
                break;
            }
        }
        return returnValue;
    }
}
