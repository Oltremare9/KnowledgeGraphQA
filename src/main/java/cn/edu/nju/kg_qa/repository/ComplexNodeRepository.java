package cn.edu.nju.kg_qa.repository;

import cn.edu.nju.kg_qa.domain.base.BaseNode;
import cn.edu.nju.kg_qa.domain.entity.BookNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description: <br/>
 * date: 2020/12/25 1:37<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Repository
public interface ComplexNodeRepository extends Neo4jRepository<BaseNode,Long> {

    /**
     * 根据节点名返回所有符合条件的节点
     * @param nodeName
     * @return
     */
    @Query("Match ans=(p)-[r]-(a) where a.name=$nodeName return p")
    List<BaseNode> findAnyProperNodesByName(@Param("nodeName") String nodeName);

    /**
     * 根据节点自增id返回节点
     * @param id
     * @return
     */
    @Query("Match (p) where id(p)=$id return p")
    List<BaseNode> findNodeByIdAnd(Long id);

    @Query("match (p)-[r]-(q) where id(p)=$id and type(r)=$relation return q")
    List<BaseNode> findNodeByIdAndRelation(Long id,String relation);

    @Query("match (p) where p.name=$nodeName return p")
    List<BaseNode> findNodeByName(String nodeName);

}
