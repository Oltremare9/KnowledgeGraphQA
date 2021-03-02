package cn.edu.nju.kg_qa.repository;

import cn.edu.nju.kg_qa.domain.base.BaseNode;
import cn.edu.nju.kg_qa.domain.base.BaseRelation;
import cn.edu.nju.kg_qa.domain.dto.NodeNameAndLabelsDto;
import cn.edu.nju.kg_qa.domain.dto.NodeNameAndNodeIdDto;
import cn.edu.nju.kg_qa.domain.entity.ConceptNode;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/3/2 17:04<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Repository
public interface InterfaceDisplayRepository extends Neo4jRepository<BaseRelation,Long> {

    /**
     * 根据自增主键 返回node
     * @param nodeId
     * @return
     */
    @Query("Match (p) where id(p)=$nodeId return p.name as nodeName,id(p) as nodeId")
    NodeNameAndNodeIdDto getNodeByNodeId(@Param("nodeId") int nodeId);

    /**
     * 根据节点类型 补全首页推荐节点
     * @param nodeType
     * @param limit
     * @return
     */
    @Query("match (n) where any(label in labels(n) WHERE label in [$nodeType]) return n.name as nodeName, id(n) as nodeId" +
            " limit $limit")
    List<NodeNameAndNodeIdDto> getNodeNameByNodeTypeAnd(@Param("nodeType") String nodeType, int limit);
}
