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
}
