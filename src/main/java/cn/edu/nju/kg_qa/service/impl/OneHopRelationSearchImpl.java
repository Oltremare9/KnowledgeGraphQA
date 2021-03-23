package cn.edu.nju.kg_qa.service.impl;

import cn.edu.nju.kg_qa.component.DataCache;
import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.constant.PresetQuestionEnum;
import cn.edu.nju.kg_qa.constant.RedisPrefix;
import cn.edu.nju.kg_qa.domain.base.BaseNode;
import cn.edu.nju.kg_qa.domain.base.BaseRelation;
import cn.edu.nju.kg_qa.domain.dto.EcharsNodesDto;
import cn.edu.nju.kg_qa.domain.dto.EchartsCategotyDto;
import cn.edu.nju.kg_qa.domain.dto.EchartsRelationDto;
import cn.edu.nju.kg_qa.domain.dto.NodeNameAndLabelsDto;
import cn.edu.nju.kg_qa.domain.request.ModelRequest;
import cn.edu.nju.kg_qa.domain.response.EchartsResponse;
import cn.edu.nju.kg_qa.domain.response.ModelResponse;
import cn.edu.nju.kg_qa.domain.response.OneHopResponse;
import cn.edu.nju.kg_qa.repository.AuthorRepository;
import cn.edu.nju.kg_qa.repository.ComplexNodeRepository;
import cn.edu.nju.kg_qa.repository.OneHopRelationSearchRepository;
import cn.edu.nju.kg_qa.service.qaService.JieBaService;
import cn.edu.nju.kg_qa.service.qaService.OneHopRelationSearchService;
import cn.edu.nju.kg_qa.util.RedisUtil;
import com.huaban.analysis.jieba.SegToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Autowired
    AuthorRepository authorRepository;

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

        RestTemplate restTemplate = new RestTemplate();
        ModelRequest request = new ModelRequest();
        ArrayList<String> data = new ArrayList<>();
        data.add(question);
        request.setData(data);
        ResponseEntity<ModelResponse> responseEntity = restTemplate.postForEntity("http://47.101.190.134:5000/qa", request, ModelResponse.class);
        ModelResponse response = responseEntity.getBody();

        if(response==null || response.getData()==null || response.getData().size()==0){
            return  returnValue;
        }
        List<List<String>> responseDataList= response.getData();
        logger.error(responseDataList.toString());
        List<String> responseData=responseDataList.get(0);
        String entityName=responseData.get(0);
        String questionType=responseData.get(1);
        String srcNodeType=questionType.split("\\.")[0];
        String relationType=questionType.split("\\.")[1];
        String dstNodeType=questionType.split("\\.")[2];

        BaseNode node;
        if(DataCache.otherNamesMap.containsKey(entityName)){
            String nodeId=DataCache.otherNamesMap.get(entityName);
            node=authorRepository.findAuthorById(nodeId).get(0);
        }else {
            List<BaseNode> nodes = complexNodeRepository.findNodeByName(entityName);
            if(nodes.size()>0) {
                node = nodes.get(0);
            }else{
                return returnValue;
            }
        }
        if (relationType.equals(dstNodeType)) {
            List<BaseNode> dstNodes = complexNodeRepository.findNodeByIdAnd(node.getIdentity().longValue());
            if (node != null && dstNodes.size() != 0) {
                OneHopResponse oneHopResponse=new OneHopResponse();
                oneHopResponse.setBase(dstNodes);
                oneHopResponse.setValueType(relationType+"_property");
                returnValue.add(oneHopResponse);
            }
            //关系查询
        } else {
            List<BaseNode> dstNodes = complexNodeRepository.findNodeByIdAndRelation(node.getIdentity().longValue(), relationType);
            if (dstNodes != null && dstNodes.size() != 0) {
                OneHopResponse oneHopResponse=new OneHopResponse();
                dstNodes.add(node);
                oneHopResponse.setBase(dstNodes);
                oneHopResponse.setValueType(relationType+"_relation");
                returnValue.add(oneHopResponse);
            }
        }
        return returnValue;
    }


    @Override
    public EchartsResponse getQuestionTypeEcharts(String question) {

        return null;
        /*
        EchartsResponse echartsResponse=new EchartsResponse();
        ArrayList<EcharsNodesDto> echarsNodesDto=new ArrayList<>();
        ArrayList<EchartsRelationDto> echartsRelationDto=new ArrayList<>();
        ArrayList<EchartsCategotyDto> echartsCategotyDto=new ArrayList<>();
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

                        String searchQuestion = question.replace(nodePrefix, "<" + searchResultType + ">");

                        RestTemplate restTemplate = new RestTemplate();
                        ModelRequest request = new ModelRequest();
                        ArrayList<String> list = new ArrayList<>();
                        list.add(searchQuestion);
                        request.setData(list);
                        ResponseEntity<ModelResponse> responseEntity = restTemplate.postForEntity("http://47.101.190.134:5000/qa", request, ModelResponse.class);
                        ModelResponse response = responseEntity.getBody();
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

                                }
                                //关系查询
                            } else {
                                List<BaseNode> node = complexNodeRepository.findNodeByIdAndRelation(nodes.getId().longValue(), relationName);
                                if (node != null && node.size() != 0) {
                                    for (BaseNode baseNode : node) {
                                        EcharsNodesDto nodesDto = new EcharsNodesDto();
                                        nodesDto.setId(baseNode.getIdentity().toString());
                                        nodesDto.setName(baseNode.getName());
                                        nodesDto.setCategory(1);
                                        echarsNodesDto.add(nodesDto);

                                        EchartsRelationDto relationDto = new EchartsRelationDto();
                                        relationDto.setSource(nodes.getId().toString());
                                        relationDto.setTarget(baseNode.getIdentity().toString());
                                        echartsRelationDto.add(relationDto);

                                    }
                                }
                            }
                        } else {
                            continue;
                        }
                        if (echartsRelationDto.size() > 0) {
                            EcharsNodesDto nodesDto = new EcharsNodesDto();
                            List<BaseNode> baseNode = complexNodeRepository.findNodeByIdAnd(Long.valueOf(nodes.getId()));
                            nodesDto.setId(baseNode.get(0).getIdentity().toString());
                            nodesDto.setName(baseNode.get(0).getName());
                            nodesDto.setCategory(0);
                            echarsNodesDto.add(nodesDto);

                            EchartsCategotyDto echartsCategotyDto1 = new EchartsCategotyDto();
                            echartsCategotyDto1.setName(srcNodeType);
                            EchartsCategotyDto echartsCategotyDto2 = new EchartsCategotyDto();
                            echartsCategotyDto2.setName(dstNodeType);

                            echartsCategotyDto.add(echartsCategotyDto1);
                            echartsCategotyDto.add(echartsCategotyDto2);
                            break;
                        }
                    }
                }
                if (echartsRelationDto.size() > 0) {
                    break;
                }
            }
            if (echartsRelationDto.size() > 0) {
                break;
            }
        }
        echartsResponse.setLinks(echartsRelationDto);
        echartsResponse.setNodes(echarsNodesDto);
        echartsResponse.setCategories(echartsCategotyDto);
        return echartsResponse;


         */
    }
}





