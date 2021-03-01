package cn.edu.nju.kg_qa.service.impl;

import cn.edu.nju.kg_qa.component.DataCache;
import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.constant.PresetQuestionEnum;
import cn.edu.nju.kg_qa.constant.RedisPrefix;
import cn.edu.nju.kg_qa.domain.base.BaseNode;
import cn.edu.nju.kg_qa.domain.base.BaseRelation;
import cn.edu.nju.kg_qa.repository.OneHopRelationSearchRepository;
import cn.edu.nju.kg_qa.service.ComplexService;
import cn.edu.nju.kg_qa.service.qaService.JieBaService;
import cn.edu.nju.kg_qa.service.qaService.OneHopRelationSearchService;
import cn.edu.nju.kg_qa.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.ArrayList;
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

    @Autowired
    OneHopRelationSearchRepository oneHopRelationSearchRepository;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public List<BaseRelation> oneHopRelationSearch(String srcNodeName, Integer nodeId, Integer qid, Integer skip) {
        List<BaseRelation> res = new ArrayList<>();
        PresetQuestionEnum questionEnum = DataCache.presetQuestionEnumMap.get(qid);
        //问题类型统计
        redisUtil.zIncrScore(RedisPrefix.Q_Type_ZSet.getPrefix(), qid.toString());
        String nodeType = DataCache.presetQuestionEnumMap.get(qid).getSrcNodeType();
        //节点类型统计
        if (nodeType != null && !nodeType.equals("")) {
            redisUtil.zIncrScore(RedisPrefix.E_Type_ZSet.getPrefix(), nodeType);
            redisUtil.zIncrScore(RedisPrefix.E_Node_ZSet.getPrefix()+nodeType, nodeId.toString());
        }

        String relationType = questionEnum.getRelationName();
        if (nodeId == null) {
            res = oneHopRelationSearchRepository.oneHopRelationSearch(srcNodeName, relationType, skip, Config.limit);
        } else {
            res = oneHopRelationSearchRepository.oneHopRelationSearch(nodeId, srcNodeName, relationType, skip, Config.limit);
        }

        return res;
    }
}
