package cn.edu.nju.kg_qa.repository;

import cn.edu.nju.kg_qa.domain.base.BaseRelation;
import cn.edu.nju.kg_qa.domain.dto.NodeNameAndLabelsDto;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description: <br/>
 * date: 2021/2/21 23:22<br/>
 *
 * @author HaoNanWang<br />
 * @since JDK 11
 */
@Repository
public interface JieBaRepository  extends Neo4jRepository<BaseRelation,Long> {

    @Query("Match (p)-[r]-() where p.name=~ $nodeName+'.*' " +
            "return distinct(labels(p)) as label ,p.name as nodeName,id(p) as id,type(r) as relationType")
    List<NodeNameAndLabelsDto> getWordLabelAndNameAndRelation(@Param("nodeName") String nodeName);

    @Query("Match (p)-[r]-() where p.name=$nodeName" +
            " return distinct(labels(p)) ")
    List<String> getWordLabelByExactName(String nodeName);

    @Query("Match (p)-[r]-() where p.id=$nodeId " +
            "return distinct(labels(p)) as label ,p.name as nodeName,id(p) as id,type(r) as relationType")
    List<NodeNameAndLabelsDto> getWordLabelAndNameAndRelationByNodeId(String nodeId);
}
