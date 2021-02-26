package cn.edu.nju.kg_qa.service.impl;

import cn.edu.nju.kg_qa.component.DataCache;
import cn.edu.nju.kg_qa.config.Config;
import cn.edu.nju.kg_qa.constant.PresetQuestionEnum;
import cn.edu.nju.kg_qa.domain.base.BaseRelation;
import cn.edu.nju.kg_qa.repository.OneHopRelationSearchRepository;
import cn.edu.nju.kg_qa.service.qaService.OneHopRelationSearchService;
import org.springframework.beans.factory.annotation.Autowired;
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


    @Override
    public List<BaseRelation> oneHopRelationSearch(String srcNodeName, Integer nodeId, Integer qid, Integer skip) {
        List<BaseRelation> res = new ArrayList<>();
        PresetQuestionEnum questionEnum = DataCache.presetQuestionEnumMap.get(qid);
        String relationType = questionEnum.getRelationName();
        if (nodeId == null) {
            res = oneHopRelationSearchRepository.oneHopRelationSearch(srcNodeName, relationType, skip, Config.limit);
        } else {
            res = oneHopRelationSearchRepository.oneHopRelationSearch(nodeId, srcNodeName, relationType, skip, Config.limit);
        }
        return res;
    }
}
