package cn.edu.nju.kg_qa.repository;

import cn.edu.nju.kg_qa.domain.base.BaseRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/2/22 23:06<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Repository
public interface OneHopRelationSearchRepository extends Neo4jRepository<BaseRelation, Long> {

    /**
     * 根据节点名 实现单跳问题模板查询
     * @param srcNodeName
     * @param relationName
     * @param skip
     * @param limit
     * @return
     */
    @Query("Match ans=(p)-[r]-(a) where p.name=$srcNodeName and type(r)=$relationName return ans skip $skip limit $limit")
    List<BaseRelation> oneHopRelationSearch(String srcNodeName, String relationName, int skip, int limit);

    /**
     * 实现单跳问题模板查询
     * @param srcNodeId
     * @param srcNodeName
     * @param relationName
     * @param skip
     * @param limit
     * @return
     */
    @Query("Match ans=(p)-[r]-(a) where id(p)=$srcNodeId and p.name=$srcNodeName and type(r)=$relationName return ans skip $skip limit $limit")
    List<BaseRelation> oneHopRelationSearch(Integer srcNodeId, String srcNodeName, String relationName, int skip, int limit);
}
