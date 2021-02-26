package cn.edu.nju.kg_qa.service.qaService;

import cn.edu.nju.kg_qa.domain.base.BaseRelation;
import org.springframework.boot.autoconfigure.integration.IntegrationAutoConfiguration;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/2/23 2:39<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public interface OneHopRelationSearchService {

    /**
     * 根据节点类型 实现单跳问题模板查询
     *
     * @param srcNodeName
     * @param nodeId
     * @param qid
     * @param skip
     * @return
     */
    List<BaseRelation> oneHopRelationSearch(String srcNodeName, Integer nodeId, Integer qid, Integer skip);
}
