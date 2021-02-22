package cn.edu.nju.kg_qa.service.qaService;

import cn.edu.nju.kg_qa.domain.base.BaseRelation;

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
     * @param srcNodeName   查询节点名
     * @param nodeId        查询节点id 可能为空
     * @param qid           问题id qid
     * @return
     */
    List<BaseRelation> oneHopRelationSearch(String srcNodeName,Integer nodeId, Integer qid);
}
