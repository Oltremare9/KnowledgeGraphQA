package cn.edu.nju.kg_qa.repository;

import cn.edu.nju.kg_qa.domain.base.BaseNode;
import cn.edu.nju.kg_qa.domain.base.BaseRelation;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description: <br/>
 * date: 2020/12/25 1:21<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Repository
public interface ComplexRelationRepository extends Neo4jRepository<BaseRelation,Long> {
    /**
     * 根据节点名返回所有符合条件的关系
     * @param nodeName
     * @return
     */
    @Query("Match (p)-[r]-(a) where a.name=$nodeName return p,r,a")
    List<BaseRelation> findAnyProperRelationByName(@Param("nodeName") String nodeName);

    /**
     * 根据节点名返回所有符合条件的多重关系
     * @param nodeName
     * @return
     */
    @Query("Match ans=(p)-[r]-(a)-[]-() where p.name=$nodeName return ans")
    List<BaseRelation> findAnyRelationByName(@Param("nodeName") String nodeName);

    /**
     * 根据节点的唯一自增id 进行关系的查询
     * @param id
     * @return
     */
    @Query("Match ans=(p)-[]-() where id(p)=$id return ans")
    List<BaseRelation> findAnyRelationByIdentity(Long id);
}
