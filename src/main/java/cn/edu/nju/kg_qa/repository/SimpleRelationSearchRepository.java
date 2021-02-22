package cn.edu.nju.kg_qa.repository;

import cn.edu.nju.kg_qa.domain.base.BaseRelation;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/2/22 23:06<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
public interface SimpleRelationSearchRepository {

    public List<BaseRelation> simpleRelationSearch(String srcNodeName,String secNodeType,String desNodeType);
}
